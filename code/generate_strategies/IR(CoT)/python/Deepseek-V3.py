
import sys
from pathlib import Path

_TRANSLIB_ROOT = Path(__file__).resolve()
for _parent in _TRANSLIB_ROOT.parents:
    if (_parent / "README.md").exists():
        if str(_parent) not in sys.path:
            sys.path.insert(0, str(_parent))
        break
del _parent, _TRANSLIB_ROOT
from translib.providers import get_deepseek_headers


import os

# --- 认证信息（环境变量） ---
os.environ["QIANFAN_ACCESS_KEY"] = os.environ.get("QIANFAN_ACCESS_KEY", "")
os.environ["QIANFAN_SECRET_KEY"] = os.environ.get("QIANFAN_SECRET_KEY", "")

import json
import requests
import logging
import time
import re

# 设置日志记录器
logging.basicConfig(
    level=logging.DEBUG,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.StreamHandler(),
        logging.FileHandler("code_generation_log.txt", mode='w', encoding='utf-8')
    ]
)
logger = logging.getLogger()

# 定义输入文件夹路径和输出文件夹
input_folder = "Deepseek_python_sum"  # 包含功能梗概的文件夹
output_folder_root = "Deepseek-v3"  # 顶级文件夹，用于存放生成的代码文件夹
output_folder_cpp = "generated_cpp"  # 生成的 C++ 文件将保存在该子文件夹
output_folder_java = "generated_java"  # 生成的 Java 文件将保存在该子文件夹

# 创建输出文件夹（如果不存在），首先创建顶级文件夹
os.makedirs(output_folder_root, exist_ok=True)
os.makedirs(os.path.join(output_folder_root, output_folder_cpp), exist_ok=True)
os.makedirs(os.path.join(output_folder_root, output_folder_java), exist_ok=True)


# 定义用于生成代码的 prompt 模板
def generate_prompt_for_code(language, summary):
    return f"""
Please generate the {language} code that implements the following functionality:

{summary}

Please ensure the code is complete and correctly follows the syntax and conventions for {language}, without including simple usage examples or test code. The code should directly implement the required functionality as described above.
"""


# 调用 DeepSeek-V3 API 进行代码生成
def generate_code_with_deepseek(summary, language, retries=5):
    prompt = generate_prompt_for_code(language, summary)

    # 更新后的模型调用部分
    url = "https://qianfan.baidubce.com/v2/chat/completions"  # DeepSeek-V3 的 URL

    payload = json.dumps({
        "model": "deepseek-v3",  # 确保模型名称正确
        "messages": [
            {
                "role": "user",
                "content": prompt
            }
        ],
        "temperature": 0.01,
        "top_p": 0.95,
        "disable_search": False,
        "enable_citation": False
    }, ensure_ascii=False)

    headers = get_deepseek_headers()

    # 尝试进行请求，最多重试 retries 次
    for attempt in range(retries):
        try:
            response = requests.post(url, headers=headers, data=payload, timeout=200)
            response.raise_for_status()  # 检查请求是否成功
            response_result = response.json().get("choices", [{}])[0].get("message", {}).get("content", "")

            # 打印返回的内容，便于调试
            logger.debug(f"API response: {response_result}")

            if response_result:
                return response_result.strip()
            else:
                logger.error(f"API returned an empty response in attempt {attempt + 1}/{retries}.")
                return None
        except requests.exceptions.RequestException as e:
            logger.error(f"API request failed (attempt {attempt + 1}/{retries}): {e}")
            if attempt < retries - 1:
                time.sleep(5)  # 等待5秒后重试
            else:
                logger.error(f"Failed after {retries} attempts.")
                return None


# 提取目标语言代码块
def extract_code_block(response_text):
    # 改进后的正则表达式，匹配任意语言的代码块并去掉语言标识符
    pattern = r"```[\s\S]*?```"  # 匹配以 ``` 开头并以 ``` 结束的代码块
    match = re.search(pattern, response_text)
    if match:
        # 提取代码块并去除代码块标记，移除语言标识符行
        code = match.group(0).strip().strip("```").strip()
        # 移除第一行语言标识符
        code_lines = code.split('\n')
        if code_lines:
            code_lines = code_lines[1:]  # 去掉第一行的语言标识符
            return '\n'.join(code_lines).strip()
    return None


# 读取并处理每个文本文件，生成相应的目标语言代码
def process_summary_file(summary_file_path, target_language):
    # 检查目标文件夹中是否已经存在该代码文件，如果存在则跳过
    base_name = os.path.basename(summary_file_path).replace(".txt",
                                                            f".{'cpp' if target_language == 'C++' else target_language.lower()}")
    output_folder = output_folder_cpp if target_language == 'C++' else output_folder_java
    output_file_path = os.path.join(output_folder_root, output_folder, base_name)

    if os.path.exists(output_file_path):
        logger.info(f"File '{output_file_path}' already exists, skipping code generation.")
        return

    # 读取文本文件内容
    with open(summary_file_path, 'r', encoding='utf-8') as file:
        summary = file.read()

    # 调用 DeepSeek-V3 API 生成目标语言代码
    logger.debug(f"Generating {target_language} code for file: {summary_file_path}")
    generated_code = generate_code_with_deepseek(summary, target_language)

    if generated_code:
        # 提取代码块
        extracted_code = extract_code_block(generated_code)
        if extracted_code:
            # 保存生成的代码到相应文件夹
            with open(output_file_path, 'w', encoding='utf-8') as out_file:
                out_file.write(extracted_code)
            logger.debug(f"Generated {target_language} code saved to: {output_file_path}")
        else:
            logger.error(f"Failed to extract {target_language} code for {summary_file_path}.")
    else:
        logger.error(f"Code generation failed for {summary_file_path}.")


# 主函数
def main():
    # 遍历 python_sum 文件夹中的所有文本文件
    for summary_file_name in os.listdir(input_folder):
        if not summary_file_name.endswith(".txt"):
            continue
        summary_file_path = os.path.join(input_folder, summary_file_name)
        logger.debug(f"Processing summary file: {summary_file_path}")

        # 生成 C++ 代码
        process_summary_file(summary_file_path, "C++")
        # 生成 Java 代码
        process_summary_file(summary_file_path, "Java")

    logger.info("All summary files have been processed, and code has been generated.")


if __name__ == "__main__":
    main()
