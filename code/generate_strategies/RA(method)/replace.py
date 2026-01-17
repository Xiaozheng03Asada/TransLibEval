import os


def remove_translated_suffix(directory, extensions):
    """
    遍历指定目录下的所有文件，将指定后缀且文件名包含 '_translated' 的文件重命名，移除 '_translated' 部分。

    :param directory: 目标文件夹路径
    :param extensions: 要处理的文件扩展名列表，例如 ['.py', '.cpp', '.java']
    """
    if not os.path.isdir(directory):
        print(f"警告: 目录不存在: {directory}")
        return
    # 遍历目录
    for root, dirs, files in os.walk(directory):
        for filename in files:
            name, ext = os.path.splitext(filename)
            # 检查扩展名是否在列表中，且文件名以 '_translated' 结尾
            if ext.lower() in extensions and name.endswith('_translated'):
                new_name = name[:-len('_translated')] + ext
                old_path = os.path.join(root, filename)
                new_path = os.path.join(root, new_name)
                try:
                    os.rename(old_path, new_path)
                    print(f"重命名: '{old_path}' -> '{new_path}'")
                except Exception as e:
                    print(f"重命名失败: '{old_path}' -> '{new_path}'，错误: {e}")


if __name__ == '__main__':
    # 根目录，请替换为实际路径
    root_dir = r"/Users/asada/Documents/MyWorkplace/[检索-全文件]/translations/CodeLlama-7b-Instruct"
    # 固定的六个子文件夹名称
    subdirs = [
        "C++_to_Java",
        "C++_to_Python",
        "Java_to_C++",
        "Java_to_Python",
        "Python_to_C++",
        "Python_to_Java",
    ]
    # 要处理的文件扩展名
    extensions = ['.py', '.cpp', '.java']

    for sub in subdirs:
        directory = os.path.join(root_dir, sub)
        print(f"正在处理文件夹: {directory}")
        remove_translated_suffix(directory, extensions)