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

# 设置安全认证 AK/SK 鉴权，通过环境变量方式初始化

# 设置日志记录器
logging.basicConfig(
    level=logging.INFO,  # 调整为 INFO 级别减少调试输出
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
input_folder = "CodeLlama-7b_java_sum"
output_folder_root = "CodeLlama-7b-Instruct"
output_folder_python = "generated_python"
output_folder_cpp = "generated_cpp"

os.makedirs(output_folder_root, exist_ok=True)
os.makedirs(os.path.join(output_folder_root, output_folder_python), exist_ok=True)
os.makedirs(os.path.join(output_folder_root, output_folder_cpp), exist_ok=True)


def generate_prompt_for_code(language, summary):
    return f"""
    Please generate the {language} code that implements the following functionality:

    {summary}

    Please ensure the code is complete and correctly follows the syntax and conventions for {language}, without including simple usage examples or test code. The code should directly implement the required functionality as described above.
    """


def generate_code_with_meta_llama(summary, language, retries=5):
    prompt = generate_prompt_for_code(language, summary)
    logger.info(f"生成{language}代码的提示词（摘要前200字符）: {prompt[:200]}...")

    for attempt in range(retries):
        try:
            logger.info(f"尝试调用API（第{attempt + 1}次，共{retries}次）")
            response = comp.do(
                model="CodeLlama-7b-Instruct",
                prompt=prompt,
                temperature=0.01,
                top_p=0.95
            )

            # 解析响应结果
            if "body" in response and "result" in response["body"]:
                generated_text = response["body"]["result"].strip()
                logger.info(f"API响应成功（第{attempt + 1}次调用）")
                logger.debug(f"原始响应内容：\n{generated_text}")
                return generated_text
            else:
                logger.error(f"API响应结构异常：{json.dumps(response, indent=2, ensure_ascii=False)}")
                raise ValueError("无效的API响应结构")

        except Exception as e:
            logger.error(f"API请求失败（第{attempt + 1}次尝试）: {str(e)}")
            if attempt < retries - 1:
                time.sleep(5)

    logger.error(f"所有{retries}次尝试均失败")
    return None


def extract_code_block(response_text):
    try:
        # 改进正则表达式以处理不同代码块格式
        code_pattern = r"```(?:[a-zA-Z]+)?\n([\s\S]*?)\n```"
        match = re.search(code_pattern, response_text)
        if match:
            extracted_code = match.group(1).strip()
            logger.info("成功从响应中提取代码块")
            logger.debug(f"提取的代码内容（前200字符）:\n{extracted_code[:200]}...")
            return extracted_code
        else:
            logger.warning("未找到代码块标记，尝试解析纯代码内容")
            # 尝试匹配无标记的代码内容
            code_lines = []
            for line in response_text.split('\n'):
                if line.strip().startswith(('import ', 'public ', 'class ', 'def ', 'function ')):
                    code_lines.append(line)
            if code_lines:
                return '\n'.join(code_lines)
            logger.error("无法识别代码结构，返回原始内容")
            return response_text
    except Exception as e:
        logger.error(f"代码解析异常：{str(e)}")
        return None


def process_summary_file(summary_file_path, target_language):
    base_name = os.path.basename(summary_file_path).replace(
        ".txt", f".{'py' if target_language == 'Python' else 'cpp'}"
    )
    output_folder = output_folder_python if target_language == 'Python' else output_folder_cpp
    output_file_path = os.path.join(output_folder_root, output_folder, base_name)

    if os.path.exists(output_file_path):
        logger.info(f"跳过已存在文件：{output_file_path}")
        return

    with open(summary_file_path, 'r', encoding='utf-8') as file:
        summary = file.read()

    logger.info(f"\n{'=' * 40}\n开始处理文件：{summary_file_path}\n目标语言：{target_language}")
    generated_code = generate_code_with_meta_llama(summary, target_language)

    if generated_code:
        extracted_code = extract_code_block(generated_code)
        if extracted_code:
            try:
                with open(output_file_path, 'w', encoding='utf-8') as out_file:
                    out_file.write(extracted_code)
                logger.info(f"成功保存代码文件：{output_file_path}")
                logger.debug(f"代码预览：\n{extracted_code[:300]}...")  # 显示前300字符
            except Exception as e:
                logger.error(f"文件保存失败：{str(e)}")
        else:
            logger.error(f"代码提取失败，原始响应：\n{generated_code}")
    else:
        logger.error("代码生成失败，无有效响应")


def main():
    logger.info("\n" + "=" * 60)
    logger.info("代码生成任务启动")
    logger.info(f"输入目录：{input_folder}")
    logger.info(f"输出目录：{output_folder_root}")

    processed_files = 0
    for summary_file_name in os.listdir(input_folder):
        if not summary_file_name.endswith(".txt"):
            continue

        summary_file_path = os.path.join(input_folder, summary_file_name)

        # 生成Python代码
        logger.info(f"\n{'#' * 30} 处理Python版本 {'#' * 30}")
        process_summary_file(summary_file_path, "Python")

        # 生成C++代码
        logger.info(f"\n{'#' * 30} 处理C++版本 {'#' * 30}")
        process_summary_file(summary_file_path, "C++")

        processed_files += 1

    logger.info(f"\n{'=' * 60}\n任务完成！共处理文件数：{processed_files}")


if __name__ == "__main__":
    main()
