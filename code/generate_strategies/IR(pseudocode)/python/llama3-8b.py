import sys
from pathlib import Path

_TRANSLIB_ROOT = Path(__file__).resolve()
for _parent in _TRANSLIB_ROOT.parents:
    if (_parent / "README.md").exists():
        if str(_parent) not in sys.path:
            sys.path.insert(0, str(_parent))
        break
del _parent, _TRANSLIB_ROOT

import os
import json
import requests
import logging
import time
import re
import qianfan  # 导入 qianfan 库


import os

# 设置安全认证 AK/SK 鉴权，通过环境变量方式初始化

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

# 初始化 Qianfan Completion 客户端
comp = qianfan.Completion()

# 定义输入文件夹路径和输出文件夹
input_folder = "Meta-Llama-3-8B_python_sum"  # 包含功能梗概的文件夹
output_folder_root = "Llama3-8B"  # 顶级文件夹，用于存放生成的代码文件夹
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

# 调用 Qianfan API（Meta-Llama-3-8B）进行代码生成
def generate_code_with_meta_llama(summary, language, retries=5):
    prompt = generate_prompt_for_code(language, summary)

    payload = json.dumps({
        "model": "Meta-Llama-3-8B",  # 使用 Meta-Llama-3-8B 模型
        "prompt": prompt,
        "temperature": 0.01,
        "top_p": 0.95,
        "max_tokens": 1500  # 设置最大 token 数量
    }, ensure_ascii=False)

    headers = {
        'Content-Type': 'application/json'  # 使用正确的 Qianfan API 密钥
    }

    for attempt in range(retries):
        try:
            # 调用 Qianfan API 使用 Meta-Llama-3-8B 模型
            response = comp.do(
                model="Meta-Llama-3-8B",
                prompt=prompt,
                temperature=0.01,
                top_p=0.95
            )
            summary = response["body"]["result"]
            if summary:
                return summary.strip()
            logger.error(f"API returned empty response in attempt {attempt + 1}/{retries}")
        except Exception as e:
            logger.error(f"API request failed (attempt {attempt + 1}/{retries}): {str(e)}")
            if attempt < retries - 1:
                time.sleep(5)
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

    # 调用 Meta-Llama-3-8B API 生成目标语言代码
    logger.debug(f"Generating {target_language} code for file: {summary_file_path}")
    generated_code = generate_code_with_meta_llama(summary, target_language)

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
