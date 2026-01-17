import os
import re
import json


def find_third_party_apis(cpp_code):
    """ 提取第三方 API 并返回一个映射的格式 """
    # 定义正则表达式来匹配第三方 API（排除 std）
    pattern = r'([a-zA-Z0-9_]+::[a-zA-Z0-9_]+)'

    # 使用正则表达式查找所有匹配项
    matches = re.findall(pattern, cpp_code)

    # 过滤掉 std 的 API 并将 '::' 替换为 '.'
    third_party_apis = [match.replace("::", ".") for match in matches if 'std::' not in match]

    # 去除重复的函数调用
    third_party_apis = list(set(third_party_apis))

    return third_party_apis


def extract_alias_map(cpp_code):
    """ 提取第三方库和别名的映射 """
    # 在此示例中，我们假设直接从头文件中提取并映射第三方库
    alias_map = {}

    # 假设通过一些头文件识别第三方库（这里只是一个简单的示例，实际代码可能更复杂）
    includes = re.findall(r'#include\s+<([^>]+)>', cpp_code)

    for include in includes:
        if 'boost' in include:
            alias_map['boost'] = 'boost'
        # 可以添加其他库处理，如 'opencv' 等
        # 这里可以继续扩展对于其他第三方库的识别

    return alias_map


def process_cpp_file(input_file_path):
    """ 处理单个 C++ 文件并返回别名映射和函数调用 """
    with open(input_file_path, "r", encoding="utf-8") as file:
        cpp_code = file.read()

    # 提取第三方 API
    function_calls = find_third_party_apis(cpp_code)

    # 提取别名映射
    alias_map = extract_alias_map(cpp_code)

    return {
        "alias_map": alias_map,
        "function_calls": function_calls
    }


def process_folder(input_folder, output_folder):
    """
    处理文件夹中的所有 C++ 文件，提取第三方 API 调用并保存为单独的 JSON 文件。

    :param input_folder: 输入文件夹路径
    :param output_folder: 输出文件夹路径
    """
    os.makedirs(output_folder, exist_ok=True)

    for filename in os.listdir(input_folder):
        input_file_path = os.path.join(input_folder, filename)

        if not filename.endswith(".cpp"):
            continue

        if os.path.isfile(input_file_path):
            # 处理 C++ 文件
            analysis_result = process_cpp_file(input_file_path)

            # 将结果保存为单独的 JSON 文件
            output_file_path = os.path.join(output_folder, f"{os.path.splitext(filename)[0]}.json")
            with open(output_file_path, "w", encoding="utf-8") as json_file:
                json.dump(analysis_result, json_file, ensure_ascii=False, indent=4)

            print(f"[INFO] {filename} 的 API 调用结果已保存到 {output_file_path}")


if __name__ == "__main__":
    # 输入文件夹路径
    INPUT_FOLDER = "cpp"  # 修改为您的输入文件夹路径
    # 输出文件夹路径
    OUTPUT_FOLDER = "cpp_api_results"  # 修改为您的输出文件夹路径

    process_folder(INPUT_FOLDER, OUTPUT_FOLDER)
