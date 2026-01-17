#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
读取给定文件夹下的所有 .py 文件，
假设每个文件里只有一个 class 且 class 里只有一个方法。
提取方法签名（包含默认值、嵌套括号、注解等情况），输出到 JSON。
"""
import json
import re
import sys
from pathlib import Path

# 源代码目录和输出目录
SRC_DIR = Path(sys.argv[1]) if len(sys.argv) > 1 else Path("python")
DEST_DIR = Path(sys.argv[2]) if len(sys.argv) > 2 else Path("signature_out")
DEST_DIR.mkdir(exist_ok=True, parents=True)

# 匹配 class 名称
CLASS_RE = re.compile(r'^\s*class\s+(\w+)\s*(?:\([\w\s,]*\))?:', re.M)
# 匹配方法定义，包括装饰器和注解
FUNC_RE = re.compile(r'^[ \t]*def\s+(?P<name>\w+)\s*\((?P<params>(?:[^()]+|\([^()]*\))*)\)\s*(?:->\s*(?P<ret>[^:]+))?:', re.M)

def parse_params(param_str: str):
    """解析方法参数字符串，返回参数列表，包含默认值和注解"""
    params = []
    # 按逗号拆顶层参数
    parts = []
    depth = 0
    current = []
    for ch in param_str:
        if ch == ',' and depth == 0:
            part = ''.join(current).strip()
            if part:
                parts.append(part)
            current = []
        else:
            if ch == '(':
                depth += 1
            elif ch == ')':
                depth -= 1
            current.append(ch)
    last = ''.join(current).strip()
    if last:
        parts.append(last)

    for raw in parts:
        # 提取默认值
        if '=' in raw:
            name_ann, default = raw.split('=', 1)
            default = default.strip()
        else:
            name_ann = raw
            default = None
        # 提取注解
        if ':' in name_ann:
            name, ann = [x.strip() for x in name_ann.split(':', 1)]
            annotation = ann
        else:
            name = name_ann.strip()
            annotation = None
        params.append({
            "name": name,
            "annotation": annotation,
            "default": default
        })
    return params

for py in SRC_DIR.glob("*.py"):
    text = py.read_text(encoding="utf-8", errors="ignore")
    m1 = CLASS_RE.search(text)
    if not m1:
        print(f"[Warning] {py.name} 未找到 class，跳过")
        continue
    cls = m1.group(1)

    # 在 class 定义后查找方法
    m2 = FUNC_RE.search(text, pos=m1.end())
    if not m2:
        print(f"[Warning] {py.name} 未找到方法定义，跳过")
        continue

    name = m2.group('name')
    ps = m2.group('params').strip()
    ret = m2.group('ret').strip() if m2.group('ret') else None

    meta = {
        "class_name": cls,
        "method": {
            "name": name,
            "return_annotation": ret,
            "parameters": parse_params(ps)
        }
    }

    out = DEST_DIR / (py.stem + ".json")
    out.write_text(json.dumps(meta, indent=4, ensure_ascii=False), encoding="utf-8")

    try:
        rel = out.resolve().relative_to(Path.cwd())
    except ValueError:
        rel = out
    print(f"[OK] {py.name} → {rel}")

print("全部处理完毕！")
