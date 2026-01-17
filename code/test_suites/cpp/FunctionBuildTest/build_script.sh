#!/bin/bash

# 获取脚本所在目录
SCRIPT_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

# 切换到项目根目录
cd "$SCRIPT_DIR" || exit 1

# 清空构建目录
echo "清空构建目录..."
rm -rf build
mkdir build

# 进入构建目录
cd build

# 生成构建文件
echo "生成构建文件..."
cmake -G Ninja ..

# 开始构建
echo "开始构建..."
ninja -k 100 2>&1 | tee build_output.log

# 初始化计数器和数组
passed=0
failed=0
declare -a passed_targets
declare -a failed_targets
declare -A error_messages  # 用于存储失败目标的错误原因

# 定义函数：检查目标是否已存在于数组中
function contains {
    local item="$1"
    shift
    for element; do
        if [[ "$element" == "$item" ]]; then
            return 0
        fi
    done
    return 1
}

# 日志分析
echo "分析构建日志..."
while read -r line; do
    # 增强匹配 src 目录下所有文件
    if [[ "$line" =~ src/([^[:space:]]+)\.cpp ]]; then
        target="${BASH_REMATCH[1]}"

        # 优先处理失败的目标
        if [[ "$line" =~ "FAILED" ]]; then
            if ! contains "$target" "${failed_targets[@]}"; then
                failed_targets+=("$target")
                ((failed++))
                # 提取错误原因
                error_messages["$target"]="$line"
            fi
            # 如果目标在成功列表中，移除
            if contains "$target" "${passed_targets[@]}"; then
                passed_targets=("${passed_targets[@]/$target}")
                ((passed--))
            fi
        elif [[ "$line" =~ "Building" || "$line" =~ "Linking" ]]; then
            # 如果目标未失败，标记为成功
            if ! contains "$target" "${passed_targets[@]}"; then
                passed_targets+=("$target")
                ((passed++))
            fi
        fi
    fi
done < build_output.log

total=$((passed + failed))

# 输出结果到根目录的 build_summary.txt 文件
{
    echo "===== 构建结果统计 ====="
    echo "总共尝试构建的目标: $total"
    echo "成功构建的目标: $passed"
    echo "失败的目标: $failed"
    echo ""

    # 成功目标日志
    if [ "$passed" -gt 0 ]; then
        echo "===== 成功构建的目标 ====="
        for target in "${passed_targets[@]}"; do
            echo "$target"
        done
        echo ""
    fi

    # 失败目标日志
    if [ "$failed" -gt 0 ]; then
        echo "===== 失败构建的目标及错误原因 ====="
        for target in "${failed_targets[@]}"; do
            echo "目标: $target"
            echo "错误原因: ${error_messages[$target]}"
            echo ""

            # 提取并附加具体的编译错误和警告信息
            echo "===== 详细编译日志 ====="
            grep -A 10 "$target" build_output.log | sed 's/^/    /'
            echo ""
        done
    fi

    # 构建通过率
    if [ "$total" -gt 0 ]; then
        pass_rate=$(echo "scale=2; $passed * 100 / $total" | bc)
        echo "===== 构建通过率 ====="
        echo "构建通过率: $pass_rate%"
    else
        echo "没有任何目标被尝试构建。"
    fi
} > "$SCRIPT_DIR/build_summary.txt"

# 打印 build_summary.txt 内容到终端
echo "构建结果已保存到 $SCRIPT_DIR/build_summary.txt"
cat "$SCRIPT_DIR/build_summary.txt"

# 根据构建结果退出
if [ "$failed" -gt 0 ]; then
    echo "构建过程中有失败的目标。"
    exit 1
else
    echo "所有目标构建成功。"
    exit 0
fi
