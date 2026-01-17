#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
读取给定文件夹下的所有 .java 文件，
假设每个文件里只有一个 class 且 class 里只有一个方法。
提取方法签名（包含泛型、throws 子句等情况），输出到 JSON。
"""
import json
import re
import sys
from pathlib import Path

# 输入输出目录设置
SRC_DIR  = Path(sys.argv[1]) if len(sys.argv)>1 else Path("java")
DEST_DIR = Path(sys.argv[2]) if len(sys.argv)>2 else Path("signature_out")
DEST_DIR.mkdir(exist_ok=True, parents=True)

# 提取 package 名（可选）
PACKAGE_RE = re.compile(r'^\s*package\s+([\w\.]+)\s*;', re.M)

# 提取 class 名
# 支持 public/abstract/final 修饰，以及 extends/implements 后跟花括号
CLASS_RE = re.compile(r'\bclass\s+(\w+)\b.*?\{', re.S)

# Java 方法正则：
# - (?P<mods>) 捕获所有修饰符
# - (?P<ret>) 泛型或普通返回类型
# - (?P<name>) 方法名
# - (?P<params>) 参数列表（支持嵌套尖括号）
# - (?P<throws>) 可选的 throws 子句
# 以方法体开头的 '{' 作为锚点
METHOD_RE = re.compile(r'''
    (?P<mods>(?:public|protected|private|static|final|synchronized|abstract|\s)*)  # 修饰符
    \s*
    (?P<ret>[\w\<\>\[\]\.\s]+?)      # 返回类型，支持泛型和数组
    \s+
    (?P<name>\w+)\s*                # 方法名
    \(
      (?P<params>
        (?:
           [^()<>]+                # 非括号/泛型字符
         | <[^<>]*>                # 泛型尖括号内
         | \([^()]*\)              # 小括号嵌套（极少用）
        )*
      )
    \)
    (?:\s+throws\s+(?P<throws>[\w\.,\s]+))?  # 可选 throws 子句
    \s*\{                           # 方法体开始
''', re.S | re.X)

def parse_params(param_str: str):
    """
    将参数字符串按顶层逗号拆分，去掉注解和泛型，
    拆出 type/name，返回列表。
    """
    params = []
    # 去掉注解（简单版，只去掉以 @ 开头的部分）
    clean = re.sub(r'@\w+(?:\([^)]*\))?', '', param_str)
    parts = [p.strip() for p in clean.split(',') if p.strip()]
    for raw in parts:
        # 去掉泛型标记 <...>
        no_gen = re.sub(r'<[^<>]*>', '', raw).strip()
        bits = no_gen.rsplit(' ', 1)
        if len(bits) == 2:
            ptype, pname = bits
        else:
            # 只有类型，则没有形参名
            ptype, pname = bits[0], ""
        params.append({"type": ptype.strip(), "name": pname.strip()})
    return params

for java in SRC_DIR.glob("*.java"):
    text = java.read_text(encoding="utf-8", errors="ignore")

    # 提取 package（如果存在）
    pkg_m = PACKAGE_RE.search(text)
    package = pkg_m.group(1) if pkg_m else ""

    # 匹配 class
    m1 = CLASS_RE.search(text)
    if not m1:
        print(f"[Warning] {java.name} 未找到 class，跳过")
        continue
    cls = m1.group(1)

    # 从 class 定义后开始查方法
    m2 = METHOD_RE.search(text, pos=m1.end())
    if not m2:
        print(f"[Warning] {java.name} 未找到方法，跳过")
        continue

    mods   = m2.group('mods').split()
    ret    = ' '.join(m2.group('ret').split())
    name   = m2.group('name')
    ps     = m2.group('params').strip()
    throws = [e.strip() for e in m2.group('throws').split(',')] if m2.group('throws') else []

    meta = {
        "package": package,
        "class_name": cls,
        "method": {
            "name": name,
            "return_type": ret,
            "modifiers": mods,
            "parameters": parse_params(ps),
            "throws": throws
        }
    }

    # 写出 JSON
    out = DEST_DIR / (java.stem + ".json")
    out.write_text(json.dumps(meta, indent=4, ensure_ascii=False), encoding="utf-8")

    # 打印结果
    try:
        rel = out.resolve().relative_to(Path.cwd())
    except ValueError:
        rel = out
    print(f"[OK] {java.name} → {rel}")

print("全部处理完毕！")
