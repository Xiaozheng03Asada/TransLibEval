import os
import re
import qianfan
import logging

# 设置安全认证 AK/SK 鉴权，通过环境变量方式初始化
# 请替换 "your_iam_ak" 和 "your_iam_sk" 为您的实际 A cess Key 和 Secret Key
os.environ["XXXX"] = "xxxx"
os.environ["XXXX"] = "xxxx"

# 初始化日志记录器
logging.basicConfig(
    level=logging.DEBUG,  # 设置日志级别为 DEBUG
    format='%(asctime)s - %(levelname)s - %(message)s',  # 设置日志格式
    handlers=[
        logging.StreamHandler(),  # 输出到控制台
        logging.FileHandler("translation_log.txt", mode='w', encoding='utf-8')  # 输出到文件
    ]
)
logger = logging.getLogger()

# 初始化 Qianfan Completion 客户端
comp = qianfan.Completion()

# 定义输入文件夹路径和输出文件夹
input_folders = {"python": "python", "java": "java", "cpp": "cpp"}
output_folder = "Meta-Llama-3-70B"
translation_dirs = [
    "python_to_java", "python_to_cpp",
    "java_to_python", "java_to_cpp",
    "cpp_to_python", "cpp_to_java"
]

# 创建 Deepseek 文件夹和子文件夹（如果不存在）
if not os.path.exists(output_folder):
    os.makedirs(output_folder)
for dir_name in translation_dirs:
    os.makedirs(os.path.join(output_folder, dir_name), exist_ok=True)

# 定义目标语言的示例代码
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

# 定义用于提取代码块的正则表达式模式
patterns = {
    "python_to_java": r"```(?:java|Java|JAVA)?\n([\s\S]*?)\n```",
    "python_to_cpp": r"```(?:cpp|CPP|Cpp|c\+\+|C\+\+)?\n([\s\S]*?)\n```",
    "java_to_python": r"```(?:python|Python|PYTHON|py|PY)?\n([\s\S]*?)\n```",
    "java_to_cpp": r"```(?:cpp|CPP|Cpp|c\+\+|C\+\+)?\n([\s\S]*?)\n```",
    "cpp_to_python": r"```(?:python|Python|PYTHON|py|PY)?\n([\s\S]*?)\n```",
    "cpp_to_java": r"```(?:java|Java|JAVA)?\n([\s\S]*?)\n```"
}

# 调用 Qianfan API 进行代码翻译
def translate_code(input_code, from_lang, to_lang):
    logger.debug(f"Starting translation from {from_lang} to {to_lang}.")

    # 获取源语言和目标语言的示例代码
    source_example = example_translations.get(from_lang.lower(), "").strip()
    target_example = example_translations.get(to_lang.lower(), "").strip()

    # 构建提示信息
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

    # 检查 prompt 长度是否超过限制
    if len(prompt) > 8000:
        logger.warning(f"Prompt for translation from {from_lang} to {to_lang} is too long ({len(prompt)} characters). Skipping this request.")
        return None  # 返回 None 表示跳过该翻译

    # 调用 Qianfan Completion 接口
    try:
        resp = comp.do(
            model="Meta-Llama-3-70B",
            prompt=prompt,
            temperature=0.01,
            top_p=0.95
        )
        translated_code = resp["body"]["result"]
        logger.debug(f"Translation completed from {from_lang} to {to_lang}.")
        return translated_code
    except Exception as e:
        logger.error(f"Error during translation from {from_lang} to {to_lang}: {e}")
        return None

# 提取代码块
def extract_code(translated_output, pattern):
    match = re.search(pattern, translated_output)
    if match:
        return match.group(1)
    return translated_output  # 如果没有找到匹配的代码块，则返回原内容

# 处理每个语言文件夹并翻译代码
for lang, folder in input_folders.items():
    logger.debug(f"Processing files in the '{folder}' folder (detected language: {lang}).")

    for file_name in os.listdir(folder):
        file_path = os.path.join(folder, file_name)

        with open(file_path, 'r', encoding='utf8', errors='ignore') as file:
            code = file.read()

        id = file_name.split('.')[0]  # 使用文件名作为 ID
        logger.debug(f"Translating file '{file_name}' with ID '{id}' from {lang}.")

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
                logger.info(f"Output file '{output_file_path}' already exists. Skipping translation.")
                continue

            # 执行翻译
            translated_code = translate_code(code, lang.capitalize(), to_lang)

            # 如果翻译被跳过（返回 None），则跳过保存
            if translated_code is None:
                logger.info(f"Skipping file '{file_name}' due to prompt length exceeding the limit.")
                continue

            # 提取代码块
            pattern = patterns.get(translation_type)
            extracted_code = extract_code(translated_code, pattern)

            # 保存翻译结果
            with open(output_file_path, 'w', encoding='utf8') as out_file:
                out_file.write(extracted_code)

            logger.debug(f"Translation for {id} ({translation_type}) saved to {output_file_path}.")

logger.info("All files have been processed and translations saved.")
