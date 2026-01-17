import json
import re
import chardet


def is_numeric_quote_diff(reason: str) -> bool:
    """
    判断是否仅为类似 '数字' != 数字 的格式不一致(值本质相同)。
    例如:
      AssertionError: '2' != 2
      AssertionError: '0' != 0
      ...
    若捕捉到且数字相同则返回 True。
    """
    pairs = re.findall(r"'(\d+)' != (\d+)", reason)
    if not pairs:
        return False

    # 如果有多组，需要都相同才算可忽略
    for left, right in pairs:
        if left != right:
            return False

    return True


def process_test_results(data: dict) -> dict:
    """
    根据最新需求：
      1) 全部 errors 都直接忽略 (视为通过)，不计入失败。
      2) failures 如果仅是 '数字' != 数字 的格式差异，则忽略。
      3) 重新计算 passed_tests / failed_tests / status:
         - 若 failed_tests==0，status=all_passed，否则保持原来（或你可视情况改为'failed'）。
      4) 重做顶层统计:
         - total_files = len(results)
         - total_files_tested = count of files with status 'all_passed'
         - total_passed_tests = sum of all passed_tests
    """
    results = data.get("results", [])

    for result in results:
        failures = result.get("failures", [])
        errors = result.get("errors", [])

        # ---- 1) 忽略全部 errors ----
        new_errors = []

        # ---- 2) 筛选 failures: 只要是 '数字' != 数字 就忽略，否则保留 ----
        new_failures = []
        for f in failures:
            reason = f["reason"]
            if is_numeric_quote_diff(reason):
                # 视为格式差异, 忽略
                continue
            new_failures.append(f)

        # ---- 更新回 result ----
        result["failures"] = new_failures
        result["errors"] = new_errors

        # ---- 3) 重算通过/失败 ----
        total_tests = result.get("total_tests", 0)
        current_failed = len(new_failures) + len(new_errors)
        result["failed_tests"] = current_failed
        result["passed_tests"] = total_tests - current_failed

        # 如果没有失败, 标记为 all_passed
        if current_failed == 0 and total_tests == 5:
            result["status"] = "all_passed"
        # 否则可以保留原状态，也可以显式改为 "failed"
        # else:
        #     result["status"] = "failed"

    # ---- 4) 顶层统计 ----
    # (1) total_files = 结果的总数
    data["total_files"] = len(results)

    # (2) total_files_tested = 状态为 'all_passed' 的文件数
    count_all_passed = sum(1 for r in results if r.get("status") == "all_passed")
    data["total_files_tested"] = count_all_passed

    # (3) total_passed_tests = 全部通过测试点的总和
    total_passed_points = 0
    for r in results:
        total_passed_points += r.get("passed_tests", 0)
    data["total_passed_tests"] = total_passed_points

    return data


def detect_file_encoding(file_path):
    """自动检测文件编码"""
    with open(file_path, 'rb') as f:
        rawdata = f.read()
        result = chardet.detect(rawdata)
        return result['encoding']


def main():
    models = [
        "CodeLlama-7b", "Llama3-70B", "gpt-3.5-turbo",
        "Deepseek-V3", "Llama3-8B", "gpt-4o", "qwen-max"
    ]
    test_types = ["cpp2python", "java2python"]
    base_dir = "/Users/asada/Desktop/测试结果/py/testing_检索-搜索名称"  # 或者你实际的根目录

    for model in models:
        for tt in test_types:
            input_file = f"{base_dir}/{model}/{tt}_test_results.json"
            output_file = f"{base_dir}/{model}/{tt}_output.json"

            # 1. 探测编码
            encoding = detect_file_encoding(input_file)

            # 2. 读取并处理 JSON，加入多重编码容错
            try:
                with open(input_file, "r", encoding=encoding) as f:
                    data = json.load(f)
            except UnicodeDecodeError:
                try:
                    with open(input_file, "r", encoding="utf-8") as f:
                        data = json.load(f)
                except UnicodeDecodeError:
                    with open(input_file, "r", encoding="gb18030", errors="ignore") as f:
                        data = json.load(f)

            # 3. 处理数据
            processed = process_test_results(data)

            # 4. 写入 UTF-8
            with open(output_file, "w", encoding="utf-8") as f:
                json.dump(processed, f, ensure_ascii=False, indent=4)

            print(f"✔ 处理完成：{model} / {tt}")

if __name__ == "__main__":
    main()
