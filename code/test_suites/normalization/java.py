#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import json
import re
import os

# 所有待处理的顶层文件夹
FOLDERS = [
    "370b",
    "codellama",
    "gpt3.5",
    "qwen",
    "38b",
    "deepseek",
    "gpt4o"
]

def parse_kv_string(s: str):
    s = s.replace("\n", ",")
    parts = [p.strip() for p in s.split(",") if p.strip()]
    kv_map = {}
    for p in parts:
        if ":" not in p:
            return None
        left, right = p.split(":", 1)
        left, right = left.strip(), right.strip()
        try:
            val = int(right)
        except ValueError:
            return None
        kv_map[left] = val
    return kv_map

def normalize_string(s: str) -> str:
    s = s.replace(" ", "")
    s = s.replace(",", "")
    s = s.lower()
    s = s.replace("false", "error")
    s = s.replace("error", "false")
    return s

def is_same_kv_ignore_order_and_commas(s1: str, s2: str) -> bool:
    return normalize_string(s1) == normalize_string(s2)

def is_json_string_equivalent(expected: str, actual: str) -> bool:
    return normalize_string(expected) == normalize_string(actual)

def should_ignore_failure(reason: str) -> bool:
    reg = re.compile(
        r"expected:\s*(?:\\u003c|<)([^>]+)(?:\\u003e|>)\s*but\s*was:\s*(?:\\u003c|<)([^>]+)(?:\\u003e|>)",
        re.IGNORECASE | re.DOTALL
    )
    m = reg.search(reason)
    if not m:
        return False
    exp_str, act_str = m.group(1).strip(), m.group(2).strip()
    if is_same_kv_ignore_order_and_commas(exp_str, act_str):
        return True
    if is_json_string_equivalent(exp_str, act_str):
        return True
    return False

def rebuild_error_type_stats(data: dict):
    new_stats = {}
    for item in data.get("results", []):
        fname = item.get("file", "")
        for f in item.get("failures", []):
            r = f["reason"]
            stat = new_stats.setdefault(r, {"count": 0, "files": []})
            stat["count"] += 1
            if fname not in stat["files"]:
                stat["files"].append(fname)
    data["errorTypeStats"] = new_stats

def process_data(data: dict) -> dict:
    for item in data.get("results", []):
        orig_fail = item.get("failures", [])
        if not orig_fail:
            continue
        filtered = [f for f in orig_fail if not should_ignore_failure(f["reason"])]
        item["failures"]    = filtered
        total               = item.get("totalTests", 0)
        failed              = len(filtered)
        item["failedTests"] = failed
        item["passedTests"] = total - failed

    total_run = total_passed = full_pass = 0
    for item in data.get("results", []):
        total_run   += item.get("totalTests", 0)
        total_passed+= item.get("passedTests", 0)
        if item.get("failedTests", 0) == 0:
            full_pass += 1

    data["totalTestsRun"]    = total_run
    data["totalTestsPassed"] = total_passed
    data["fullyPassedFiles"] = full_pass

    rebuild_error_type_stats(data)
    return data

def process_all_json_files(input_dir: str):
    """
    只处理 cpp2java / py2java 下的 test_results.json 文件，
    并输出 fn_test_results.json。遇到 JSONDecodeError 时跳过。
    """
    for root, _, files in os.walk(input_dir):
        if os.path.basename(root) not in ("cpp2java", "py2java"):
            continue
        for filename in files:
            if filename != "test_results.json":
                continue
            in_path  = os.path.join(root, filename)
            out_path = os.path.join(root, "fn_test_results.json")
            try:
                with open(in_path, "r", encoding="utf-8") as f:
                    data = json.load(f)
            except json.JSONDecodeError as e:
                print(f"警告：跳过无法解析的文件 {in_path}（{e}）")
                continue

            new_data = process_data(data)
            with open(out_path, "w", encoding="utf-8") as f:
                json.dump(new_data, f, indent=2, ensure_ascii=False)
            print(f"处理完成，结果写入: {out_path}")

def main():
    # TODO: 修改为你的父目录路径
    root_dir = "/Users/asada/Desktop/测试结果/java/伪代码"
    for folder in FOLDERS:
        folder_path = os.path.join(root_dir, folder)
        if os.path.isdir(folder_path):
            process_all_json_files(folder_path)
        else:
            print(f"警告：未找到目录 {folder_path}")

if __name__ == "__main__":
    main()
