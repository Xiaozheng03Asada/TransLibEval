#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
读取给定文件夹下的所有 .cpp 文件，
假设每个文件里只有一个 class 且 class 里只有一个成员函数。
提取函数签名（包含默认值、嵌套括号等情况），输出到 JSON。
"""
import json
import re
import sys
from pathlib import Path

SRC_DIR  = Path(sys.argv[1]) if len(sys.argv)>1 else Path("cpp")
DEST_DIR = Path(sys.argv[2]) if len(sys.argv)>2 else Path("signature_out")
DEST_DIR.mkdir(exist_ok=True, parents=True)

# 提取 class 名
CLASS_RE = re.compile(r'\bclass\s+(\w+)\s*{', re.S)

# 改进后的成员函数正则：
# 参数部分 (?P<params>) 可以匹配内部的 (...)，直到最外层的 ')'
FUNC_RE = re.compile(r'''
    (?P<ret>[\w:\<\>\s\*&]+?)      # 返回类型
    \s+
    (?P<name>\w+)\s*               # 函数名
    \(                             # 参数列表起
      (?P<params>                  # 开始捕获 params
        (?:                        # 参数列表内：
           [^()]+                  #   非括号字符
         | \([^()]*\)              #   或者一对内部括号
        )*                         # 重复任意次
      )                            # 结束 params
    \)                             # 参数列表闭
    \s*(?P<qualifiers>             # 可选限定符
       (?:
         const|noexcept|override|final|virtual|mutable
         |\[\[.*?\]\]
       )*
    )\s*\{                         # 函数体起始 {
''', re.S | re.X)

def parse_params(param_str: str):
    params = []
    # 按逗号拆顶层参数
    parts = [p.strip() for p in param_str.split(',') if p.strip()]
    for raw in parts:
        # 去掉默认值部分 “= ...”
        no_def = raw.split('=',1)[0].strip()
        # 然后再按最后一个空格拆类型和名称
        bits = no_def.rsplit(' ', 1)
        if len(bits)==2:
            ptype, pname = bits
        else:
            ptype, pname = bits[0], ""
        params.append({"type":ptype.strip(), "name":pname.strip()})
    return params

for cpp in SRC_DIR.glob("*.cpp"):
    text = cpp.read_text(encoding="utf-8", errors="ignore")
    m1 = CLASS_RE.search(text)
    if not m1:
        print(f"[Warning] {cpp.name} 未找到 class，跳过")
        continue
    cls = m1.group(1)

    # 从 class { 之后开始找函数
    m2 = FUNC_RE.search(text, pos=m1.end())
    if not m2:
        print(f"[Warning] {cpp.name} 未找到函数，跳过")
        continue

    ret   = ' '.join(m2.group('ret').split())
    name  = m2.group('name')
    ps    = m2.group('params').strip()
    quals = m2.group('qualifiers').split() if m2.group('qualifiers') else []

    meta = {
        "class_name": cls,
        "function": {
            "name": name,
            "return_type": ret,
            "parameters": parse_params(ps),
            "qualifiers": quals,
            "access": "public"
        }
    }

    out = DEST_DIR / (cpp.stem + ".json")
    out.write_text(json.dumps(meta, indent=4, ensure_ascii=False), encoding="utf-8")

    # 安全打印
    try:
        rel = out.resolve().relative_to(Path.cwd())
    except ValueError:
        rel = out
    print(f"[OK] {cpp.name} → {rel}")

print("全部处理完毕！")
