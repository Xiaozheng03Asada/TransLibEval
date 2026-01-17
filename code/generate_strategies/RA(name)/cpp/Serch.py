import os
import sys
import json
import re
import requests
from bs4 import BeautifulSoup
import logging
import time
from pathlib import Path

_TRANSLIB_ROOT = Path(__file__).resolve()
for _parent in _TRANSLIB_ROOT.parents:
    if (_parent / "README.md").exists():
        if str(_parent) not in sys.path:
            sys.path.insert(0, str(_parent))
        break
del _parent, _TRANSLIB_ROOT

from translib.providers import ensure_non_empty, get_google_api_keys, get_google_cse_id

# 强制 stdout/stderr 使用 UTF‑8 编码，避免打印 Unicode 时出现 Latin‑1 错误
if hasattr(sys.stdout, "reconfigure"):
    sys.stdout.reconfigure(encoding="utf-8")
if hasattr(sys.stderr, "reconfigure"):
    sys.stderr.reconfigure(encoding="utf-8")

# 初始化日志记录器
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.StreamHandler(),
        logging.FileHandler("log.txt", mode='w', encoding='utf-8')
    ]
)
logger = logging.getLogger()

# —— Google CSE API key 列表与轮询逻辑 —— #
API_KEYS = ensure_non_empty(
    get_google_api_keys(),
    what="Google Custom Search API Keys (GOOGLE_CSE_API_KEYS)",
)
api_index = 0
def get_next_api_key():
    global api_index
    key = API_KEYS[api_index]
    api_index = (api_index + 1) % len(API_KEYS)
    return key

# Google Programmable Search Engine ID
SEARCH_ENGINE_ID = get_google_cse_id()

def search_stackoverflow_question(query, pagesize=5, max_retries=3):
    """
    使用 Google CustomSearch (site:stackoverflow.com) 检索最相关的 StackOverflow 问题。
    返回第一个问题的 {"question_id","title","link"}，或 None。
    """
    url = "https://www.googleapis.com/customsearch/v1"
    params = {
        "q": f"site:stackoverflow.com {query}",
        "key": get_next_api_key(),
        "cx": SEARCH_ENGINE_ID,
        "num": pagesize
    }
    retries = 0
    while retries < max_retries:
        try:
            r = requests.get(url, params=params)
            r.raise_for_status()
            items = r.json().get("items", [])
            if not items:
                return None
            first = items[0]
            link = first.get("link","")
            m = re.search(r"/questions/(\d+)", link)
            if not m:
                return None
            return {
                "question_id": m.group(1),
                "title": first.get("title",""),
                "link": link
            }
        except Exception as e:
            retries += 1
            logger.error(f"[ERROR] CSE search attempt {retries} failed: {e}")
            time.sleep(2)
    return None

def fetch_answers(question_id, max_answers=3):
    """
    获取指定 StackOverflow 问题的答案正文（按投票数排序）。
    返回答案文本列表。
    """
    url = f"https://api.stackexchange.com/2.3/questions/{question_id}/answers"
    params = {
        "order": "desc",
        "sort": "votes",
        "site": "stackoverflow",
        "filter": "!9_bDE(fI5"
    }
    try:
        r = requests.get(url, params=params)
        r.raise_for_status()
        items = r.json().get("items", [])
        return [BeautifulSoup(it["body"], "html.parser").get_text() for it in items[:max_answers]]
    except Exception as e:
        logger.error(f"[ERROR] fetch_answers: {e}")
        return []

def extract_function_names_from_cpp(cpp_file):
    """
    使用正则提取所有函数名，并过滤掉 C++ 关键字。
    匹配形如 “返回类型 函数名(…) {”。
    """
    # 允许匹配 . : < > 在返回类型中
    pattern = re.compile(r'\b[\w\.:<>]+\s+([A-Za-z_]\w*)\s*\([^)]*\)\s*\{')
    content = open(cpp_file, encoding='utf-8').read()
    names = {m.group(1) for m in pattern.finditer(content)}
    cpp_keywords = {
        "alignas","alignof","and","and_eq","asm","auto","bitand","bitor","bool","break",
        "case","catch","char","char16_t","char32_t","class","compl","const","constexpr",
        "const_cast","continue","decltype","default","delete","do","double","dynamic_cast",
        "else","enum","explicit","export","extern","false","float","for","friend","goto",
        "if","inline","int","long","mutable","namespace","new","noexcept","not","not_eq",
        "nullptr","operator","or","or_eq","private","protected","public","register",
        "reinterpret_cast","return","short","signed","sizeof","static","static_assert",
        "static_cast","struct","switch","template","this","thread_local","throw","true",
        "try","typedef","typeid","typename","union","unsigned","using","virtual","void",
        "volatile","wchar_t","while","xor","xor_eq"
    }
    return [n for n in names if n not in cpp_keywords]

def process_cpp_file(cpp_file, target_pl, output_base_folder):
    """
    对单个 .cpp 文件进行处理：
      1. 提取函数名
      2. 对每个函数名执行“Google CSE 搜索 → 抓第一个问题 → 抓答案”
      3. 仅当有至少一个成功结果时，才写输出 JSON
    """
    funcs = extract_function_names_from_cpp(cpp_file)
    if not funcs:
        logger.info(f"No functions found in {cpp_file}")
        return

    base = os.path.splitext(os.path.basename(cpp_file))[0]
    out_dir = os.path.join(output_base_folder, f"{target_pl}_function_results")
    os.makedirs(out_dir, exist_ok=True)
    out_file = os.path.join(out_dir, base + "_results.json")
    if os.path.exists(out_file):
        logger.info(f"Skipping already processed: {base}")
        return

    results = []
    for func in funcs:
        query = f"{func} {target_pl}"
        logger.info(f"Processing function `{func}` → query: \"{query}\"")

        q = search_stackoverflow_question(query)
        if not q:
            results.append({"function": func, "status": "failed", "error": "no question found"})
            continue

        answers = fetch_answers(q["question_id"])
        if not answers:
            results.append({"function": func, "status": "failed", "error": "no answers"})
            continue

        results.append({
            "function": func,
            "status": "success",
            "question": q,
            "answers": answers
        })

    # 只有当至少有一个成功，才保存 JSON
    if any(r["status"] == "success" for r in results):
        with open(out_file, "w", encoding="utf-8") as f:
            json.dump(results, f, indent=4, ensure_ascii=False)
        logger.info(f"Results saved to: {out_file}")
    else:
        logger.info(f"No successful results for {base}, skipping JSON save.")

def process_folder(input_folder, output_base_folder):
    """
    遍历 input_folder 下所有 .cpp 文件，
    分别以 java 和 py 为目标语言处理。
    """
    target_languages = ["Java", "Python", "C++"]
    os.makedirs(output_base_folder, exist_ok=True)
    for target_pl in target_languages:
        logger.info(f"=== Processing target language: {target_pl} ===")
        for root, dirs, files in os.walk(input_folder):
            for fn in files:
                if fn.endswith(".cpp"):
                    process_cpp_file(
                        cpp_file=os.path.join(root, fn),
                        target_pl=target_pl,
                        output_base_folder=output_base_folder
                    )

if __name__ == "__main__":
    INPUT_FOLDER = "cpp"
    OUTPUT_BASE_FOLDER = "function_stackoverflow_answers"
    process_folder(INPUT_FOLDER, OUTPUT_BASE_FOLDER)
