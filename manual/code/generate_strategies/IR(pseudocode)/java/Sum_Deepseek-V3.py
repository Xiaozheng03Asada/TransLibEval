import os
import json
import requests
import logging
import time
import re  # 导入正则表达式模块

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
input_folder = "java"  # Java 源文件所在的文件夹（修改为 Java 文件夹）
output_folder = "Deepseek_java_sum"  # 生成的文本文件将保存在该文件夹

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

# 获取 Java 文件中的类名
def extract_class_name(source_code):
    # 匹配类定义，获取类名（适应 Java 中可能的 public 或 class 关键字）
    match = re.search(r'class\s+(\w+)|public\s+class\s+(\w+)', source_code)
    if match:
        # 如果匹配到了类名，返回第一个捕获的组
        return match.group(1) or match.group(2)
    return "UnnamedClass"  # 如果没有找到类名，返回默认值


# 调用 DeepSeek-V3 API 进行分析，返回代码纲要
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

    headers = {
        'Content-Type': 'application/json',
        'appid': '',  # 填入正确的 appid
        'Authorization': 'Bearer xxxx'
    }

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


# 处理每个 Java 文件并生成代码纲要
def process_java_file(java_file):
    base_name = os.path.basename(java_file).replace(".java", "")
    output_file_path = os.path.join(output_folder, f"{base_name}.txt")

    # 如果文件已存在，则跳过
    if os.path.exists(output_file_path):
        logger.info(f"File '{output_file_path}' already exists, skipping analysis.")
        return

    # 读取 Java 文件内容
    with open(java_file, 'r', encoding='utf-8') as file:
        source_code = file.read()

    # 提取类名
    class_name = extract_class_name(source_code)

    # 调用 DeepSeek-V3 API 获取代码纲要
    logger.debug(f"Analyzing file: {java_file}")
    summary = analyze_code_with_deepseek(source_code, class_name)

    # 只有在分析成功的情况下才保存结果
    if summary:
        with open(output_file_path, 'w', encoding='utf-8') as out_file:
            out_file.write(summary)
        logger.debug(f"Analysis result saved: {output_file_path}")
    else:
        logger.error(f"Analysis failed for file: {java_file}, skipping saving.")


# 主函数
def main():
    # 遍历文件夹中的所有 Java 文件
    for java_file_name in os.listdir(input_folder):
        if not java_file_name.endswith(".java"):
            continue
        java_file_path = os.path.join(input_folder, java_file_name)
        logger.debug(f"Processing file: {java_file_path}")

        # 处理每个 Java 文件
        process_java_file(java_file_path)

    logger.info("All files have been processed, and analysis results have been saved.")


if __name__ == "__main__":
    main()
