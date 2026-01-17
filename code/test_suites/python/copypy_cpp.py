import os
import shutil

def read_successful_programs(log_file):
    """读取日志文件中“以下程序执行成功”部分"""
    successful_programs = []
    with open(log_file, 'r', encoding='utf-8') as log:  # 指定编码为 UTF-8
        lines = log.readlines()
    
    in_success_section = False
    for line in lines:
        if "以下程序执行成功：" in line:
            in_success_section = True
            continue
        if in_success_section:
            if line.strip().startswith("-"):
                # 提取成功的程序文件名
                program = line.strip()[2:]  # 去掉前面的 "- "
                successful_programs.append(program)
            elif line.strip() == "":
                break  # 空行表示成功部分结束
    
    return successful_programs

def find_and_copy_files(successful_programs, python_folder, merge_folder, output_folder):
    """根据成功程序名寻找对应文件，并复制到新文件夹"""
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)
    
    for program in successful_programs:
        # 找 function 文件
        function_file_path = os.path.join(merge_folder, program)
        
        # 找 test 文件
        test_file_name = program.replace("function_", "test_")
        test_file_path = os.path.join(python_folder, test_file_name)
        
        # 复制文件
        if os.path.exists(function_file_path):
            shutil.copy(function_file_path, output_folder)
            print(f"已复制 {function_file_path} 到 {output_folder}")
        else:
            print(f"未找到 function 文件: {function_file_path}")
        
        if os.path.exists(test_file_path):
            shutil.copy(test_file_path, output_folder)
            print(f"已复制 {test_file_path} 到 {output_folder}")
        else:
            print(f"未找到 test 文件: {test_file_path}")

if __name__ == "__main__":
    # 设置路径
    log_file = "cpp_combined_log.txt"  # 日志文件路径
    python_folder = "python"  # Python 文件夹路径
    merge_folder = "cpp_to_python"  # Java-to-Python 文件夹路径
    output_folder = "cpp_collected_files"  # 输出文件夹路径

    # 检查文件夹是否存在
    if not os.path.exists(log_file):
        print(f"日志文件 {log_file} 不存在，请检查路径！")
        exit(1)
    if not os.path.exists(python_folder):
        print(f"Python 文件夹 {python_folder} 不存在，请检查路径！")
        exit(1)
    if not os.path.exists(merge_folder):
        print(f"Java-to-Python 文件夹 {merge_folder} 不存在，请检查路径！")
        exit(1)

    # 读取成功的程序名
    successful_programs = read_successful_programs(log_file)
    
    # 如果没有成功的程序
    if not successful_programs:
        print("日志文件中没有找到成功的程序！")
        exit(1)
    
    # 查找并复制文件
    find_and_copy_files(successful_programs, python_folder, merge_folder, output_folder)
    
    print(f"\n所有文件已处理完成，结果已保存到: {output_folder}")