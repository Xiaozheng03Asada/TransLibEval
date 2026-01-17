import os
import shutil
import subprocess
import json
import re

# 路径设置
src_dir = os.path.abspath("FunctionBuildTest/new_src_folder")  # Function 源文件路径
test_dir = os.path.abspath("FunctionBuildTest/new_test_folder")  # Test 文件路径
do_src_dir = os.path.abspath("do/src")  # 目标项目的 src 文件目录
do_test_dir = os.path.abspath("do/tests")  # 目标项目的 test 文件目录
build_dir = os.path.abspath("do/build")  # CMake 构建目录
json_output = os.path.abspath("results.json")  # JSON 结果存储文件

# JSON 结果数据
results = []

# 获取所有的源文件和测试文件
src_files = sorted(os.listdir(src_dir))
test_files = sorted(os.listdir(test_dir))

# 过滤出匹配的文件对（通过正则表达式）
file_pairs = []
for src_file in src_files:
    # 从源文件名中提取关键部分
    src_match = re.match(r"function_(.+)", src_file)
    if src_match:
        src_key = src_match.group(1)  # 提取关键部分
        
        # 遍历测试文件寻找匹配
        for test_file in test_files:
            test_match = re.match(r"test_(.+)", test_file)
            if test_match:
                test_key = test_match.group(1)  # 提取关键部分
                if src_key == test_key:  # 判断关键部分是否匹配
                    file_pairs.append((src_file, test_file))


if not file_pairs:
    print("没有找到符合格式的 src 和 test 文件对！")
    exit(1)


def parse_test_log(stdout):
    """
    解析测试日志，提取测试统计信息和失败测试点的详细信息。
    """
    total_tests = 0
    passed_tests = 0
    failures = []

    # 正则表达式
    total_tests_pattern = r"\[==========\] (\d+) tests from"  # 匹配总测试点
    passed_tests_pattern = r"\[  PASSED  \] (\d+) test"  # 匹配通过测试点
    failure_start_pattern = r"\[  FAILED  \] ([^\s]+)"  # 匹配失败测试点名称
    failure_detail_pattern = r"(\/.*?\.cpp:\d+: Failure.*?)\n\n"  # 匹配失败详细信息

    failure_list_started = False  # 标志失败测试点列表的开始
    failure_details = []  # 临时存储所有失败详细信息

    for line in stdout.splitlines():
        # 提取总测试点数量
        total_match = re.search(total_tests_pattern, line)
        if total_match:
            total_tests = int(total_match.group(1))

        # 提取通过测试点数量
        passed_match = re.search(passed_tests_pattern, line)
        if passed_match:
            passed_tests = int(passed_match.group(1))

        # 检测失败列表的开始标志
        if "tests, listed below" in line:
            failure_list_started = True
            continue

        # 提取失败测试点详细信息
        if failure_list_started:
            failure_match = re.search(failure_start_pattern, line)
            if failure_match:
                failures.append({
                    "test": failure_match.group(1),
                    "reason": ""  # 原因稍后填充
                })
            elif not line.strip():  # 空行表示失败列表结束
                failure_list_started = False

    # 提取具体失败原因
    for match in re.finditer(failure_detail_pattern, stdout, re.DOTALL):
        failure_details.append(match.group(1).strip())

    # 将失败原因关联到失败测试点
    for idx, failure in enumerate(failures):
        if idx < len(failure_details):
            failure["reason"] = failure_details[idx]

    # 过滤掉 reason 为空的失败项
    failures = [failure for failure in failures if failure["reason"]]

    # 如果日志中未能明确提取总测试点，尝试通过推导补全
    if total_tests == 0 and (passed_tests or failures):
        total_tests = passed_tests + len(failures)

    return total_tests, passed_tests, failures


def filter_warnings(log):
    """
    过滤日志中的 warning 信息。
    """
    if not log:
        return log
    log = re.sub(r"warning: .*?\n", "", log, flags=re.IGNORECASE)
    log = re.sub(r"ld: warning: .*?\n", "", log, flags=re.IGNORECASE)
    return log.strip()


# 遍历匹配的文件对
for src_file, test_file in file_pairs:
    print(f"处理文件对：{src_file} 和 {test_file}")

    # 动态生成目标文件路径
    target_src_file = os.path.join(do_src_dir, src_file)
    target_test_file = os.path.join(do_test_dir, test_file)

    # 清理目标目录
    if os.path.exists(do_src_dir):
        shutil.rmtree(do_src_dir)
    os.makedirs(do_src_dir, exist_ok=True)
    if os.path.exists(do_test_dir):
        shutil.rmtree(do_test_dir)
    os.makedirs(do_test_dir, exist_ok=True)
    print(f"已清理并重新创建目录：{do_src_dir} 和 {do_test_dir}")

    # 文件路径
    src_path = os.path.join(src_dir, src_file)
    test_path = os.path.join(test_dir, test_file)

    # 替换文件到 do 项目
    shutil.copy(src_path, target_src_file)
    shutil.copy(test_path, target_test_file)
    print(f"已复制 {src_file} 到 {target_src_file}")
    print(f"已复制 {test_file} 到 {target_test_file}")

    # 清空并重新创建 build 目录
    if os.path.exists(build_dir):
        shutil.rmtree(build_dir)  # 删除整个 build 目录
    os.makedirs(build_dir, exist_ok=True)  # 重新创建 build 目录
    print(f"已清空并重新创建构建目录：{build_dir}")

    # 编译程序（使用 CMake）
    try:
        cmake_command = ["cmake", ".."]
        subprocess.run(cmake_command, cwd=build_dir, check=True, capture_output=True, text=True)

        cmake_build_command = ["cmake", "--build", "."]
        subprocess.run(cmake_build_command, cwd=build_dir, check=True, capture_output=True, text=True)

    except subprocess.CalledProcessError as e:
        print(f"编译失败：{src_file} 和 {test_file}")
        # 捕获标准错误输出并过滤警告
        error_message = e.stderr if e.stderr else "No error output."
        error_message = filter_warnings(error_message.strip())  # 过滤掉无关警告

        results.append({
            "src_file": src_file,
            "test_file": test_file,
            "status": "compile_failed",
            "output": error_message,
            "total_tests": 0,
            "passed_tests": 0
        })
        continue

    # 动态查找生成的可执行文件（严格匹配）
    executables = []
    expected_executable = os.path.splitext(test_file)[0]
    for root, dirs, files in os.walk(build_dir):
        for file in files:
            file_path = os.path.join(root, file)
            # 匹配实际生成的可执行文件名（支持 "_test" 后缀）
            if (expected_executable in file or f"{expected_executable}_test" in file) and os.access(file_path, os.X_OK):
                executables.append(file_path)

    if not executables:
        print(f"没有找到匹配的可执行文件：{src_file} 和 {test_file}")
        results.append({
            "src_file": src_file,
            "test_file": test_file,
            "status": "no_executable",
            "output": "No matching executable found in build directory.",
            "total_tests": 0,
            "passed_tests": 0
        })
        continue

    print(f"找到的可执行文件：{executables}")

    # 遍历找到的目标可执行文件
    for exe in executables:
        try:
            result = subprocess.run(
                [exe], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True, check=True, timeout=10
            )
            stdout = result.stdout.strip()
            stderr = result.stderr.strip()

            total_tests, passed_tests, failures = parse_test_log(stdout)

            results.append({
                "src_file": src_file,
                "test_file": test_file,
                "status": "success",
                "output": stdout,
                "failures": failures,
                "total_tests": total_tests,
                "passed_tests": passed_tests,
                "failed_tests": len(failures)
            })

        except subprocess.CalledProcessError as e:
            stdout = e.stdout.strip() if e.stdout else "No standard output."
            stderr = e.stderr.strip() if e.stderr else "No error output."

            # 过滤掉 warnings
            stdout = filter_warnings(stdout)
            stderr = filter_warnings(stderr)

            print(f"程序运行错误：{exe}")

            # 初始化统计变量
            total_tests, passed_tests, failures = 0, 0, []

            if stdout:  # 如果捕获到部分输出，尝试解析日志
                total_tests, passed_tests, failures = parse_test_log(stdout)

            results.append({
                "src_file": src_file,
                "test_file": test_file,
                "status": "runtime_error",
                "output": f"Standard Output:\n{stdout}\nStandard Error:\n{stderr}",
                "failures": failures,
                "total_tests": total_tests,
                "passed_tests": passed_tests,
                "failed_tests": len(failures)
            })

        except subprocess.TimeoutExpired as e:
            # 捕获超时情况下的部分输出
            stdout = e.stdout if e.stdout else "No partial output."
            stderr = e.stderr if e.stderr else "No error output."

            print(f"程序运行超时：{exe}")

            total_tests, passed_tests, failures = 0, 0, []

            if stdout:  # 如果捕获到部分输出，尝试解析日志
                total_tests, passed_tests, failures = parse_test_log(stdout)

            results.append({
                "src_file": src_file,
                "test_file": test_file,
                "status": "timeout",
                "output": f"Execution timed out after 10 seconds for {exe}\nPartial Output:\n{stdout}\nError Output:\n{stderr}",
                "failures": failures,
                "total_tests": total_tests,
                "passed_tests": passed_tests,
                "failed_tests": len(failures)
            })

# 初始化总体统计变量
total_passed_tests = 0
total_files_tested = 0
total_files = len(file_pairs)  # 匹配到的文件对数量

# 遍历匹配的文件对，运行后的 `results` 会包含测试结果
for result in results:
    total_passed_tests += result.get("passed_tests", 0)
    if result.get("status") == "success" and result.get("failed_tests", 0) == 0:
        total_files_tested += 1

# 构造总体统计信息
summary = {
    "total_passed_tests": total_passed_tests,
    "total_files_tested": total_files_tested,
    "total_files": total_files
}

# 在结果前插入总体统计信息
final_results = {"summary": summary, "details": results}

# 将结果写入 JSON 文件
with open(json_output, "w", encoding="utf-8") as f:
    json.dump(final_results, f, indent=4, ensure_ascii=False)

print(f"测试完成，总体统计信息已加入，结果已保存到 {json_output}")
