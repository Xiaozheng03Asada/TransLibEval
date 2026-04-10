## Getting Started

1. **Clone the repository** and set up the environment.
2. **Data Preparation**: Ensure the TransLibEval dataset is available and ready for LLM invocation.
3. **Running Experiments**: Use the **Execute experiments with CLI** flow described in **Setup with Automated Execution**. We also provide a manually executed version, placed in the "manual" folder, see in **Setup with Manual Execution**.



## Setup with Automated Execution

### Environment Setup

This repository includes a lightweight CLI that can perform common setup and run tasks in an automated fashion. The "one-click" flow below is designed for a macOS development machine with zsh. It will: create a Python virtual environment, install Python dependencies, verify configuration, and optionally run translation and test suites.

Prerequisites

- Git
- Python 3.9 or newer
- pip
- A system package manager for platform-specific C++ dependencies (e.g., Homebrew on macOS)
- JDK (for Java runs)

Quick one-command deployment (recommended for new users):

1. Clone the repo and enter the project directory.

```bash
git clone https://github.com/your-org/TransLibEval.git
cd TransLibEval
```

1. Copy environment template and fill required API keys.

```bash
cp .env.example .env
# Edit .env and add keys (OPENAI_API_KEY, QWEN_API_KEY, DEEPSEEK_KEY, etc.)
```

1. Create and activate a Python virtual environment, then install Python dependencies.

```bash
python -m venv .venv
source .venv/bin/activate   # macOS/Linux (zsh)
pip install --upgrade pip
pip install -r data/dependencies/requirements.txt
```

1. (Optional) Install platform C++ dependencies via Homebrew or your preferred package manager. Check `data/dependencies/cpp_third_party.txt` for details. Java dependencies also need to be manually installed

```bash
# Example (macOS):
brew install some-cpp-lib another-lib
```

1. Quick configuration check and run generator/test pipelines using the included CLI.

```bash
python scripts/translib_cli.py env            # show env/config status; use --show-values to print values
python scripts/translib_cli.py generate --strategies direct --dry-run  # dry run translation
python scripts/translib_cli.py generate --strategies direct            # run translation
python scripts/translib_cli.py test cpp2py data/python/src             # isolated cpp→python tests
python scripts/translib_cli.py test java2py path/to/py_funcs
python scripts/translib_cli.py test py2cpp data/cpp/src                # copy function_*.cpp -> C++ suite/src & run
python scripts/translib_cli.py test java2cpp path/to/cpp_funcs
```

### Execute experiments with CLI

1. `python scripts/translib_cli.py env [--show-values]`  
   Reports readiness of required env vars (OpenAI/Qwen/Qianfan/Deepseek/Google CSE). Use `--show-values` only when it is safe to print secrets.

2. `python scripts/translib_cli.py generate [--strategies ...] [--dry-run] [--stop-on-error]`  
   Runs the high-level translation scripts:
   - `--strategies`: any subset of `direct`, `ra-method`, `ra-name`, `ir-pseudocode`, `ir-summary`, `ir-cot`.  
   - IR strategies run `Sum_*` scripts before the base scripts. RA(name) runs `signature.py`, then `Search.py`, then all model drivers per language.  
   - `--dry-run` lists scripts without executing; `--stop-on-error` aborts on the first failure.

3. `python scripts/translib_cli.py test <pipeline> <source_dir>`  
   Builds an isolated copy of the test suites, injects your generated files, and executes the existing harness:
   - `<pipeline>` ∈ {`cpp2py`, `java2py`, `py2cpp`, `java2cpp`}.  
   - `cpp2py/java2py`: expect `source_dir` to contain `function_*.py`, copy them into `cpp_to_python` or `java_to_python`, then run `run_* → copypy_* → automate_test_*`.  
   - `py2cpp/java2cpp`: expect `function_*.cpp`, replace `FunctionBuildTest/src`, then run `build_script.sh → select_tests.py → automate_test.py`.  
   - Each run happens in a temp directory (original suites remain untouched). Result JSON files—or `FAILED.txt` when the harness stops early—are archived under `test_results/<timestamp>_<pipeline>/`.

Notes & tips

- Use `--dry-run` first to validate what will be executed.
- If you have limited API quota, run `generate` with `--stop-on-error` and small batches.
- For CI, export required env vars and run the same commands in your pipeline.
