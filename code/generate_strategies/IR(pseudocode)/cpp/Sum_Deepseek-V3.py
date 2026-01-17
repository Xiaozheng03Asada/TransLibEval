
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
        logging.FileHandler("code_summary_log.txt", mode='w', encoding='utf-8')
    ]
)
logger = logging.getLogger()

# 定义输入文件夹路径和输出文件夹
input_folder = "cpp"  # Python 源文件所在的文件夹
output_folder = "Deepseek_cpp_sum"  # 生成的文本文件将保存在该文件夹

# 创建输出文件夹（如果不存在）
if not os.path.exists(output_folder):
    os.makedirs(output_folder)


# 定义用于生成伪代码的 prompt 模板
def generate_prompt(source_code, class_name):
    return f"""

    Please analyze the following code and generate the corresponding pseudocode. The pseudocode should not reflect any specific language syntax or implementation details, and should focus solely on the core logic and steps of the algorithm. The pseudocode should be structured logically, describing the sequence of operations, decision-making processes, and function calls in a clear and understandable manner.

    Write only the pseudocode without any additional explanations or details.

    Class name: {class_name}. The Class name needs to appear

    Next, I will provide the source code; you must not directly mention the source code in your response:
    {source_code}
    """


# 提取类名的函数
def extract_class_name(source_code):
    match = re.search(r'class\s+(\w+)', source_code)
    if match:
        return match.group(1)
    else:
        return "UnknownClass"  # 如果没有找到类名，返回一个默认的类名


# 调用 DeepSeek-V3 API 进行分析，返回伪代码
def analyze_code_with_deepseek(source_code, class_name, retries=5):
    prompt = generate_prompt(source_code, class_name)

    # 更新后的模型调用部分
    url = "https://qianfan.baidubce.com/v2/chat/completions"  # 更新后的 URL

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
            response = requests.post(url, headers=headers, data=payload, timeout=60)
            response.raise_for_status()  # 检查请求是否成功
            response_result = response.json().get("choices", [{}])[0].get("message", {}).get("content", "")
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


# 处理每个 cpp 文件并生成伪代码
def process_cpp_file(cpp_file):
    base_name = os.path.basename(cpp_file).replace(".cpp", "")
    output_file_path = os.path.join(output_folder, f"{base_name}.txt")

    # 如果文件已存在，则跳过
    if os.path.exists(output_file_path):
        logger.info(f"File '{output_file_path}' already exists, skipping analysis.")
        return

    # 读取 cpp 文件内容
    with open(cpp_file, 'r', encoding='utf-8') as file:
        source_code = file.read()

    # 提取类名
    class_name = extract_class_name(source_code)

    # 调用 DeepSeek-V3 API 获取伪代码
    logger.debug(f"Analyzing file: {cpp_file}")
    pseudocode = analyze_code_with_deepseek(source_code, class_name)

    # 只有在分析成功的情况下才保存结果
    if pseudocode:
        with open(output_file_path, 'w', encoding='utf-8') as out_file:
            out_file.write(pseudocode)
        logger.debug(f"Analysis result saved: {output_file_path}")
    else:
        logger.error(f"Analysis failed for file: {cpp_file}, skipping saving.")


# 主函数
def main():
    # 遍历文件夹中的所有 cpp 文件
    for cpp_file_name in os.listdir(input_folder):
        if not cpp_file_name.endswith(".cpp"):
            continue
        cpp_file_path = os.path.join(input_folder, cpp_file_name)
        logger.debug(f"Processing file: {cpp_file_path}")

        # 处理每个 cpp 文件
        process_cpp_file(cpp_file_path)

    logger.info("All files have been processed, and analysis results have been saved.")


if __name__ == "__main__":
    main()
