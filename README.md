# TransLibEval: Demystify Large Language Models’ Capability in Third-party Library-targeted Code Translation

TransLibEval is the first library-centric code translation benchmark presented in the paper " TransLibEval: Demystify Large Language Models’ Capability in Third-party Library-targeted Code Translation. " 





## 1 Dataset Overview

TransLibEval, a TPL-focused, multi-PL code translation benchmark with 200 method-level tasks built from a Python calibration across 50 widely used libraries (data processing, ML, web, visualization, NLP, utilities, etc.). Each Python task defines exactly one top-level class with a single instance method that calls a third-party API; method signatures use primitive types only. For every task, we provide parallel Java and C++ counterparts plus equivalent unit tests (Python unittest, Java JUnit via Maven, C++ GoogleTest via NuGet).

Each task’s test suite contains five test cases—normal input, edge input, exception handling, type validation, and resource-constraint—executed across all three languages. Code is comment-free, PEP 8 compliant, and Java/C++ follow official style and library selection rules.



### 1.1 End-to-End Running Example

For a step-by-step example that traces a single Python task through every translation strategy, build, test, and evaluation stage, see [`workflow_example.md`](./workflow_example.md).



### 1.2 Parallel Triplet Illustration

![](https://blogxiaozheng.oss-cn-beijing.aliyuncs.com/images/2.png)

The figure above visualizes one minimal unit inside the parallel corpus: `function_request_get` shown in Python, C++, and Java. Each block contains distilled code plus the corresponding third-party HTTP stack (`requests`, `libcurl`, `OkHttp + Jackson`). This snapshot demonstrates how every task in TransLibEval keeps the same control flow, timeout handling, JSON validation, and return semantics across the three languages, forming aligned triplets for training or evaluation.



### 1.3 Example of Test Cases 

`example: test_pyfftw_interfaces_numpy_fft_ifft` illustrates the five test cases end to end. The file wires up an `IFFTProcessor`, probes diverse Fourier inputs, and verifies shape/exception semantics.

```cpp
class TestIFFTFunction(unittest.TestCase):
    setUp(): self.processor = IFFTProcessor()
    test_ifft_simple_input(): assert "(4,)" for a mixed complex vector
    test_ifft_zero_input():    assert "(4,)" for an all-zero tensor
    test_ifft_invalid_input_real():   expect ValueError for purely real array
    test_ifft_invalid_input_string(): expect ValueError for string array
    test_ifft_large_input():   assert "(1000,)" for a 1k-length complex vector
```

| Test name                         | input                                           | Classification |
|----------------------------------|-------------------------------------------------------------|------------------------|
| `test_ifft_simple_input`         | Regular complex array succeeds                             | Nominal semantics      |
| `test_ifft_zero_input`           | Zero-filled array exercises a boundary case                | Boundary adherence     |
| `test_ifft_invalid_input_real`   | Real-only array violates Fourier contract                  | Exception semantics    |
| `test_ifft_invalid_input_string` | String array triggers explicit dtype validation            | Type conformance       |
| `test_ifft_large_input`          | 1000-length random vector stresses FFT resource handling   | Resource resilience    |

The corresponding Java and C++ suites mirror the same five situations, guaranteeing that the multilingual corpus enforces identical behavioral granularity.



## 2 Getting Started

1. **Clone the repository** and set up the environment.
2. **Data Preparation**: Ensure the TransLibEval dataset is available and ready for LLM invocation.
3. **Running Experiments**: Use the **Execute experiments with CLI** flow described in **Setup with Automated Execution**. We also provide a manually executed version, placed in the "manual" folder, see in **Setup with Manual Execution**.



## 3 Setup with Automated Execution

### 3.1 Environment Setup

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

### 3.2 Execute experiments with CLI

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



## 4 Setup with Manual Execution

To make third-party-library–aware code translation comparable across different LLMs, this repo ships lightweight invocation scripts per provider. Each script unifies a common CLI and prompt template for translation, so results are reproducible across models.

+ **DeepSeek Invocation Script:** The `deepseek.py` file calls the DeepSeek model with a predefined prompt for code translation.
+ **Qwen Invocation Script:** The `qwen-max.py` file invokes Alibaba Cloud models (Tongyi) with the standardized prompt and CLI.
+ etc.



### 4.1 Execute Code Translation with Diverse Strategies

All scripts live under `code/generate_strategies/<strategy_name>/` (one folder per strategy).  Concretely: 

- **Direct** / **RA(method)** — run the provider script **directly**.

  ```bash
  # <provider> ∈ {deepseek.py, qwen-max.py, ...}
  python code/generate_strategies/Direct/<provider>
  python code/generate_strategies/RA-method/<provider>
  ```

- **IR strategies (CoT, pseudocode, summary)** — run the **target-language** script under that strategy.

  ```bash
  # <ir> ∈ {IR（*）, e.g., IR（CoT）, IR（pseudocode）, IR(summary)}; <target_lang> ∈ {java, cpp, python}
  # Stage A: IR extraction
  python code/generate_strategies/<ir>/<target_lang>/Sum_<provider>
  
  # Stage A: translation
  python code/generate_strategies/<ir>/<target_lang>/<provider>
  ```

- **RA(name)** — first extract the function signature for the **target language**, then run the provider script.

  ```bash
  # <source_lang> ∈ {java, cpp, python}
  # Stage A.1: search query generation
  python code/generate_strategies/RA(name)/<target_lang>/signature.py \
    --out <source_lang>_api_results
    
  # Stage A.2: StackOverflow retrieval
  python code/generate_strategies/RA(name)/<target_lang>/Search.py # Just need to run once.
  
  # Stage B: translation
  python code/generate_strategies/RA(name)/<target_lang>/<provider>
  ```

- `<provider>` examples: `deepseek.py`, `qwen-max.py`, etc.

- All scripts share a common CLI (e.g., `--temperature`, `--max_tokens`, `--retries`) for consistent comparisons.



### 4.2 Execute Test Suite 

We provide automated test harnesses for Python (unittest),  Java (JUnit via Maven), and C++ (GoogleTest via CMake/CTest).  Each task includes exactly five test cases—normal input, edge input, exception handling, type validation, and resource-constraint—to enforce behavioral equivalence across languages.



#### 4.2.1 Dependencies

Before running any tests, install the third-party libraries listed under `data/requirements/`:

```
data/dependencies/
├── requirements.txt         # Python deps
├── java_third_party.txt     # Java deps (add to your build tool)
└── cpp_third_party.txt      # C++ deps (install via pkg manager)
```

- **Python:**

  ```
  python -m venv .venv && source .venv/bin/activate    # Windows: .venv\Scripts\activate
  pip install -r data/requirements/requirements.txt
  ```

- **Java:** Add the artifacts from `java_third_party.txt` to your **Maven/Gradle** build (or download JARs and update your `CLASSPATH`). Ensure versions match the list.

- **C++:** Install libraries from `cpp_third_party.txt` using your platform’s package manager (e.g., `apt`, `brew`) or a C++ package manager (e.g., **vcpkg**, **Conan**). Make sure headers and link libraries are discoverable by your compiler/CMake toolchain.



**Python**

+ Place the code to be tested under `code/test_suites/python/` as a **packaged folder** named `<source_lang>_to_<target_lang>` (e.g., `java_to_python`, `cpp_to_python`). Then run the following in order:
  1. **Compile & build:** `run_<source_lang>.py`
  2. **Bundle passing artifacts:** `copypy_<source_lang>.py`
  3. **Execute unit tests sequentially:** `automate_test_<source_lang>.py`

**C++**

+ Place the code to be tested under `code/test_suites/cpp/FunctionBuildTest/`, packaged inside the `src/` folder. Then run:

  1. **Compile & build:**

     ```
     cd code/test_suites/cpp/FunctionBuildTest
     bash build_script.sh
     ```

  2. **Bundle passing artifacts:**

     ```
     python select_tests.py
     ```

  3. **Execute unit tests sequentially:** (from the parent folder)

     ```
     cd ..
     python automate_test.py
     ```

**Java**

+ Configure dependencies & paths. Set up required libraries (e.g., via Maven/Gradle or local jars) and update all path placeholders inside the scripts to match your workspace.
+ Run integrator in a separate project. Execute `ProjectIntegrator.java` from an unrelated/bootstrapping project to assemble dependencies and lay out the build structure.
+ Discover & test in the code project. In the project that contains your functional code and test code, run `MethodFinder.java` first, then `JSONTestRunner.java` to execute the tests based on the discovered methods.



Each run produces a **JSON report** with the test results (the script prints the exact path). If model outputs contain superficial differences (formatting, minor strings) that cause spurious mismatches, use the regex-based normalization utilities under `normalization/` to post-process and align outputs with the test cases.



### 4.3 Prompt of Different Strategies

#### 4.3.1 Direct

> Example:Translate the following {from_lang} code to {to_lang}.\n\n
> Source Code:\n
> {source_example}\n\n
> Target Code:\n
> {target_example}\n\n
> Translate the following {from_lang} code to {to_lang}. Only return the translated code without any explanations or additional text.\n\n
> Source Code:\n
> {input_code.strip()}\n\n
> Target Code:\n



One-Shot  Example

```python
example_translations = {
    "python": """
        from faker import Faker

        class NameGenerator:
            def generate_name(self):
                fake = Faker()
                return fake.name()
    """,
    "java": """
        import com.github.javafaker.Faker;

        public class NameGenerator {
            public String generateName() {
                Faker faker = new Faker();
                return faker.name().fullName();
            }
        }
    """,
    "cpp": """
        #include <fakeit.hpp>
        #include <string>

        class NameGenerator {
        public:
            std::string generate_name() {
                fakeit::FakeIt fake;
                return fake.name();
            }
        };
    """
}
```



#### 4.3.2 IR (Pseudocode)

*Stage A (IR extraction)*

> Please analyze the following code and generate the corresponding pseudocode. The pseudocode should not reflect any specific language syntax or implementation details, and should focus solely on the core logic and steps of the algorithm. The pseudocode should be structured logically, describing the sequence of operations, decision-making processes, and function calls in a clear and understandable manner.
>
> Write only the pseudocode without any additional explanations or details.
>
> Class name: {class_name}. The Class name needs to appear
>
> Next, I will provide the source code; you must not directly mention the source code in your response:
> {source_code}

*Stage B (translation)*

> Please generate the {language} code that implements the following functionality:
>
> {pseudocode}
>
> Please ensure the code is complete and correctly follows the syntax and conventions for {language}, without including simple usage examples or test code. The code should directly implement the required functionality as described above.



#### 4.3.3. IR(summary)

*Stage A (IR extraction)*

> Please analyze the following code and generate a summary of its functionality. 
> The summary should not focus on specific language syntax, but should explain the key steps, 
> purpose of the code, and overall logic of the program or class in a concise manner.
>
> Class name: {class_name}. The class name should be included in the summary.
>
> Next, I will provide the source code; you must not directly mention the source code in your response:
> {source_code}

*Stage B (translation)*

> Please generate the {language} code that implements the following functionality:
>
> {summary}
>
> Please ensure the code is complete and correctly follows the syntax and conventions for {language}, without including simple usage examples or test code. The code should directly implement the required functionality as described above.



#### 4.3.4 IR(CoT)

*Stage A (IR extraction)*

> Please read the following source code for the class '{class_name}' and provide a step-by-step chain of thought that describes the logical flow and  steps. 
>
> Focus on the conceptual process rather than language-specific syntax. 
> Do not quote the exact source code.
>
> Class name: {class_name}. The Class name needs to appear
>
> Here is the code，which provides only a step-by-step chain of thought:
> {source_code}

*Stage B (translation)*

> Please generate the {language} code that implements the following functionality:
>
> {summary}
>
> Please ensure the code is complete and correctly follows the syntax and conventions for {language}, without including simple usage examples or test code. The code should directly implement the required functionality as described above.



#### 4.3.5 RA(name)

*Stage B (translation)*

> You are a world‑class expert in code translation with deep mastery of translating 
>  {from_lang} class methods into {target} implementations.\n\n
> Below are the precise function signature details and either community‑sourced reference 
> implementations or the original {src} code as fallback. Your task is to generate clean, 
> idiomatic, and fully functional {target} code that exactly matches the behavior.\n\n
> === Function Signature & Metadata ===\n
> {sig_json}\n\n
> === Reference Implementation ===\n
> {ref_impl}\n\n
> Produce only the final {target} code. Do not include any explanations, comments, or extra text.
> \n\nBegin {target} code now:\n



#### 4.3.6 RA(method)



*Stage A.1(search query generation)*

> Analyze the following code snippet written in {src}, and generate a single, concise, and well-formed question that summarizes the translation requirements of this code into {tgt}. The question should:\n
> 1. Be a simple sentence.\n
> 2. Avoid including the original code snippet directly.\n
> 3. Clearly describe the key functionality or purpose of the code that needs to be translated.\n
> 4. Be enclosed in triple single quotes (''').\n\n
>   Code snippet:
>

*Stage B (translation)*

>  You are a world‑class expert in code generation with deep mastery of translating  {src} class methods into {target} implementations.
>
>  Below are the precise function signature details and either community‑sourced reference implementations or the original {src} code as fallback. Your task is to generate clean, idiomatic, and fully functional {target} code that exactly matches the behavior.\n\n
>
>  === Function Signature & Metadata ===\n
>
>  {sig_json}\n\n
>
>  === Reference Implementation ===\n
>
>  {ref_impl}\n\n
>
>  Produce only the final {target} code. Do not include any explanations, comments, or extra text.\n\n
>
>  Begin {target} code now:\n



## 5 Research Questions (RQs)

### 5.1 RQ1 (Overall Correctness)

How do recent LLMs perform on library-centric code translation? Compared with general code translation, library-centric translation introduces additional challenges due to the need for correct recognition, import, and use of third-party libraries. Given the increasing prevalence of external library APIs in modern software development, it becomes critical to understand how well LLMs can handle such dependencies across languages.

![](https://blogxiaozheng.oss-cn-beijing.aliyuncs.com/images/20250910114103143.png)



### 5.2 RQ2 (Translation Strategies)

How do different translation strategies affect the performance of recent LLMs on library-centric code translation? Developers may adopt different prompting strategies like direct translation, Intermediate Representation (IR)-guided translation, or retrieval-augmented translation. Evaluating how these strategies impact the handling of library dependencies can offer practical guidance for prompt engineering and model deployment.

![](https://blogxiaozheng.oss-cn-beijing.aliyuncs.com/images/rq2.png)



### 5.3 RQ3 (Library Dependency Awareness)

How do the LLMs perform in identifying necessary and available libraries? Library-centric code translation often requires not just syntax conversion but also awareness of the necessary libraries for implementing equivalent functionalities. Considering that the library mappings between different PLs are not always one-to-one, LLMs have to infer a limited library set to accomplish the translation task in this work. Nonetheless, LLMs’ above-mentioned ability is still unknown, and the corresponding investigation has not been extensively conducted before, thus motivating us to delve into this RQ.

![](https://blogxiaozheng.oss-cn-beijing.aliyuncs.com/images/20250910114256860.png)

![](https://blogxiaozheng.oss-cn-beijing.aliyuncs.com/images/20250910114318937.png)



### 5.4 RQ4 (Failed Cases Analysis)

What kind of errors do LLMs make in library-centric translation, and how frequent are they? While prior work has examined LLM failures in method-, class- or even repo-level translation, their included libraries, even in repo-level, are limited as shown in Section 2. Thus, a large-scale fine-grained analysis of libraryrelated errors is still lacking, and identifying these failure modes can help the community develop more targeted improvements for LLM-based code translation systems.

For the precise taxonomy and operational definitions of error types, see **`failed_cases_report.pdf`**.

![](https://blogxiaozheng.oss-cn-beijing.aliyuncs.com/images/20250910114335463.png)

![](https://blogxiaozheng.oss-cn-beijing.aliyuncs.com/images/20250910114353580.png)



## 6 Requirements

+ **Python** 3.9 +
+ **C++** CMake ≥ 3.15, a C++20 compiler, and the following deps (recommended via **vcpkg**)
+ **Java** JDK 23



## 7 Library Versions & Dependencies

TransLibEval supports 50+ third-party libraries across three languages. Full dependency lists are located in `data/dependencies/`.

### 7.1 Python Dependencies

| Library | Version |
|---------|---------|
| numpy | 2.3.5 |
| pandas | 2.3.3 |
| scikit-learn | 1.7.2 |
| matplotlib | 3.10.0 |
| seaborn | 0.13.2 |
| bokeh | 3.6.3 |
| nltk | 3.9.2 |
| requests | 2.32.5 |
| beautifulsoup4 | 4.12.3 |
| marshmallow | 3.22.0 |
| jsonschema | 4.24.0 |
| networkx | 3.4.2 |
| pydantic | 2.10.3 |
| spacy | 3.8.2 |
| gensim | 4.3.3 |
| catboost | 1.2.8 |
| lightgbm | 4.4.1 |
| Polars | 1.12.0 |
| dask | 2024.12.0 |
| joblib | 1.4.2 |
| tqdm | 4.67.1 |
| tenacity | 8.7.0 |
| more-itertools | 10.6.0 |
| funcy | 2.0 |
| boltons | 24.1.1 |
| sortedcontainers | 2.4.0 |
| shapely | 2.0.6 |
| statsmodels | 0.14.2 |
| patsy | 0.5.6 |
| autopep8 | 2.3.1 |
| Cerberus | 1.3.5 |
| Schema | 0.7.7 |
| Schematics | 1.1.3 |
| Whoosh | 2.7.4 |
| algorithms | 1.0.5 |
| bitarray | 3.0.0 |
| cvxpy | 1.5.3 |
| levenshtein | 0.25.1 |
| missingno | 0.5.2 |
| mlxtend | 0.24.2 |
| peewee | 3.21.4 |
| pycrypto | 2.6.1 |
| pyphonetics | 1.0.5 |
| pyfftw | 0.14.1 |
| shutilwhich | 1.1.1 |

See `data/dependencies/requirements.txt` for complete list.

### 7.2 Java Dependencies

| Artifact ID | Version |
|-------------|---------|
| com.fasterxml.jackson.core:jackson-databind | 2.20.1 |
| com.fasterxml.jackson.core:jackson-core | 2.20.1 |
| com.google.guava:guava | 33.5.0-jre |
| org.jsoup:jsoup | 1.21.2 |
| org.apache.commons:commons-csv | 1.11.0 |
| org.apache.commons:commons-lang3 | 3.15.0 |
| org.apache.commons:commons-collections4 | 4.4 |
| org.apache.commons:commons-math3 | 3.6.1 |
| org.apache.commons:commons-text | 1.12.0 |
| edu.stanford.nlp:stanford-corenlp | 4.5.7 |
| org.jfree:jfreechart | 1.5.4 |
| org.deeplearning4j:deeplearning4j-core | 1.0.0-M2.1 |
| org.nd4j:nd4j-native-platform | 1.0.0-M2.1 |
| nz.ac.waikato.cms.weka:weka-stable | 3.8.6 |
| io.reactivex.rxjava3:rxjava | 3.1.10 |
| org.apache.httpcomponents:httpclient | 4.5.14 |
| org.json:json | 20241210 |
| com.github.haifengl:smile-core | 3.1.1 |
| com.opencsv:opencsv | 5.10 |
| com.squareup.okhttp3:okhttp | 4.12.0 |
| commons-cli:commons-cli | 1.8.0 |
| commons-codec:commons-codec | 1.17.1 |
| commons-io:commons-io | 2.16.1 |
| com.networknt:json-schema-validator | 1.5.1 |
| dev.failsafe:failsafe | 3.3.2 |
| jakarta.validation:jakarta.validation-api | 3.0.2 |
| joda-time:joda-time | 2.13.0 |
| me.tongfei:progressbar | 0.10.1 |
| org.apache.lucene:lucene-core | 9.11.1 |
| org.apache.opennlp:opennlp-tools | 2.4.0 |
| org.bouncycastle:bcprov-jdk18on | 1.78.1 |
| org.everit.json:org.everit.json.schema | 1.14.4 |
| org.jdbi:jdbi3-core | 3.46.1 |
| org.jgrapht:jgrapht-core | 1.5.2 |
| org.knowm:xchart | 3.8.9 |
| org.locationtech.jts:jts-core | 1.18.2 |
| org.ojalgo:ojalgo | 53.2.0 |
| org.springframework:spring-context | 6.1.14 |
| org.sql2o:sql2o | 1.6.0 |
| org.tartarus:snowball | 2.2.0 |
| tech.tablesaw:tablesaw-core | 0.43.1 |
| io.projectreactor:reactor-core | 2023.12.1 |

See `data/dependencies/java_third_party.txt` for complete list.

### 7.3 C++ Dependencies

| Library | Version |
|---------|---------|
| boost | 1.85.0 |
| Eigen | 3.4.0 |
| nlohmann-json | 3.11.3 |
| rapidjson | 1.1.0 |
| libcurl | 8.10.1 |
| libxml2 | 2.13.5 |
| sqlite3 | 3.47.0 |
| fmt | 11.0.2 |
| indicators | 2.3 |
| xtensor | 0.25.0 |
| xgboost | 2.1.1 |
| CLI | 2.4.2 |
| cryptopp | 8.10.0 |
| date | 3.0.1 |
| fasttext | 0.9.2 |
| llvm | 18.1.8 |
| plplot | 5.15.0 |
| range | 3.12.0 |
| clang | 18.1.8 |

See `data/dependencies/cpp_third_party.txt` for complete list.
