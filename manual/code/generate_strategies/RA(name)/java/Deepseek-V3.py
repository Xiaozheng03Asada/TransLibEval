
import os
import json
import logging
import requests
import time
from pathlib import Path
import re

# --- 认证信息（环境变量） ---
os.environ["XXXX"] = "xxxx"
os.environ["XXXX"] = "xxxx"

# --- 日志设置 ---
logging.basicConfig(
    level=logging.DEBUG,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.StreamHandler(),
        logging.FileHandler("translation_log.txt", mode='a', encoding='utf-8')
    ]
)
logger = logging.getLogger()

# --- 目录配置 ---
SIG_DIR = Path("signature_out")
SO_ROOT = Path("function_stackoverflow_answers")
JAVA_DIR = Path("java")
OUTPUT_ROOT = Path("DeepSeek-V3")

# 目标语言与 StackOverflow 子目录映射
TARGETS = {
    "python": "py_function_results",
    "cpp":   "cpp_function_results"
}

# 提取 code block 的正则
CODE_BLOCK_RE = re.compile(r"```(?:\w+)?\n([\s\S]*?)\n```")

def translate_code(prompt: str, retries: int = 3) -> str | None:
    url = "https://qianfan.baidubce.com/v2/chat/completions"
    payload = {
        "model": "deepseek-v3",
        "messages": [{"role": "user", "content": prompt}],
        "temperature": 0.01,
        "top_p": 0.95,
        "disable_search": False,
        "enable_citation": False
    }
    headers = {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer xxxx'
    }
    for attempt in range(1, retries + 1):
        try:
            resp = requests.post(url, headers=headers, json=payload, timeout=60)
            resp.raise_for_status()
            return resp.json()["choices"][0]["message"]["content"].strip()
        except Exception as e:
            logger.error(f"翻译尝试 {attempt} 失败: {e}")
            if attempt < retries:
                time.sleep(5)
    return None

def load_so_answers(base: str, target: str) -> list[str]:
    folder = SO_ROOT / TARGETS[target]
    path = folder / f"{base}_results.json"
    print(folder)
    if not path.exists():
        logger.warning(f"未找到 SO 参考答案：{path}")
        return []
    with path.open(encoding="utf-8") as f:
        data = json.load(f)
    answers = []
    for rec in data:
        answers.extend(rec.get("answers", []))
    return answers

def load_signature(base: str) -> dict:
    path = SIG_DIR / f"{base}.json"
    if not path.exists():
        logger.warning(f"未找到签名文件：{path}")
        return {}
    with path.open(encoding="utf-8") as f:
        return json.load(f)

def make_prompt(base: str, target: str, so_answers: list[str], signature: dict, java_code: str) -> str:
    sig_json = json.dumps(signature, ensure_ascii=False)
    ref_impl = "\n".join(so_answers) if so_answers else java_code or "（无可用参考实现）"
    return (
        "You are a world‑class expert in code generation with deep mastery of translating "
        f"algorithmic Java class methods into {target} implementations.\n\n"
        "Below are the precise function signature details and either community‑sourced reference implementations "
        "or the original Java code as fallback. Your task is to generate clean, idiomatic, and fully functional "
        f"{target} code that exactly matches the behavior.\n\n"
        "=== Function Signature & Metadata ===\n"
        f"{sig_json}\n\n"
        "=== Reference Implementation ===\n"
        f"{ref_impl}\n\n"
        f"Produce only the final {target} code. Do not include any explanations, comments, or extra text.\n\n"
        f"Begin {target} code now:\n"
    )

def extract_code(translated: str) -> str:
    m = CODE_BLOCK_RE.search(translated)
    return m.group(1) if m else translated

def process_function(base: str, target: str):
    out_dir = OUTPUT_ROOT / f"java_to_{target}"
    out_dir.mkdir(parents=True, exist_ok=True)
    ext = "py" if target == "python" else "cpp"
    out_path = out_dir / f"{base}.{ext}"
    # ====== checkpoint: 文件存在则跳过 ======
    if out_path.exists():
        logger.info(f"[{base}→{target}] 已存在，跳过生成：{out_path}")
        return

    so_ans = load_so_answers(base, target)
    sig    = load_signature(base)
    java_code = ""
    java_path = JAVA_DIR / f"{base}.java"
    if java_path.exists():
        java_code = java_path.read_text(encoding="utf-8", errors="ignore")

    prompt = make_prompt(base, target, so_ans, sig, java_code)
    logger.debug(f"[{base}→{target}] Prompt 长度：{len(prompt)}")
    translated = translate_code(prompt)
    if not translated:
        logger.error(f"[{base}→{target}] 生成失败")
        return

    code = extract_code(translated)
    out_path.write_text(code, encoding="utf-8")
    logger.info(f"[{base}→{target}] 代码已生成并保存至 {out_path}")

def main():
    OUTPUT_ROOT.mkdir(exist_ok=True)
    for target in TARGETS:
        for sig_file in SIG_DIR.glob("*.json"):
            base = sig_file.stem
            process_function(base, target)
    logger.info("所有函数代码生成完成。")

if __name__ == "__main__":
    main()
