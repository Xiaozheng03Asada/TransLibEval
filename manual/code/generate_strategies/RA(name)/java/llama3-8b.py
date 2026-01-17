from __future__ import annotations

import os
import json
import logging
import time
import re
from pathlib import Path

# === Qianfan SDK ===
import qianfan  # pip install qianfan

# ---------------------------------------------------------------------------
# 1⃣️  认证信息（环境变量）
# ---------------------------------------------------------------------------
os.environ.setdefault("QIANFAN_ACCESS_KEY", "xxxx")
os.environ.setdefault("QIANFAN_SECRET_KEY", "xxxx")

# ---------------------------------------------------------------------------
# 2⃣️  日志设置
# ---------------------------------------------------------------------------
logging.basicConfig(
    level=logging.DEBUG,
    format="%(asctime)s - %(levelname)s - %(message)s",
    handlers=[
        logging.StreamHandler(),
        logging.FileHandler("translation_log.txt", mode="a", encoding="utf-8"),
    ],
)
logger = logging.getLogger(__name__)

# ---------------------------------------------------------------------------
# 3⃣️  目录常量
# ---------------------------------------------------------------------------
SIG_DIR = Path("signature_out")
SO_ROOT = Path("function_stackoverflow_answers")
JAVA_DIR = Path("cpp")
OUTPUT_ROOT = Path("Meta-Llama-3-8B")  # ← 顶级输出目录同步更名

# 目标语言与 StackOverflow 子目录映射
TARGETS = {
    "python": "py_function_results",
    "cpp": "cpp_function_results",
}

# 提取 code‑block 的正则
CODE_BLOCK_RE = re.compile(r"```(?:\\w+)?\n([\s\S]*?)\n```", re.MULTILINE)

# ---------------------------------------------------------------------------
# 4⃣️  Qianfan Completion 客户端（全局复用）
# ---------------------------------------------------------------------------
COMP = qianfan.Completion()
MODEL_NAME = "Meta-Llama-3-8B"  # ✅ 目标模型更换处


def translate_code(prompt: str, retries: int = 3) -> str | None:
    """使用 Meta-Llama-3-8B 生成代码实现。"""
    for attempt in range(1, retries + 1):
        try:
            resp = COMP.do(
                model=MODEL_NAME,
                prompt=prompt,
                temperature=0.01,
                top_p=0.95,
            )
            return resp["body"]["result"].strip()
        except Exception as exc:
            logger.error("翻译尝试 %s 失败: %s", attempt, exc)
            if attempt < retries:
                time.sleep(5)
    return None


# ---------------------------------------------------------------------------
# 5⃣️  StackOverflow / 签名 / Prompt 相关工具函数
# ---------------------------------------------------------------------------

def load_so_answers(base: str, target: str) -> list[str]:
    """加载与指定函数匹配的 SO 参考答案内容。"""
    path = SO_ROOT / TARGETS[target] / f"{base}_results.json"
    if not path.exists():
        logger.warning("未找到 SO 参考答案：%s", path)
        return []
    with path.open(encoding="utf-8") as fh:
        data = json.load(fh)
    answers: list[str] = []
    for rec in data:
        answers.extend(rec.get("answers", []))
    return answers


def load_signature(base: str) -> dict:
    """加载函数签名 JSON。"""
    path = SIG_DIR / f"{base}.json"
    if not path.exists():
        logger.warning("未找到签名文件：%s", path)
        return {}
    return json.loads(path.read_text(encoding="utf-8"))


def make_prompt(
    base: str,
    target: str,
    so_answers: list[str],
    signature: dict,
    java_code: str,
) -> str:
    """根据多源信息拼装大模型输入 Prompt。"""
    sig_json = json.dumps(signature, ensure_ascii=False)
    ref_impl = "\n".join(so_answers) if so_answers else java_code or "（无可用参考实现）"
    return (
        "You are a world‑class expert in code generation with deep mastery of translating "
        f"algorithmic JAVA class methods into {target} implementations.\n\n"
        "Below are the precise function signature details and either community‑sourced reference "
        "implementations or the original JAVA code as fallback. Your task is to generate clean, "
        "idiomatic, and fully functional {target} code that exactly matches the behavior.\n\n"
        "=== Function Signature & Metadata ===\n"
        f"{sig_json}\n\n"
        "=== Reference Implementation ===\n"
        f"{ref_impl}\n\n"
        f"Produce only the final {target} code. Do not include any explanations, comments, or extra text."\
        "\n\nBegin {target} code now:\n"
    )


def extract_code(translated: str) -> str:
    """从模型的纯文本输出中提取代码块；若未包裹 ```，则返回原文。"""
    m = CODE_BLOCK_RE.search(translated)
    return m.group(1) if m else translated


# ---------------------------------------------------------------------------
# 6⃣️  主流程：逐函数调用大模型 & 保存结果
# ---------------------------------------------------------------------------

def process_function(base: str, target: str) -> None:
    out_dir = OUTPUT_ROOT / f"cpp_to_{target}"
    out_dir.mkdir(parents=True, exist_ok=True)
    ext = "py" if target == "python" else "cpp"
    out_path = out_dir / f"{base}.{ext}"

    # —— 断点续跑：若目标文件已存在则跳过 ——
    if out_path.exists():
        logger.info("[%s→%s] 已存在，跳过生成：%s", base, target, out_path)
        return

    # ① 参考答案 / ② 签名 / ③ JAVA 源码
    so_ans = load_so_answers(base, target)
    sig = load_signature(base)
    java_path = JAVA_DIR / f"{base}.java"
    java_code = java_path.read_text(encoding="utf-8", errors="ignore") if java_path.exists() else ""

    prompt = make_prompt(base, target, so_ans, sig, java_code)
    logger.debug("[%s→%s] Prompt 长度：%d", base, target, len(prompt))

    translated = translate_code(prompt)
    if translated is None:
        logger.error("[%s→%s] 生成失败", base, target)
        return

    code = extract_code(translated)
    out_path.write_text(code, encoding="utf-8")
    logger.info("[%s→%s] 代码已生成并保存至 %s", base, target, out_path)


def main() -> None:
    OUTPUT_ROOT.mkdir(exist_ok=True)
    for target in TARGETS:
        for sig_file in SIG_DIR.glob("*.json"):
            base = sig_file.stem
            process_function(base, target)
    logger.info("所有函数代码生成完成。")


if __name__ == "__main__":
    main()
