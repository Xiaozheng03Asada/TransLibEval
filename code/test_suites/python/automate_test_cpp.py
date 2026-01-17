import os
import subprocess
import json
import re


def parse_test_results(test_output, timeout=False):
    """解析测试输出以提取状态、失败和错误信息"""
    result = {
        "total_tests": 0,
        "passed_tests": 0,
        "failed_tests": 0,
        "errors": [],
        "failures": [],
    }
    
    if not timeout:
        # 使用正则提取测试统计信息
        match = re.search(r"Ran (\d+) tests? in .*", test_output)
        if match:
            result["total_tests"] = int(match.group(1))
        
        # 提取失败和错误信息
        result["failures"] = parse_errors_and_failures(test_output, "FAIL")
        result["errors"] = parse_errors_and_failures(test_output, "ERROR")
        
        # 计算失败的测试数
        result["failed_tests"] = len(result["errors"]) + len(result["failures"])
        
        # 计算通过的测试数
        result["passed_tests"] = result["total_tests"] - result["failed_tests"]
    else:
        # 在超时的情况下解析部分已运行的测试结果
        match = re.search(r"Ran (\d+) tests? in .*", test_output)
        if match:
            result["total_tests"] = int(match.group(1))
        
        # 检查是否有部分通过的信息
        if "PASSED" in test_output or "OK" in test_output:
            passed_tests = len(re.findall(r"[ ]+\[  PASSED  \]", test_output))
            result["passed_tests"] = passed_tests
        
        # 提取失败和错误信息
        result["failures"] = parse_errors_and_failures(test_output, "FAIL")
        result["errors"] = parse_errors_and_failures(test_output, "ERROR")
        
        # 计算失败的测试数
        result["failed_tests"] = len(result["errors"]) + len(result["failures"])
    
    return result


def parse_errors_and_failures(test_output, section):
    """提取测试中的错误或失败的具体原因"""
    results = []
    # 错误或失败分段
    if section in test_output:
        matches = re.findall(
            rf"{section}: (.+?)\n\-+\n(Traceback .*?)(?=(?:\n[A-Z]+:|$))",
            test_output,
            re.DOTALL
        )
        for test_name, traceback in matches:
            results.append({
                "test": test_name.strip(),
                "reason": traceback.strip()
            })
    return results

def run_tests_in_collected_files(collected_folder, output_json):
    """运行 collected_files 文件夹中的所有测试文件，并保存结果为 JSON"""
    if not os.path.exists(collected_folder):
        print(f"文件夹 {collected_folder} 不存在，请检查路径！")
        return
    
    results = []
    total_passed_tests = 0
    all_passed_files = 0

    for file_name in os.listdir(collected_folder):
        if file_name.startswith("test_") and file_name.endswith(".py"):
            test_file_path = os.path.join(collected_folder, file_name)
            src_file_name = file_name.replace("test_", "function_")
            src_file_path = os.path.join(collected_folder, src_file_name)

            print(f"正在运行测试文件: {file_name} ...")
            try:
                # 运行测试文件并捕获输出
                result = subprocess.run(
                    ["python", test_file_path],
                    capture_output=True,
                    text=True,
                    timeout=15  # 设置超时时间
                )
                status = "passed" if result.returncode == 0 else "failed"

                # 解析测试输出
                parsed_results = parse_test_results(result.stdout + result.stderr)

                # 更新通过的测试点统计
                total_passed_tests += parsed_results["passed_tests"]

                # 如果文件的测试点全部通过
                if parsed_results["failed_tests"] == 0 and parsed_results["total_tests"] > 0:
                    status = "all_passed"
                    all_passed_files += 1

                # 构造结果
                results.append({
                    "src_file": src_file_name,
                    "test_file": file_name,
                    "status": status,
                    "output": result.stdout.strip(),
                    "errors": parsed_results["errors"],
                    "failures": parsed_results["failures"],
                    "total_tests": parsed_results["total_tests"],
                    "passed_tests": parsed_results["passed_tests"],
                    "failed_tests": parsed_results["failed_tests"]
                })
            except subprocess.TimeoutExpired as e:
                print(f"测试文件 {file_name} 运行超时！")
                # 确保 stdout 和 stderr 解码为字符串，即使为空
                stdout = e.stdout or ""
                stderr = e.stderr or ""
                if isinstance(stdout, bytes):
                    stdout = stdout.decode("utf-8", errors="ignore")
                if isinstance(stderr, bytes):
                    stderr = stderr.decode("utf-8", errors="ignore")
                combined_output = stdout + stderr  # 合并输出

                # 如果 combined_output 仍然为空，提示可能是其他原因导致无输出
                if not combined_output.strip():
                    combined_output = "测试运行超时且未捕获到输出（stdout 和 stderr 为空）"

                # 修正 timeout 情况的统计逻辑
                parsed_results = parse_test_results(combined_output, timeout=True)

                # 如果超时且没有有效输出，设置 failed_tests 为 5
                if not combined_output.strip() or "未捕获到输出" in combined_output:
                    parsed_results["total_tests"] = 5
                    parsed_results["failed_tests"] = 5
                    parsed_results["passed_tests"] = 0
                else:
                    # 如果有部分输出，根据测试点解析内容
                    match = re.search(r"Ran (\d+) tests? in .*", combined_output)
                    if match:
                        parsed_results["total_tests"] = int(match.group(1))
                    else:
                        parsed_results["total_tests"] = 5  # 默认记录 5

                    passed_tests = len(re.findall(r"(?i)\bPASSED\b", combined_output)) + \
                                   combined_output.count(".")  # 用 . 或 PASSED 判断通过点数
                    failed_tests = parsed_results["total_tests"] - passed_tests
                    parsed_results["passed_tests"] = passed_tests
                    parsed_results["failed_tests"] = failed_tests

                # 记录超时的测试结果
                results.append({
                    "src_file": src_file_name,
                    "test_file": file_name,
                    "status": "timeout",
                    "output": combined_output.strip(),
                    "errors": parsed_results["errors"],
                    "failures": parsed_results["failures"],
                    "total_tests": parsed_results["total_tests"],
                    "passed_tests": parsed_results["passed_tests"],
                    "failed_tests": parsed_results["failed_tests"]
                })

            except Exception as e:
                print(f"测试文件 {file_name} 运行出错：{e}")
                results.append({
                    "src_file": src_file_name,
                    "test_file": file_name,
                    "status": "error",
                    "output": str(e),
                    "errors": [],
                    "failures": [],
                    "total_tests": 0,
                    "passed_tests": 0,
                    "failed_tests": 0
                })
    
    # 汇总结果
    summary = {
        "total_passed_tests": total_passed_tests,
        "total_files_tested": all_passed_files,
        "total_files": len(results),
        "results": results
    }

    # 写入 JSON 文件
    with open(output_json, "w") as json_file:
        json.dump(summary, json_file, indent=4, ensure_ascii=False)
    
    print(f"测试结果已保存到 {output_json}")


if __name__ == "__main__":
    # 设置路径
    collected_folder = "cpp_collected_files"  # 测试文件所在文件夹
    output_json = "cpp2python_test_results.json"  # 输出 JSON 文件路径
    
    # 运行测试并保存结果
    run_tests_in_collected_files(collected_folder, output_json)
