import sys
from pathlib import Path

_TRANSLIB_ROOT = Path(__file__).resolve()
for _parent in _TRANSLIB_ROOT.parents:
    if (_parent / "README.md").exists():
        if str(_parent) not in sys.path:
            sys.path.insert(0, str(_parent))
        break
del _parent, _TRANSLIB_ROOT
from translib.providers import ensure_non_empty, get_google_api_keys, get_google_cse_id


import os
import re
import time
import logging
import requests
from bs4 import BeautifulSoup
import qianfan                              # 替换 OpenAI 为千帆 SDK
from qianfan.errors import APIError         # ⬅ 用于捕获超长异常

# ------------------ 日志配置（保持不变） ------------------
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.StreamHandler(),
        logging.FileHandler("log.txt", mode='a', encoding='utf-8')
    ]
)
logger = logging.getLogger()


def log_print(msg):
    logger.info(msg)
    print(msg)


# ------------------ 模型配置 ------------------
MODEL_NAME = "CodeLlama-7b-Instruct"
comp = qianfan.Completion()

# --------------- Google API Key 轮换列表 ---------------
API_KEYS = ensure_non_empty(
    get_google_api_keys(),
    what="Google Custom Search API Keys (GOOGLE_CSE_API_KEYS)",
)
api_idx = 0
def get_next_api_key():
    global api_idx
    key = API_KEYS[api_idx]
    api_idx = (api_idx + 1) % len(API_KEYS)
    return key


# ------------------ Token 限制辅助 ------------------
MAX_PROMPT_TOKENS = 7800       # 预留一点裕度，避免估算误差

def approx_tokens(text: str) -> int:
    """粗略估算 token 数；中文按 1，英文按空格分。"""
    return len(re.sub(r"[^\x00-\x7f]", " ", text).split())

def build_prompt_with_answers(src: str, tgt: str, answers: list[str], code: str) -> str:
    head = (f"Using the following StackOverflow answers as reference, "
            f"translate this {src} code into {tgt}:\n\n")
    tail = f"\n\n{code}"
    prompt_answers = "\n\n".join(answers)
    return head + prompt_answers + tail


# ------------------ 提取 Markdown 代码块 ------------------
def extract_code_block(text, lang=None):
    if lang:
        pattern = rf"```{lang}\s*\n([\s\S]*?)```"
    else:
        pattern = r"```(?:\w+)?\s*\n([\s\S]*?)```"
    m = re.search(pattern, text, re.IGNORECASE)
    return m.group(1).rstrip() if m else text.strip()


# ------------------ 搜索 StackOverflow 问题 ------------------
def search_stackoverflow_question(query, cx, pagesize=5, max_retries=3):
    url = "https://www.googleapis.com/customsearch/v1"
    for attempt in range(1, max_retries + 1):
        key = get_next_api_key()
        try:
            r = requests.get(url, params={"q": query, "key": key, "cx": cx, "num": pagesize}, timeout=10)
            r.raise_for_status()
            items = r.json().get("items", [])
            qs = []
            for it in items:
                link = it.get("link", "")
                m = re.search(r"/questions/(\d+)", link)
                if m:
                    qs.append({
                        "title": it["title"],
                        "link": link,
                        "question_id": m.group(1)
                    })
            return qs
        except Exception as e:
            log_print(f"[WARN] 搜索失败 {attempt}/{max_retries}: {e}")
            time.sleep(2)
    log_print("[ERROR] 达到最大重试，放弃检索。")
    return []


# ------------------ 获取 StackOverflow 答案 ------------------
def fetch_answers(qid, max_answers=3):
    url = f"https://api.stackexchange.com/2.3/questions/{qid}/answers"
    params = {
        "order": "desc",
        "sort": "votes",
        "site": "stackoverflow",
        "filter": "!9_bDE(fI5"
    }
    try:
        r = requests.get(url, params=params, timeout=10)
        r.raise_for_status()
        return [
            BeautifulSoup(it["body"], "html.parser").get_text()
            for it in r.json().get("items", [])[:max_answers]
        ]
    except requests.exceptions.HTTPError as he:
        if he.response is not None and he.response.status_code == 429:
            log_print(f"[WARN] 答案获取限流 429: question_id={qid}")
            return None
    except requests.exceptions.ConnectionError as ce:
        log_print(f"[WARN] 答案获取网络错误: {ce}")
        return None
    except Exception as e:
        log_print(f"[ERROR] 获取答案失败: {e}")
    return []


# ------------------ generate_question ------------------
def generate_question(code, src, tgt):
    prompt = (
        f"Analyze the following code snippet written in {src}, and generate a single, concise, and well-formed question that summarizes the translation requirements of this code into {tgt}. The question should:\n"
        f"1. Be a simple sentence.\n"
        f"2. Avoid including the original code snippet directly.\n"
        f"3. Clearly describe the key functionality or purpose of the code that needs to be translated.\n"
        f"4. Be enclosed in triple single quotes (''').\n\n"
        f"Code snippet:\n`{src}\n{code}\n`"
    )

    resp = comp.do(
        model=MODEL_NAME,
        prompt=prompt,
        temperature=0.01,
        top_p=0.95
    )
    raw = resp["body"]["result"].strip()
    log_print(f"[DEBUG] Raw question response: {raw}")
    m = re.search(r"'''(.*?)'''", raw, re.DOTALL)
    if m:
        return m.group(1).strip()
    cleaned = re.sub(r"`[\s\S]*?`", "", raw).strip()
    return cleaned


# ------------------ 核心翻译 ------------------
def translate_code(code, src, tgt, cx):
    log_print(f"[INFO] 翻译 {src} → {tgt}")
    q = generate_question(code, src, tgt)
    log_print(f"[INFO] 问句: {q}")
    qs = search_stackoverflow_question(q, cx)

    # ---- 无相关问题：直接翻译 ----
    if not qs:
        log_print("[INFO] 无相关问题，直接调用模型翻译")
        try:
            resp = comp.do(
                model=MODEL_NAME,
                prompt=f"Translate the following {src} code into {tgt}:\n\n{code}",
                temperature=0.01,
                top_p=0.95
            )
            return resp["body"]["result"].strip()
        except APIError as e:
            log_print(f"[ERROR] 直接翻译仍超长，跳过此文件: {e}")
            return None

    # ---- 有相关问题 ----
    sel = qs[0]
    log_print(f"[INFO] 选第1条: {sel['title']}")
    answers = fetch_answers(sel["question_id"])


    # 动态截断并重试
    answers_copy = answers.copy()
    while True:
        prompt = build_prompt_with_answers(src, tgt, answers_copy, code)

        # 估算过长就先删
        while approx_tokens(prompt) > MAX_PROMPT_TOKENS and answers_copy:
            answers_copy.pop()
            prompt = build_prompt_with_answers(src, tgt, answers_copy, code)

        try:
            resp = comp.do(
                model=MODEL_NAME,
                prompt=prompt,
                temperature=0.01,
                top_p=0.95
            )
            return resp["body"]["result"].strip()      # ✅ 成功
        except APIError as e:
            # 仅当超长，再继续删一条重试
            if (getattr(e, "code", getattr(e, "error_code", None)) == 336007
                    and answers_copy):
                log_print("[WARN] API 报超长，再删除一条答案并重试……")
                answers_copy.pop()
                continue
            log_print(f"[ERROR] 翻译失败: {e}")
            return None


# ------------------ 批量处理（保持不变） ------------------
LANG_MAP = {
    "C++": ("cpp", ".cpp", "cpp"),
    "Python": ("python", ".py", "python"),
    "Java": ("java", ".java", "java"),
}

def process_all(INPUT_ROOT, OUTPUT_ROOT, cx):
    for src_lang, (src_dir, src_ext, _) in LANG_MAP.items():
        in_dir = os.path.join(INPUT_ROOT, src_dir)
        for fname in os.listdir(in_dir):
            if not fname.endswith(src_ext):
                continue
            base = fname[:-len(src_ext)]
            with open(os.path.join(in_dir, fname), encoding="utf-8") as f:
                code = f.read()

            for tgt_lang, (tgt_dir, tgt_ext, tgt_mark) in LANG_MAP.items():
                if tgt_lang == src_lang:
                    continue
                out_base = os.path.join(OUTPUT_ROOT, MODEL_NAME, f"{src_lang}_to_{tgt_lang}")
                os.makedirs(out_base, exist_ok=True)
                out_path = os.path.join(out_base, f"{base}{tgt_ext}")

                if os.path.exists(out_path):
                    log_print(f"[INFO] 已存在，跳过: {out_path}")
                    continue

                result = translate_code(code, src_lang, tgt_lang, cx)
                if result:
                    code_block = extract_code_block(result, lang=tgt_mark)
                    with open(out_path, "w", encoding="utf-8") as wf:
                        wf.write(code_block + "\n")
                    log_print(f"[INFO] 保存: {out_path}")

                time.sleep(5)  # 避免请求过快


# ------------------ 入口 ------------------
if __name__ == "__main__":
    INPUT_ROOT = "."
    OUTPUT_ROOT = "translations"
    SEARCH_CX = get_google_cse_id()
    process_all(INPUT_ROOT, OUTPUT_ROOT, SEARCH_CX)
