import os
import json
import re
import logging
import time
import qianfan  # 导入 qianfan 库

import os

# 设置安全认证 AK/SK 鉴权，通过环境变量方式初始化
os.environ["XXXX"] = "xxxx"
os.environ["XXXX"] = "xxxx"


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

# 初始化 Qianfan Completion 客户端
comp = qianfan.Completion()

# 定义输入文件夹路径和输出文件夹
input_folder = "cpp"  # cpp 源文件所在的文件夹
output_folder = "Meta-Llama-3-8B_cpp_sum"  # 生成的文本文件将保存在该文件夹

# 创建输出文件夹（如果不存在）
if not os.path.exists(output_folder):
    os.makedirs(output_folder)

# 定义用于生成伪代码的 prompt 模板
def generate_prompt(source_code, class_name):
    return f"""
    Please read the following source code for the class '{class_name}' and provide a step-by-step chain of thought that describes the logical flow and algorithmic steps. 

    Focus on the conceptual process rather than language-specific syntax. 
    Do not quote the exact source code.

    Class name: {class_name}. The Class name needs to appear

    Here is the code，which provides only a step-by-step chain of thought:
    {source_code}
    """


# 提取类名的函数
def extract_class_name(source_code):
    match = re.search(r'class\s+(\w+)', source_code)
    if match:
        return match.group(1)
    else:
        return "UnknownClass"  # 如果没有找到类名，返回一个默认的类名


# 修改后的 API 调用函数，使用 Meta-Llama-3-8B模型
def analyze_code_with_meta_llama(source_code, class_name, retries=5):
    prompt = generate_prompt(source_code, class_name)

    # 设置对话消息
    messages = [
        {"role": "system", "content": "You are a code analysis assistant that provides structured summaries."},
        {"role": "user", "content": prompt}
    ]

    for attempt in range(retries):
        try:
            # 调用 Qianfan API 使用 Meta-Llama-3-8B模型
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


# 以下部分保持不变
def process_cpp_file(cpp_file):
    base_name = os.path.basename(cpp_file).replace(".cpp", "")
    output_file_path = os.path.join(output_folder, f"{base_name}.txt")

    if os.path.exists(output_file_path):
        logger.info(f"File '{output_file_path}' already exists, skipping analysis.")
        return

    with open(cpp_file, 'r', encoding='utf-8') as file:
        source_code = file.read()

    # 提取类名
    class_name = extract_class_name(source_code)

    logger.debug(f"Analyzing file: {cpp_file}")
    summary = analyze_code_with_meta_llama(source_code, class_name)

    if summary:
        with open(output_file_path, 'w', encoding='utf-8') as out_file:
            out_file.write(summary)
        logger.debug(f"Analysis result saved: {output_file_path}")
    else:
        logger.error(f"Analysis failed for file: {cpp_file}, skipping saving.")


def main():
    for cpp_file_name in os.listdir(input_folder):
        if not cpp_file_name.endswith(".cpp"):
            continue
        cpp_file_path = os.path.join(input_folder, cpp_file_name)
        logger.debug(f"Processing file: {cpp_file_path}")
        process_cpp_file(cpp_file_path)
    logger.info("All files processed, results saved.")


if __name__ == "__main__":
    main()
