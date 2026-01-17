import os
import subprocess
import logging

# 配置日志记录
def setup_logger():
    log_file = 'cpp_combined_log.txt'
    open(log_file, 'w').close()  # 清空日志文件
    logger = logging.getLogger('combined_logger')
    logger.setLevel(logging.INFO)
    handler = logging.FileHandler(log_file, encoding='utf-8')  # 指定编码为 UTF-8
    formatter = logging.Formatter('%(asctime)s - %(message)s')
    handler.setFormatter(formatter)
    logger.addHandler(handler)
    return logger

def run_scripts_in_directory(input_folder):
    # 获取输入文件夹中的所有文件
    files_in_directory = os.listdir(input_folder)
    
    # 存储出错和成功的程序名称及错误原因
    error_programs = {}
    success_programs = []
    
    print("开始检查并执行指定文件夹中的所有可执行程序...\n")
    
    # 遍历所有文件
    for file_name in files_in_directory:
        file_path = os.path.join(input_folder, file_name)
        
        # 如果是 Python 脚本，且以 "function" 开头，尝试使用 Python 解释器执行
        if os.path.isfile(file_path) and file_name.startswith("function") and file_name.endswith(".py"):
            print(f"正在尝试执行 Python 脚本: {file_name} ...")
            try:
                # 使用 python 来确保调用的是 Python 解释器
                subprocess.run(
                    ['python', file_path], 
                    check=True, 
                    timeout=10,  # 设置超时时间为10秒
                    capture_output=True,  # 捕获标准输出和标准错误输出
                    text=True  # 捕获输出并解码为文本
                )
                print(f"{file_name} 执行成功!\n")
                success_programs.append(file_name)  # 成功执行，记录程序
            except subprocess.TimeoutExpired:
                print(f"{file_name} 执行超时，跳过...\n")
                error_programs[file_name] = "执行超时"
            except subprocess.CalledProcessError as e:
                print(f"{file_name} 执行失败，跳过...\n")
                error_programs[file_name] = f"错误输出: {e.stderr.strip()}"
            except FileNotFoundError:
                print(f"Python 解释器未找到，请确保 Python 已安装并添加到 PATH 中。\n")
                error_programs[file_name] = "Python 解释器未找到"
        
        # 对其他可执行文件进行处理（如 .exe、.bat、.cmd）
        elif os.path.isfile(file_path) and file_name.lower().endswith(('.exe', '.bat', '.cmd')):
            print(f"正在尝试执行: {file_name} ...")
            try:
                subprocess.run(
                    [file_path], 
                    check=True, 
                    timeout=60,  # 设置超时时间为60秒
                    capture_output=True,  # 捕获标准输出和标准错误输出
                    text=True  # 捕获输出并解码为文本
                )
                print(f"{file_name} 执行成功!\n")
                success_programs.append(file_name)  # 成功执行，记录程序
            except subprocess.TimeoutExpired:
                print(f"{file_name} 执行超时，跳过...\n")
                error_programs[file_name] = "执行超时"
            except subprocess.CalledProcessError as e:
                print(f"{file_name} 执行失败，跳过...\n")
                error_programs[file_name] = f"错误输出: {e.stderr.strip()}"
            except Exception as e:
                print(f"{file_name} 执行出错: {e}\n")
                error_programs[file_name] = f"执行出错: {e}"
        else:
            print(f"{file_name} 不是可执行文件，跳过...\n")
    
    # 返回所有出错和成功的程序名称和错误原因
    return error_programs, success_programs

if __name__ == "__main__":
    # 设置输入文件夹路径
    input_folder = "cpp_to_python"  # 可以修改为你的实际输入文件夹路径
    
    # 确保输入文件夹存在
    if not os.path.exists(input_folder):
        print(f"输入文件夹不存在: {input_folder}")
        exit(1)
    
    # 配置日志记录
    logger = setup_logger()
    
    # 获取出错和成功的程序名称
    error_programs, success_programs = run_scripts_in_directory(input_folder)
    
    # 构造日志内容
    log_content = []
    
    # 打印错误原因
    if error_programs:
        log_content.append("\n记录出错原因：")
        for program, reason in error_programs.items():
            log_content.append(f"- {program}: {reason}")
    
    # 打印成功的程序
    log_content.append("\n以下程序执行成功：")
    for program in success_programs:
        log_content.append(f"- {program}")
    
    # 打印失败的程序
    if error_programs:
        log_content.append("\n以下程序执行出错：")
        for program in error_programs:
            log_content.append(f"- {program}")
    
    # 计算成功率
    total_programs = len(error_programs) + len(success_programs)
    if total_programs > 0:
        success_rate = len(success_programs) / total_programs * 100
        log_content.append(f"\n成功率：{success_rate:.2f}%")
    
    # 打印和记录日志
    final_log_output = "\n".join(log_content)
    print(final_log_output)
    logger.info(final_log_output)