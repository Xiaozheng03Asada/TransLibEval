import os
from tree_sitter import Language, Parser
import tree_sitter_cpp as tscpp

# 加载 C++ 语言
CPP_LANGUAGE = Language(tscpp.language())

# 创建解析器并设置语言为 C++
parser = Parser(CPP_LANGUAGE)


def find_main_function(root_node):
    """查找并返回 main 函数节点"""
    for child in root_node.children:
        if child.type == 'function_definition':
            for descendant in child.children:
                if descendant.type == 'function_declarator':
                    for subchild in descendant.children:
                        if subchild.type == 'identifier' and subchild.text.decode() == 'main':
                            return child
        found = find_main_function(child)
        if found:
            return found
    return None


def get_functions_and_calls(root_node):
    """获取所有函数名及被调用的函数名，包括在类内的函数"""
    function_names = set()
    called_functions = set()

    def traverse(node):
        if node.type == 'function_definition':
            for child in node.children:
                if child.type == 'function_declarator':
                    for subchild in child.children:
                        if subchild.type in {'identifier', 'field_identifier'}:
                            func_name = subchild.text.decode()
                            function_names.add(func_name)

        elif node.type == 'call_expression':
            for child in node.children:
                if child.type == 'identifier':
                    called_func = child.text.decode()
                    called_functions.add(called_func)

        if node.type == 'class_specifier':
            for child in node.children:
                if child.type == 'field_declaration_list':
                    for grandchild in child.children:
                        traverse(grandchild)

        for child in node.children:
            traverse(child)

    traverse(root_node)
    return function_names, called_functions

def rename_function(function_name, code):
    """重命名指定的主要功能函数为 'abC123'，支持全局和类内函数"""
    tree = parser.parse(code.encode())
    root_node = tree.root_node
    modified_code_lines = code.splitlines()

    def find_and_rename_function(node):
        for child in node.children:
            if child.type == 'function_definition':
                # 检查全局或类内的函数定义
                for grandchild in child.children:
                    if grandchild.type == 'function_declarator':
                        for subchild in grandchild.children:
                            if subchild.type in {'identifier', 'field_identifier'}:
                                func_name = subchild.text.decode()
                                if func_name == function_name:
                                    print(f"Renaming function '{func_name}' to 'abC123'.")
                                    # 修改函数名
                                    line_index = child.start_point[0]
                                    modified_code_lines[line_index] = modified_code_lines[line_index].replace(func_name, 'abC123')
                                    return  # 一旦找到并修改，退出函数以避免重复

            # 如果是类，继续递归检查类内的成员
            elif child.type == 'class_specifier':
                for subchild in child.children:
                    if subchild.type == 'field_declaration_list':
                        find_and_rename_function(subchild)

            # 递归检查所有子节点
            find_and_rename_function(child)

    find_and_rename_function(root_node)
    return '\n'.join(modified_code_lines)


def move_function_out_of_class(function_name, code, inp):
    """将类内的所有函数移出到类外，并删除类"""
    tree = parser.parse(code.encode())
    root_node = tree.root_node
    modified_code_lines = code.splitlines()

    def find_class_with_functions(node, m, inp):
        for child in node.children:
            if child.type == 'class_specifier':
                print(f"Found class at lines {child.start_point[0]}-{child.end_point[0]}")
                class_start_line = child.start_point[0]
                class_end_line = child.end_point[0]
                function_bodies = []

                for subchild in child.children:
                    if subchild.type == 'field_declaration_list':
                        for grandchild in subchild.children:
                            if grandchild.type == 'function_definition':
                                function_start_line = grandchild.start_point[0]
                                function_end_line = grandchild.end_point[0]

                                # 检查索引范围
                                modified_code_lines = m
                                if 0 <= function_start_line <= function_end_line < len(modified_code_lines):
                                    function_body = '\n'.join(modified_code_lines[
                                                              function_start_line:function_end_line + 1])
                                    function_bodies.append(function_body)
                                else:
                                    print(
                                        f"Warning: Invalid line range for function extraction: {function_start_line} to {function_end_line}")
                                    return code  # 返回原始代码以避免错误

                if function_bodies:
                    # 删除类框架并将函数移到类外
                    print("Moving functions out of the class.")
                    modified_code_lines = m[:class_start_line] + m[class_end_line + 1:]
                    modified_code_lines.extend(function_bodies)
                    return '\n'.join(modified_code_lines)

        # 如果没有找到类，直接返回原始代码
        return code

    modified_code = find_class_with_functions(root_node, modified_code_lines, inp)
    return modified_code if modified_code else code


def remove_main_and_modify_functions(code,i):
    # 解析代码
    tree = parser.parse(code.encode())
    root_node = tree.root_node

    # 删除 main 函数
    main_node = find_main_function(root_node)
    if main_node:
        start_byte = main_node.start_byte
        end_byte = main_node.end_byte
        code = code[:start_byte] + code[end_byte:]

        # 重新解析代码以排除删除后的影响
        tree = parser.parse(code.encode())
        root_node = tree.root_node

    # 查找主要功能函数
    function_names, called_functions = get_functions_and_calls(root_node)
    main_functions = function_names - called_functions

    # 处理主要功能函数
    if len(main_functions) == 1:
        main_function_name = list(main_functions)[0]
        modified_code = rename_function(main_function_name, code)
    else:
        with open("log.txt", "a") as log_file:
            log_file.write(f"{i}:Number of main functions: {len(main_functions)}. No changes made.\n")
        modified_code = code
    modified_code = move_function_out_of_class("abC123", modified_code,i)
    return modified_code


def process_folder(input_folder, output_folder):
    """处理指定文件夹内的所有文件并保存到另一个文件夹中"""
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)

    for filename in os.listdir(input_folder):
        input_file_path = os.path.join(input_folder, filename)
        output_file_path = os.path.join(output_folder, filename)

        # 只处理 C++ 文件
        if filename.endswith('.cpp') or filename.endswith('.h'):
            print(f"Processing file: {input_file_path}")

            with open(input_file_path, 'r', encoding='utf-8') as file:
                code = file.read()

            # 修改代码
            modified_code = remove_main_and_modify_functions(code,input_file_path)

            # 保存到输出文件夹
            with open(output_file_path, 'w', encoding='utf-8') as file:
                file.write(modified_code)
            print(f"Modified file saved to: {output_file_path}")


# 示例使用
input_folder = "src"  # 替换为您的输入文件夹路径
output_folder = "python_to_cpp_merge"  # 替换为您的输出文件夹路径

process_folder(input_folder, output_folder)