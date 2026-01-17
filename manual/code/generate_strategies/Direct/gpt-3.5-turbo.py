import os
import re
import logging
import time
from openai import OpenAI

# 设置 API 密钥和基础 URL
client = OpenAI(api_key="xxxx", base_url = 'xxxx')

# 设置日志记录
logging.basicConfig(
    filename='translation_log.txt',  # 日志文件
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s'
)

# 定义输入文件夹路径和输出文件夹
input_folders = {"python": "python", "java": "java", "cpp": "cpp"}
output_folder = "gpt-3.5-turbo"
translation_dirs = [
    "python_to_java", "python_to_cpp",
    "java_to_python", "java_to_cpp",
    "cpp_to_python", "cpp_to_java"
]

# 创建输出文件夹和子文件夹（如果不存在）
if not os.path.exists(output_folder):
    os.makedirs(output_folder)
for dir_name in translation_dirs:
    os.makedirs(os.path.join(output_folder, dir_name), exist_ok=True)

# 定义目标语言的示例代码（去重后）
example_translations = {
    "python": """
        from faker import Faker

        class NameGenerator:
            def generate_name(self):
                fake = Faker()
                return fake.name()
    """,
    "java": """
        import com.github.javafaker.Faker;

        public class NameGenerator {
            public String generateName() {
                Faker faker = new Faker();
                return faker.name().fullName();
            }
        }
    """,
    "cpp": """
        #include <fakeit.hpp>
        #include <string>

        class NameGenerator {
        public:
            std::string generate_name() {
                fakeit::FakeIt fake;
                return fake.name();
            }
        };
    """
}

# 最大字符长度限制，防止超出 API 请求限制
MAX_INPUT_LENGTH = 10000  # 可以根据模型的字符限制调整

# 检查代码是否超过最大字符长度
def is_code_too_large(code):
    return len(code) > MAX_INPUT_LENGTH

# 调用 OpenAI API 进行代码翻译
def translate_code(input_code, from_lang, to_lang, max_retries=3):
    retries = 0
    while retries < max_retries:
        try:
            logging.info(f"[DEBUG] Starting translation from {from_lang} to {to_lang}.")
            print(f"[DEBUG] Starting translation from {from_lang} to {to_lang}.")

            # 获取源语言和目标语言的示例代码
            source_example = example_translations.get(from_lang.lower(), "").strip()
            target_example = example_translations.get(to_lang.lower(), "").strip()

            # 构建翻译提示 (prompt)，但不显式显示
            prompt = (
                f"Example:Translate the following {from_lang} code to {to_lang}.\n\n"
                f"Source Code:\n"
                f"{source_example}\n\n"
                f"Target Code:\n"
                f"{target_example}\n\n"
                f"Translate the following {from_lang} code to {to_lang}. Only return the translated code without any explanations or additional text.\n\n"
                f"Source Code:\n"
                f"{input_code.strip()}\n\n"
                f"Target Code:\n"
            )

            # 调用 OpenAI Chat APIt
            response = client.chat.completions.create(
                model="gpt-3.5-turbo",
                messages=[
                    {"role": "system", "content": "You are a code translation assistant. Only return the translated code without any additional explanations or text."},
                    {"role": "user", "content": prompt}
                ],
                temperature=0,
                timeout=60  # 增加超时时间
            )

            translated_code = response.choices[0].message.content
            logging.info(f"[DEBUG] Translation completed from {from_lang} to {to_lang}.")
            print(f"[DEBUG] Translation completed from {from_lang} to {to_lang}.")
            return translated_code.strip()

        except Exception as e:
            retries += 1
            logging.error(f"[ERROR] Error during translation from {from_lang} to {to_lang}: {str(e)} (Retry {retries}/{max_retries})")
            print(f"[ERROR] Error during translation from {from_lang} to {to_lang}: {str(e)} (Retry {retries}/{max_retries})")
            time.sleep(5)  # 等待 5 秒后重试
    return None

# 提取代码块
def extract_code(translated_output, pattern):
    match = re.search(pattern, translated_output, re.DOTALL)
    if match:
        return match.group(1).strip()  # 返回提取的代码块并去除首尾空白
    return translated_output  # 如果没有找到匹配的代码块，则返回原内容

# 处理每个语言文件夹并翻译代码
for lang, folder in input_folders.items():
    logging.info(f"[DEBUG] Processing files in the '{folder}' folder (detected language: {lang}).")
    print(f"[DEBUG] Processing files in the '{folder}' folder (detected language: {lang}).")

    for file_name in os.listdir(folder):
        file_path = os.path.join(folder, file_name)

        with open(file_path, 'r', encoding='utf8', errors='ignore') as file:
            code = file.read()

        # 检查代码是否过长
        if is_code_too_large(code):
            logging.warning(f"[INFO] Skipping file '{file_name}' due to input code length exceeding the limit.")
            print(f"[INFO] Skipping file '{file_name}' due to input code length exceeding the limit.")
            continue

        id = file_name.split('.')[0]  # 使用文件名作为 ID
        logging.info(f"[DEBUG] Translating file '{file_name}' with ID '{id}' from {lang}.")
        print(f"[DEBUG] Translating file '{file_name}' with ID '{id}' from {lang}.")

        translations = {}

        # 根据原始语言确定翻译方向
        if lang == "python":
            translations["python_to_java"] = ("Java", "java")
            translations["python_to_cpp"] = ("C++", "cpp")
        elif lang == "java":
            translations["java_to_python"] = ("Python", "py")
            translations["java_to_cpp"] = ("C++", "cpp")
        elif lang == "cpp":
            translations["cpp_to_python"] = ("Python", "py")
            translations["cpp_to_java"] = ("Java", "java")

        # 保存翻译代码到适当的文件夹和文件格式
        for translation_type, (to_lang, target_ext) in translations.items():
            output_dir = os.path.join(output_folder, translation_type)
            output_file_path = os.path.join(output_dir, f"{id}.{target_ext}")

            # **断点续用功能**：如果输出文件已存在，跳过翻译
            if os.path.exists(output_file_path):
                logging.info(f"[INFO] Output file '{output_file_path}' already exists. Skipping translation.")
                print(f"[INFO] Output file '{output_file_path}' already exists. Skipping translation.")
                continue

            # 定义正则表达式模式，用于从翻译结果中提取代码块。
            patterns = {
                "python_to_java": r"```java\n(.*?)\n```",  # 提取 Java 代码块
                "python_to_cpp": r"```cpp\n(.*?)\n```",   # 提取 C++ 代码块
                "java_to_python": r"```python\n(.*?)\n```",  # 提取 Python 代码块
                "java_to_cpp": r"```cpp\n(.*?)\n```",     # 提取 C++ 代码块
                "cpp_to_python": r"```python\n(.*?)\n```",  # 提取 Python 代码块
                "cpp_to_java": r"```java\n(.*?)\n```"     # 提取 Java 代码块
            }

            # 执行翻译
            translated_code = translate_code(code, lang.capitalize(), to_lang)
            if not translated_code:
                logging.error(f"[ERROR] Translation failed for {file_name}. Skipping file.")
                continue

            # 提取代码块
            pattern = patterns.get(translation_type)
            if pattern:
                extracted_code = extract_code(translated_code, pattern)  # 确保调用 extract_code 并赋值
            else:
                extracted_code = translated_code  # 如果没有定义 pattern，直接使用翻译结果

            # 保存翻译结果
            with open(output_file_path, 'w', encoding='utf8') as out_file:
                out_file.write(extracted_code)  # 确保使用 extracted_code

logging.info("All files have been processed and translations saved.")
print("All files have been processed and translations saved.")