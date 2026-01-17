#!/usr/bin/env python3
"""
Utility CLI for TransLibEval.

Usage examples:
  python scripts/translib_cli.py env
  python scripts/translib_cli.py test --targets python cpp
"""

from __future__ import annotations

import argparse
import os
import subprocess
import sys
from pathlib import Path
from typing import Iterable, List

ROOT = Path(__file__).resolve().parents[1]
if str(ROOT) not in sys.path:
    sys.path.insert(0, str(ROOT))

from translib import load_env_file
from translib.env import get_env
from translib.testing import StageResult, run_cpp_pipeline, run_python_pipeline

STRATEGY_DIRS = {
    "direct": ROOT / "code" / "generate_strategies" / "Direct",
    "ra-method": ROOT / "code" / "generate_strategies" / "RA(method)",
    "ra-name": ROOT / "code" / "generate_strategies" / "RA(name)",
    "ir-pseudocode": ROOT / "code" / "generate_strategies" / "IR(pseudocode)",
    "ir-summary": ROOT / "code" / "generate_strategies" / "IR(summary)",
    "ir-cot": ROOT / "code" / "generate_strategies" / "IR(CoT)",
}
MODEL_SCRIPT_NAMES = [
    "Deepseek-V3.py",
    "codellama-7b.py",
    "gpt-3.5-turbo.py",
    "gpt-4o.py",
    "llama3-70b.py",
    "llama3-8b.py",
    "qwen-max.py",
]


def _format_value(value: str | None, show: bool) -> str:
    if not value:
        return "未设置"
    if not show:
        return "已设置"
    if len(value) <= 6:
        return value
    return f"{value[:3]}...{value[-2:]}"


def command_env(args: argparse.Namespace) -> None:
    load_env_file()
    groups = {
        "OpenAI/GPT": ["OPENAI_API_KEY", "OPENAI_BASE_URL"],
        "Qwen DashScope": ["QWEN_API_KEY", "QWEN_API_BASE"],
        "DeepSeek Qianfan REST": ["DEEPSEEK_AUTH_TOKEN", "DEEPSEEK_APP_ID"],
        "Baidu Qianfan SDK": ["QIANFAN_ACCESS_KEY", "QIANFAN_SECRET_KEY"],
        "Google Custom Search": ["GOOGLE_CSE_ID", "GOOGLE_CSE_API_KEYS"],
    }
    print("当前环境变量状态：")
    for group, keys in groups.items():
        print(f"\n[{group}]")
        for key in keys:
            value = get_env(key)
            rendered = _format_value(value, args.show_values)
            print(f"  {key:<22} {rendered}")
    print(
        "\n提示：可复制 .env.example -> .env 并运行 `python scripts/translib_cli.py env` 以快速检查配置。"
    )


def _print_stage_group(title: str, results: Iterable[StageResult]) -> None:
    print(f"\n{title}")
    for stage in results:
        if stage.skipped:
            print(f"- {stage.name}: ⚠️  跳过（{stage.reason}）")
            continue
        icon = "✅" if stage.success else "❌"
        print(f"- {stage.name}: {icon}  {stage.duration:.1f}s")
        if not stage.success:
            print("  ↳ 请查看上方命令输出定位问题。")


def command_test(args: argparse.Namespace) -> None:
    load_env_file()
    from translib.testing import run_conversion_tests

    dest = run_conversion_tests(args.pipeline, Path(args.source_dir))
    print(f"✅ 完成 {args.pipeline} 测试，结果保存在 {dest}")


def _existing(paths: Iterable[Path]) -> list[Path]:
    return [p for p in paths if p.exists()]


def _build_direct_scripts() -> list[Path]:
    base = STRATEGY_DIRS["direct"]
    return _existing(base / name for name in MODEL_SCRIPT_NAMES)


def _build_ra_method_scripts() -> list[Path]:
    base = STRATEGY_DIRS["ra-method"]
    return _existing(base / name for name in MODEL_SCRIPT_NAMES)


def _build_ir_scripts(kind: str) -> list[Path]:
    base = STRATEGY_DIRS[kind]
    scripts: List[Path] = []
    for lang in ("cpp", "java", "python"):
        lang_dir = base / lang
        if not lang_dir.exists():
            continue
        # 第一阶段：Sum_*
        for name in MODEL_SCRIPT_NAMES:
            sum_path = lang_dir / f"Sum_{name}"
            if sum_path.exists():
                scripts.append(sum_path)
        # 第二阶段：原始脚本
        for name in MODEL_SCRIPT_NAMES:
            script = lang_dir / name
            if script.exists():
                scripts.append(script)
    return scripts


def _build_ra_name_scripts() -> list[Path]:
    base = STRATEGY_DIRS["ra-name"]
    scripts: List[Path] = []
    for lang in ("cpp", "java", "python"):
        lang_dir = base / lang
        if not lang_dir.exists():
            continue
        signature = lang_dir / "signature.py"
        if signature.exists():
            scripts.append(signature)
        for search_name in ("Search.py", "Serch.py"):
            search_path = lang_dir / search_name
            if search_path.exists():
                scripts.append(search_path)
        for name in MODEL_SCRIPT_NAMES:
            script = lang_dir / name
            if script.exists():
                scripts.append(script)
    return scripts


def _build_scripts_for_strategy(strategy: str) -> list[Path]:
    if strategy == "direct":
        return _build_direct_scripts()
    if strategy == "ra-method":
        return _build_ra_method_scripts()
    if strategy == "ra-name":
        return _build_ra_name_scripts()
    if strategy in {"ir-pseudocode", "ir-summary", "ir-cot"}:
        return _build_ir_scripts(strategy)
    raise ValueError(f"未知策略：{strategy}")


def _run_generation_script(script_path: Path, dry_run: bool) -> bool:
    rel = script_path.relative_to(ROOT)
    print(f"\n=== 运行脚本: {rel}")
    if dry_run:
        print("    (dry-run) 仅展示不执行。")
        return True
    cmd = [sys.executable, script_path.name]
    proc = subprocess.run(cmd, cwd=script_path.parent)
    if proc.returncode == 0:
        print(f"    完成 ({rel})")
        return True
    print(f"    失败 ({rel})，return code = {proc.returncode}")
    return False


def command_generate(args: argparse.Namespace) -> None:
    load_env_file()
    selected = args.strategies or list(STRATEGY_DIRS.keys())
    selected = [s.lower() for s in selected]
    invalid = [s for s in selected if s not in STRATEGY_DIRS]
    if invalid:
        raise SystemExit(f"未知策略：{', '.join(invalid)}。可选：{', '.join(STRATEGY_DIRS)}")

    fail_count = 0
    total = 0
    for strat in selected:
        print(f"\n##### 执行策略 {strat} #####")
        scripts = _build_scripts_for_strategy(strat)
        if not scripts:
            print("    未找到脚本，跳过。")
            continue
        for script in scripts:
            total += 1
            ok = _run_generation_script(script, args.dry_run)
            if not ok:
                fail_count += 1
                if args.stop_on_error:
                    print("检测到错误，按要求终止后续执行。")
                    print(f"总结：总脚本 {total} 个，失败 {fail_count} 个。")
                    return

    suffix = "（dry-run 模式，仅列出脚本）" if args.dry_run else ""
    print(f"\n全部策略执行完毕，总脚本 {total} 个，失败 {fail_count} 个。{suffix}")


def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(description="TransLibEval 辅助脚本")
    sub = parser.add_subparsers(dest="command", required=True)

    env_parser = sub.add_parser("env", help="检查 .env 配置状态")
    env_parser.add_argument(
        "--show-values",
        action="store_true",
        help="在终端中直接显示变量内容（默认仅提示是否已设置）",
    )
    env_parser.set_defaults(func=command_env)

    test_parser = sub.add_parser("test", help="隔离运行测试（cpp/java ↔ python 或 python/java → cpp）")
    test_parser.add_argument(
        "pipeline",
        choices=["cpp2py", "java2py", "py2cpp", "java2cpp"],
        help="选择要运行的测试方向",
    )
    test_parser.add_argument(
        "source_dir",
        help="包含 function_*.py 或 function_*.cpp 文件的源码目录",
    )
    test_parser.set_defaults(func=command_test)

    generate_parser = sub.add_parser("generate", help="一键运行七大策略的所有脚本")
    generate_parser.add_argument(
        "--strategies",
        nargs="+",
        choices=list(STRATEGY_DIRS.keys()),
        help="仅执行指定策略（默认全部）",
    )
    generate_parser.add_argument(
        "--dry-run",
        action="store_true",
        help="只列出将运行的脚本，不真正执行",
    )
    generate_parser.add_argument(
        "--stop-on-error",
        action="store_true",
        help="任意脚本失败时立即终止",
    )
    generate_parser.set_defaults(func=command_generate)

    return parser


def main() -> None:
    parser = build_parser()
    args = parser.parse_args()
    args.func(args)


if __name__ == "__main__":
    main()
