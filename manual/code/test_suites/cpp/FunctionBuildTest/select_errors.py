import re

def extract_errors_from_log(file_path):
    errors = []
    with open(file_path, 'r', encoding='utf-8') as file:
        lines = file.readlines()

    target = None
    error_reason = None
    key_errors = []
    collecting_errors = False  # 是否正在收集关键错误

    for line in lines:
        # 匹配失败目标的名称
        target_match = re.match(r"目标:\s*(\S+)", line)
        if target_match:
            if target and error_reason:
                errors.append((target, error_reason, key_errors))  # 存储之前的目标错误信息
            target = target_match.group(1)
            error_reason = None
            key_errors = []
            collecting_errors = False
        
        # 匹配错误原因
        error_match = re.match(r"错误原因:\s*(.+)", line)
        if error_match and target:
            error_reason = error_match.group(1)
            collecting_errors = True  # 进入关键错误信息收集模式
        
        # 收集 error 相关的错误信息（带去重功能）
        if collecting_errors:
            error_detail_match = re.search(r"error: .+", line)
            if error_detail_match:
                error_detail = error_detail_match.group().strip()
                # 检查是否已存在相同的错误信息
                if error_detail not in key_errors:
                    key_errors.append(error_detail)

    # 处理最后一组错误
    if target and error_reason:
        errors.append((target, error_reason, key_errors))

    return errors

def save_errors_to_file(errors, output_file):
    output = []
    for target, error, key_errors in errors:
        output.append(f"目标: {target}")
        output.append(f"错误原因: {error}")
        output.append("关键错误信息:")
        if key_errors:
            for err in key_errors:
                output.append(f"- {err}")
        else:
            output.append("- (无详细错误信息)")
        output.append("=" * 50)  # 分割线

    with open(output_file, "w", encoding="utf-8") as f:
        f.write("\n".join(output))

    print(f"错误信息已保存至 {output_file}")

def main():
    input_file = "build_summary.txt"  # 确保该文件存在
    output_file = "extracted_errors.txt"
    
    extracted_errors = extract_errors_from_log(input_file)
    save_errors_to_file(extracted_errors, output_file)

if __name__ == "__main__":
    main()