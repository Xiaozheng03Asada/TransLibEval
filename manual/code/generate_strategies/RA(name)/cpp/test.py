import os


def remove_markdown_code_blocks(file_content: str) -> str:
    """
    删除文件中的 markdown 代码块标识（即删除"```python"和"```"）
    保留代码的缩进不变
    """
    # 删除 markdown 代码块标识
    lines = file_content.splitlines()
    lines = [line for line in lines if not line.startswith("```")]

    # 将剩余的行重新连接成一个字符串
    return "\n".join(lines)


def process_python_files_in_folder(folder_path: str):
    """
    处理文件夹中的所有 Python 文件，提取代码块并去除 markdown 标识
    并将处理后的内容覆盖原文件
    """
    # 获取文件夹中的所有文件
    for filename in os.listdir(folder_path):
        if filename.endswith(".py"):  # 只处理 .py 文件
            file_path = os.path.join(folder_path, filename)

            # 打开并读取文件内容
            with open(file_path, 'r', encoding='utf-8') as file:
                file_content = file.read()

            # 去除 markdown 代码块标识
            cleaned_content = remove_markdown_code_blocks(file_content)

            # 将处理后的内容保存回原文件
            with open(file_path, 'w', encoding='utf-8') as file:
                file.write(cleaned_content)

            # 可选：打印文件处理结果
            print(f"已处理并替换文件: {filename}")
            print("=" * 40)


# 指定文件夹路径
folder_path = 'DeepSeek-V3/cpp_to_python'

# 处理文件夹中的 Python 文件
process_python_files_in_folder(folder_path)
