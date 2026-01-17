from __future__ import annotations

import shutil
import subprocess
import sys
import tempfile
import time
from dataclasses import dataclass
from datetime import datetime
from pathlib import Path
from typing import Iterable, List, Sequence

ROOT = Path(__file__).resolve().parents[1]
PYTHON_SUITE = ROOT / "code" / "test_suites" / "python"
CPP_SUITE = ROOT / "code" / "test_suites" / "cpp"
RESULT_ARCHIVE = ROOT / "test_results"


@dataclass
class StageResult:
    name: str
    command: Sequence[str]
    cwd: Path
    duration: float
    success: bool
    skipped: bool = False
    reason: str | None = None


def _run(name: str, command: Sequence[str], cwd: Path) -> StageResult:
    print(f"\n>>> {name}: {' '.join(command)} (cwd={cwd})")
    start = time.time()
    proc = subprocess.run(command, cwd=cwd)
    duration = time.time() - start
    status = "OK" if proc.returncode == 0 else f"FAILED ({proc.returncode})"
    print(f"<<< {name} {status} in {duration:.1f}s")
    return StageResult(name=name, command=command, cwd=cwd, duration=duration, success=proc.returncode == 0)


def _skip(name: str, command: Sequence[str], cwd: Path, reason: str) -> StageResult:
    print(f"\n>>> {name}: 跳过（{reason}）")
    return StageResult(name=name, command=command, cwd=cwd, duration=0.0, success=False, skipped=True, reason=reason)


def run_python_pipeline(sources: Iterable[str]) -> dict[str, List[StageResult]]:
    configs = {
        "java": {
            "label": "Java → Python",
            "folder": PYTHON_SUITE / "java_to_python",
            "scripts": [
                ("运行 Java 输出", [sys.executable, "run_java.py"]),
                ("挑选通过用例", [sys.executable, "copypy_java.py"]),
                ("执行单测", [sys.executable, "automate_test_java.py"]),
            ],
        },
        "cpp": {
            "label": "C++ → Python",
            "folder": PYTHON_SUITE / "cpp_to_python",
            "scripts": [
                ("运行 C++ 输出", [sys.executable, "run_cpp.py"]),
                ("挑选通过用例", [sys.executable, "copypy_cpp.py"]),
                ("执行单测", [sys.executable, "automate_test_cpp.py"]),
            ],
        },
    }
    summary: dict[str, List[StageResult]] = {}
    for source in sources:
        meta = configs[source]
        label = meta["label"]
        steps = meta["scripts"]
        folder = meta["folder"]
        results: List[StageResult] = []
        if not folder.exists():
            results.append(_skip(label, steps[0][1], PYTHON_SUITE, f"缺少 {folder.relative_to(ROOT)} 目录"))
            summary[source] = results
            continue
        for step_name, command in steps:
            result = _run(step_name, command, PYTHON_SUITE)
            results.append(result)
            if not result.success:
                break
        summary[source] = results
    return summary


def run_cpp_pipeline() -> List[StageResult]:
    build_dir = CPP_SUITE / "FunctionBuildTest"
    steps = [
        ("C++ 编译", ["bash", "build_script.sh"], build_dir),
        ("挑选可执行用例", [sys.executable, "select_tests.py"], build_dir),
        ("执行 GoogleTest", [sys.executable, "automate_test.py"], CPP_SUITE),
    ]
    results: List[StageResult] = []
    if not build_dir.exists():
        return [
            _skip("C++ 测试", steps[0][1], build_dir, f"缺少 {build_dir.relative_to(ROOT)} 目录"),
        ]
    for name, command, cwd in steps:
        result = _run(name, command, cwd)
        results.append(result)
        if not result.success:
            break
    return results


def _copy_python_suite() -> Path:
    tmp_root = Path(tempfile.mkdtemp(prefix="translib_py_suite_"))
    suite_dir = tmp_root / "python_suite"
    shutil.copytree(PYTHON_SUITE, suite_dir)
    return suite_dir


def _prepare_python_sources(pipeline: str, source_dir: Path, suite_dir: Path) -> int:
    source_dir = source_dir.resolve()
    if not source_dir.exists():
        raise FileNotFoundError(f"源码目录不存在: {source_dir}")
    target_name = "cpp_to_python" if pipeline == "cpp2py" else "java_to_python"
    target_dir = suite_dir / target_name
    if target_dir.exists():
        shutil.rmtree(target_dir)
    target_dir.mkdir()
    count = 0
    for item in sorted(source_dir.rglob("function_*.py")):
        if item.is_file():
            shutil.copy2(item, target_dir / item.name)
            count += 1
    if count == 0:
        raise RuntimeError(f"{source_dir} 中未找到 function_*.py 文件")
    return count


def _run_python_commands(pipeline: str, suite_dir: Path) -> tuple[bool, list[StageResult]]:
    if pipeline == "cpp2py":
        commands = [
            ("运行 C++ 代码", [sys.executable, "run_cpp.py"]),
            ("复制通过用例", [sys.executable, "copypy_cpp.py"]),
            ("执行 Python 测试", [sys.executable, "automate_test_cpp.py"]),
        ]
    else:
        commands = [
            ("运行 Java 代码", [sys.executable, "run_java.py"]),
            ("复制通过用例", [sys.executable, "copypy_java.py"]),
            ("执行 Python 测试", [sys.executable, "automate_test_java.py"]),
        ]
    results: list[StageResult] = []
    for name, cmd in commands:
        result = _run(name, cmd, suite_dir)
        results.append(result)
        if not result.success:
            return False, results
    return True, results


def _copy_cpp_suite() -> Path:
    tmp_root = Path(tempfile.mkdtemp(prefix="translib_cpp_suite_"))
    suite_dir = tmp_root / "cpp_suite"
    shutil.copytree(CPP_SUITE, suite_dir)
    return suite_dir


def _prepare_cpp_sources(source_dir: Path, suite_dir: Path) -> int:
    source_dir = source_dir.resolve()
    if not source_dir.exists():
        raise FileNotFoundError(f"源码目录不存在: {source_dir}")
    target_dir = suite_dir / "FunctionBuildTest" / "src"
    if target_dir.exists():
        shutil.rmtree(target_dir)
    target_dir.mkdir()
    count = 0
    for item in sorted(source_dir.rglob("function_*.cpp")):
        if item.is_file():
            shutil.copy2(item, target_dir / item.name)
            count += 1
    if count == 0:
        raise RuntimeError(f"{source_dir} 中未找到 function_*.cpp 文件")
    return count


def _run_cpp_commands(suite_dir: Path) -> tuple[bool, list[StageResult]]:
    build_dir = suite_dir / "FunctionBuildTest"
    commands = [
        ("C++ 构建", ["bash", "build_script.sh"], build_dir),
        ("筛选可执行用例", [sys.executable, "select_tests.py"], build_dir),
        ("执行 GoogleTest", [sys.executable, "automate_test.py"], suite_dir),
    ]
    results: list[StageResult] = []
    for name, cmd, cwd in commands:
        res = _run(name, cmd, cwd)
        results.append(res)
        if not res.success:
            return False, results
    return True, results


def run_conversion_tests(pipeline: str, source_dir: Path) -> Path:
    RESULT_ARCHIVE.mkdir(parents=True, exist_ok=True)
    if pipeline in {"cpp2py", "java2py"}:
        suite_dir = _copy_python_suite()
        tmp_root = suite_dir.parent
        result_name = "cpp2python_test_results.json" if pipeline == "cpp2py" else "java2python_test_results.json"
        try:
            copied = _prepare_python_sources(pipeline, Path(source_dir), suite_dir)
            print(f"已复制 {copied} 个 function_*.py 文件到临时目录。")
            success, _ = _run_python_commands(pipeline, suite_dir)
            result_path = suite_dir / result_name
            timestamp = datetime.now().strftime("%Y%m%d-%H%M%S")
            dest_dir = RESULT_ARCHIVE / f"{timestamp}_{pipeline}"
            dest_dir.mkdir(parents=True, exist_ok=True)
            if result_path.exists():
                shutil.copy2(result_path, dest_dir / result_name)
                print(f"测试结果已保存到 {dest_dir / result_name}")
            else:
                note = dest_dir / "FAILED.txt"
                note.write_text("测试流程未生成结果文件，请查看上方日志。\n", encoding="utf-8")
                print("⚠️ 测试失败且未生成结果 JSON，已在 FAILED.txt 中记录。")
            if not success:
                print("部分测试阶段失败，但流程未被中断。")
            return dest_dir
        finally:
            shutil.rmtree(tmp_root, ignore_errors=True)
    else:
        suite_dir = _copy_cpp_suite()
        tmp_root = suite_dir.parent
        result_name = "results.json"
        try:
            copied = _prepare_cpp_sources(Path(source_dir), suite_dir)
            print(f"已复制 {copied} 个 function_*.cpp 文件到临时目录。")
            success, _ = _run_cpp_commands(suite_dir)
            result_path = suite_dir / result_name
            timestamp = datetime.now().strftime("%Y%m%d-%H%M%S")
            dest_dir = RESULT_ARCHIVE / f"{timestamp}_{pipeline}"
            dest_dir.mkdir(parents=True, exist_ok=True)
            if result_path.exists():
                shutil.copy2(result_path, dest_dir / result_name)
                print(f"测试结果已保存到 {dest_dir / result_name}")
            else:
                note = dest_dir / "FAILED.txt"
                note.write_text("测试流程未生成结果文件，请查看上方日志。\n", encoding="utf-8")
                print("⚠️ 测试失败且未生成结果 JSON，已在 FAILED.txt 中记录。")
            if not success:
                print("部分测试阶段失败，但流程未被中断。")
            return dest_dir
        finally:
            shutil.rmtree(tmp_root, ignore_errors=True)
