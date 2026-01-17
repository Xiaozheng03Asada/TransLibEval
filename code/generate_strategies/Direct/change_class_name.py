import os
import re

def extract_class_name_from_python(python_file_path):
    """提取 Python 文件中的类名"""
    with open(python_file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    # 正则表达式提取 class 名称
    match = re.search(r'class (\w+)', content)
    if match:
        return match.group(1)
    return None

def rename_java_classes_to_python(java_folder_path, python_folder_path):
    """将 Java 类名修改为对应的 Python 类名"""
    # 遍历python文件夹中的每个文件
    for root, _, files in os.walk(python_folder_path):
        for file in files:
            if file.endswith('.py'):
                python_file_path = os.path.join(root, file)
                class_name = extract_class_name_from_python(python_file_path)
                if class_name:
                    # 查找对应的Java文件
                    java_file_path = os.path.join(java_folder_path, file.replace('.py', '.java'))
                    if os.path.exists(java_file_path):
                        # 读取 Java 文件内容
                        with open(java_file_path, 'r', encoding='utf-8') as f:
                            java_content = f.read()

                        # 替换类名
                        java_content_updated = re.sub(r'public class \w+', f'public class {class_name}', java_content)

                        # 写回修改后的内容
                        with open(java_file_path, 'w', encoding='utf-8') as f:
                            f.write(java_content_updated)
                            print(f'Updated class name in {java_file_path}')

# 设置 Java 和 Python 文件夹路径
java_folder_path = 'java'
python_folder_path = 'python'

rename_java_classes_to_python(java_folder_path, python_folder_path)
