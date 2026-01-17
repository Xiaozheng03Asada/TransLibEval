import os
import shutil

# 读取 error_build.txt 文件并提取成功的目标名称
def read_success_files(file_path):
    success_files = []
    
    with open(file_path, 'r') as file:
        lines = file.readlines()

    # 标志位，判断是否在读取成功构建的目标
    reading_success = False
    
    for line in lines:
        line = line.strip()
        
        if line == "===== 成功构建的目标 =====":
            reading_success = True
            continue
        elif line == "===== 失败构建的目标及错误原因 =====":
            # 遇到失败目标部分，立即停止读取
            print("检测到失败的目标名称，停止读取文件！")
            break
        
        if reading_success and line:
            success_files.append(line)
    
    return success_files

# 转换为测试文件名（test_ 文件）
def convert_to_test_filename(success_file):
    return f"test_{success_file.replace('function_', '')}.cpp"

# 转换为源文件名（src 文件）
def convert_to_src_filename(success_file):
    return f"{success_file}.cpp"

# 复制成功的测试文件到目标文件夹
def copy_success_test_files(success_files, source_dir, target_dir):
    os.makedirs(target_dir, exist_ok=True)  # 创建目标文件夹
    
    for success_file in success_files:
        # 获取测试文件名
        test_file_name = convert_to_test_filename(success_file)
        test_file_path = os.path.join(source_dir, test_file_name)
        
        if os.path.exists(test_file_path):
            shutil.copy(test_file_path, target_dir)
            print(f"复制成功的测试文件: {test_file_name}")
        else:
            print(f"找不到测试文件: {test_file_name}")

# 复制成功的源文件到目标文件夹
def copy_success_src_files(success_files, source_dir, target_dir):
    os.makedirs(target_dir, exist_ok=True)  # 创建目标文件夹
    
    for success_file in success_files:
        # 获取源文件名
        src_file_name = convert_to_src_filename(success_file)
        src_file_path = os.path.join(source_dir, src_file_name)
        
        if os.path.exists(src_file_path):
            shutil.copy(src_file_path, target_dir)
            print(f"复制成功的源文件: {src_file_name}")
        else:
            print(f"找不到源文件: {src_file_name}")

def main():
    # 获取当前脚本所在目录
    current_dir = os.path.dirname(os.path.abspath(__file__))
    
    # 设置路径
    error_build_file = os.path.join(current_dir, 'build_summary.txt')  # error_build.txt 文件路径
    test_folder = os.path.join(current_dir, 'tests')  # tests 文件夹路径
    src_folder = os.path.join(current_dir, 'src')  # src 文件夹路径
    new_test_folder = os.path.join(current_dir, 'new_test_folder')  # 新的测试文件夹路径
    new_src_folder = os.path.join(current_dir, 'new_src_folder')  # 新的 src 文件夹路径
    
    # 读取文件并获取成功的目标名称
    success_files = read_success_files(error_build_file)
    
    # 复制成功的测试文件到新的测试文件夹
    copy_success_test_files(success_files, test_folder, new_test_folder)
    
    # 复制成功的源文件到新的源文件夹
    copy_success_src_files(success_files, src_folder, new_src_folder)

if __name__ == "__main__":
    main()
