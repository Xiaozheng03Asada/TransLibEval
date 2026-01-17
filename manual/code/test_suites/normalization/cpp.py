import json
import re


def parse_kv_string(s: str):
    """
    尝试把诸如 'banana:1, orange:1, apple:2' 解析成一个 dict:
      {
        'banana': 1,
        'orange': 1,
        'apple': 2
      }
    如果解析失败(格式不符), 返回 None.
    """
    s = s.strip()
    if not s:
        # 空字符串也当作空dict
        return {}

    # 按逗号分隔
    parts = [p.strip() for p in s.split(",")]
    kv_dict = {}
    for p in parts:
        # 再按冒号分隔
        if ":" not in p:
            return None
        left, right = p.split(":", 1)
        left = left.strip()
        right = right.strip()
        # 尝试把value解析成整数, 如果不行, 就只能原样字符串
        try:
            val = int(right)
        except ValueError:
            return None
        kv_dict[left] = val
    return kv_dict


def is_same_kv_ignore_order(actual: str, expected: str) -> bool:
    """
    判断两个字符串，如果都是 'key:val, key:val' 这种形式，
    且只在顺序上不同(键值对一模一样)，就返回 True。
    """
    dict_a = parse_kv_string(actual)
    dict_b = parse_kv_string(expected)
    if dict_a is None or dict_b is None:
        return False
    return dict_a == dict_b


def should_ignore_failure(reason: str) -> bool:
    """
    在 reason 文本里，若检测到 "Which is: \"...\"\n  expected \"...\""
    并且这两个只是在 key:value 列表上顺序不同, 就返回 True.

    否则返回 False.
    """
    # 正则捕捉: Which is: "xxx" ... expected "yyy"
    # 带换行的场景可以用 DOTALL
    pattern = re.compile(r'Which is:\s*"([^"]+)"\s*.*?\s*expected\s*"([^"]+)"', re.DOTALL)
    match = pattern.search(reason)
    if not match:
        return False

    actual_str = match.group(1).strip()
    expected_str = match.group(2).strip()
    return is_same_kv_ignore_order(actual_str, expected_str)


def process_details(data: dict) -> dict:
    """
    1. 遍历 details 数组.
    2. 对于 runtime_error 或 success 的条目, 若有 failures, 检查是否可忽略.
    3. 重算 passed_tests / failed_tests, 并在没有失败时把 status 改为 'success'（可改成你想要的字样）.
    4. 最后更新 top-level summary: total_files, total_files_tested, total_passed_tests.
    """
    details = data.get("details", [])
    for item in details:
        status = item.get("status", "")
        # 只有在 runtime_error 或 success 下, 可能有 "failures" 可忽略
        # compile_failed 的 total_tests=0，一般没 failures
        if status in ("runtime_error", "success"):
            # 假设 "failures" 是一个列表（runtime_error 通常会有, success 可能是空）
            failures = item.get("failures", [])
            new_failures = []
            for f in failures:
                reason = f["reason"]
                # 如果 reason 中仅是键值对顺序差异, 就忽略
                if should_ignore_failure(reason):
                    continue
                # 否则保留
                new_failures.append(f)
            item["failures"] = new_failures

            # 重新计算
            total_tests = item.get("total_tests", 0)
            # 如果还有 failures，则失败数 = len(new_failures)
            failed_tests = len(new_failures)
            item["failed_tests"] = failed_tests
            passed_tests = total_tests - failed_tests
            item["passed_tests"] = passed_tests

            # 如果没有失败，则把 status 改为 success
            if failed_tests == 0 and total_tests == 5:
                item["status"] = "success"

    # 重新统计 summary
    # total_files = len(details)
    data["summary"]["total_files"] = len(details)

    # total_files_tested = 看哪些条目 total_tests>0 (表示至少编译成功并跑了测试)
    files_tested = sum(1 for d in details if d.get("total_tests", 0) > 0)
    data["summary"]["total_files_tested"] = files_tested

    # total_passed_tests = 所有条目的 passed_tests 之和
    total_passed = 0
    for d in details:
        total_passed += d.get("passed_tests", 0)
    data["summary"]["total_passed_tests"] = total_passed

    return data


def main():
    base_dir = "/Users/asada/Desktop/测试结果/cpp/6jiansuo (quanwenjian)"  # 或者你实际的根目录
    # 要处理的所有文件夹列表
    folders = [
        "Meta-Llama-3-70B_java_to_cpp_autotest",
        "Meta-Llama-3-70B_python_to_cpp_autotest",
        "Meta-Llama-3-8B_java_to_cpp_autotest",
        "Meta-Llama-3-8B_python_to_cpp_autotest",
        "codellama-7b_java_to_cpp_autotest",
        "codellama-7b_python_to_cpp_autotest",
        "deepseekv3_java_to_cpp_autotest",
        "deepseekv3_python_to_cpp_autotest",
        "gpt3.5_java_to_cpp_autotest",
        "gpt3.5_python_to_cpp_autotest",
        "gpt4o_java_to_cpp_autotest",
        "gpt4o_python_to_cpp_autotest",
        "qwmax_java_to_cpp_autotest",
        "qwmax_python_to_cpp_autotest"
    ]

    for folder in folders:
        input_file = f"{base_dir}/{folder}/results.json"
        output_file = f"{base_dir}/{folder}/fn_results.json"

        # 读取原始结果
        with open(input_file, "r", encoding="utf-8") as f:
            data = json.load(f)

        # 处理细节
        new_data = process_details(data)

        # 写入新的 fn_results.json
        with open(output_file, "w", encoding="utf-8") as f:
            json.dump(new_data, f, ensure_ascii=False, indent=4)

    print("所有文件夹处理完毕。")


if __name__ == "__main__":
    main()
