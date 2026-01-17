import sys
from pathlib import Path

_TRANSLIB_ROOT = Path(__file__).resolve()
for _parent in _TRANSLIB_ROOT.parents:
    if (_parent / "README.md").exists():
        if str(_parent) not in sys.path:
            sys.path.insert(0, str(_parent))
        break
del _parent, _TRANSLIB_ROOT
from translib.providers import build_openai_client, ensure_non_empty, get_google_api_keys, get_google_cse_id


import os
import re
import time
import logging
import requests
from bs4 import BeautifulSoup
from translib.providers import build_openai_client

# --- 日志配置 ---
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

# --- OpenAI 客户端 （请用环境变量配置）---
MODEL_NAME = "gpt-3.5-turbo"
client = build_openai_client(script_path=__file__)

# --- Google API Key 轮换列表 & 获取函数 ---
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

# --- 提取第一个 Markdown 代码块中的纯代码 ---
def extract_code_block(text, lang=None):
    if lang:
        pattern = rf"```{lang}\s*\n([\s\S]*?)```"
    else:
        pattern = r"```(?:\w+)?\s*\n([\s\S]*?)```"
    m = re.search(pattern, text, re.IGNORECASE)
    return m.group(1).rstrip() if m else text.strip()

# --- 搜索问题（轮换 & 重试） ---
def search_stackoverflow_question(query, cx, pagesize=5, max_retries=3):
    url = "https://www.googleapis.com/customsearch/v1"
    for attempt in range(1, max_retries+1):
        key = get_next_api_key()
        try:
            r = requests.get(url, params={"q":query, "key":key, "cx":cx, "num":pagesize}, timeout=10)
            r.raise_for_status()
            items = r.json().get("items", [])
            qs = []
            for it in items:
                link = it.get("link","")
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

# --- 获取答案 ---
def fetch_answers(qid, max_answers=3):
    url = f"https://api.stackexchange.com/2.3/questions/{qid}/answers"
    params = {"order":"desc","sort":"votes","site":"stackoverflow","filter":"!9_bDE(fI5"}
    try:
        r = requests.get(url, params=params, timeout=10)
        r.raise_for_status()
        return [BeautifulSoup(it["body"], "html.parser").get_text()
                for it in r.json().get("items", [])[:max_answers]]
    except requests.exceptions.HTTPError as he:
        if he.response is not None and he.response.status_code == 429:
            log_print(f"[WARN] 答案获取限流 429: question_id={qid}")
            return None  # 限流也跳过
    except requests.exceptions.ConnectionError as ce:
        log_print(f"[WARN] 答案获取网络错误: {ce}")
        return None  # 连接重置等网络错误也跳过
    except Exception as e:
        log_print(f"[ERROR] 获取答案失败: {e}")
    return []  # 其他错误当作无答案处理


# --- generate_question ---
def generate_question(code, src, tgt):
    prompt = (
        f"Analyze the following code snippet written in {src}, and generate a single, concise, and well-formed question that summarizes the translation requirements of this code into {tgt}. The question should:\n"
        f"1. Be a simple sentence.\n"
        f"2. Avoid including the original code snippet directly.\n"
        f"3. Clearly describe the key functionality or purpose of the code that needs to be translated.\n"
        f"4. Be enclosed in triple single quotes (''').\n\n"
        f"Code snippet:\n```{src}\n{code}\n```"
    )
    resp = client.chat.completions.create(
        model=MODEL_NAME,
        messages=[{"role": "user", "content": prompt}],
        temperature=0.01
    )
    raw = resp.choices[0].message.content.strip()
    log_print(f"[DEBUG] Raw question response: {raw}")
    m = re.search(r"'''(.*?)'''", raw, re.DOTALL)
    if m:
        return m.group(1).strip()
    cleaned = re.sub(r"```[\s\S]*?```", "", raw).strip()
    return cleaned

# --- 单文件翻译 ---
def translate_code(code, src, tgt, cx):
    log_print(f"[INFO] 翻译 {src} → {tgt}")
    q = generate_question(code, src, tgt)
    log_print(f"[INFO] 问句: {q}")
    qs = search_stackoverflow_question(q, cx)

    # 如果没有相关问题，直接翻译
    if not qs:
        log_print("[INFO] 无相关问题，直接调用 OpenAI 翻译")
        resp = client.chat.completions.create(
            model=MODEL_NAME,
            messages=[
                {"role":"system","content":"You are a helpful assistant for code translation."},
                {"role":"user","content":(
                    f"Translate the following {src} code into {tgt}:\n\n{code}"
                )}
            ],
            temperature=0.01
        )
        return resp.choices[0].message.content.strip()

    sel = qs[0]
    log_print(f"[INFO] 选第1条: {sel['title']}")
    ans = fetch_answers(sel["question_id"])

    # 如果限流失败，跳过保存
    if ans is None:
        log_print("[WARN] 答案因限流失败，跳过此文件")
        return None

    # 如果没有答案，也直接翻译
    if not ans:
        log_print("[INFO] 无答案，直接调用 OpenAI 翻译")
        resp = client.chat.completions.create(
            model=MODEL_NAME,
            messages=[
                {"role":"system","content":"You are a helpful assistant for code translation."},
                {"role":"user","content":(
                    f"Translate the following {src} code into {tgt}:\n\n{code}"
                )}
            ],
            temperature=0.01
        )
        return resp.choices[0].message.content.strip()

    # 正常通过参考答案翻译
    ref = "\n\n".join(ans)
    resp = client.chat.completions.create(
        model=MODEL_NAME,
        messages=[
            {"role":"system","content":"You are a helpful assistant for code translation."},
            {"role":"user","content":(
                f"Using the following StackOverflow answers as reference, translate this {src} code into {tgt}:\n\n{ref}\n\n{code}"
            )}
        ],
        temperature=0.01
    )
    return resp.choices[0].message.content.strip()

# --- 批量处理 ---
LANG_MAP = {
    "C++":    ("cpp",   ".cpp", "cpp"),
    "Python": ("python",".py",  "python"),
    "Java":   ("java",  ".java", "java"),
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
                # result=None 时跳过保存
                if result:
                    code_block = extract_code_block(result, lang=tgt_mark)
                    with open(out_path, "w", encoding="utf-8") as wf:
                        wf.write(code_block + "\n")
                    log_print(f"[INFO] 保存: {out_path}")
                # 为了避免请求过快，适当休眠
                time.sleep(5)

if __name__ == "__main__":
    INPUT_ROOT  = "."               # 当前目录下有 cpp/, python/, java/
    OUTPUT_ROOT = "translations"    # 最终输出到 translations/<MODEL_NAME>/...
    SEARCH_CX = get_google_cse_id()
    process_all(INPUT_ROOT, OUTPUT_ROOT, SEARCH_CX)
