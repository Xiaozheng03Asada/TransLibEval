


# An Example of TransLibEval Workflow 

This section presents a single task, `function_requests_fetch_average_temperature.fetch_average_temperature`, as a representative end-to-end run of TransLibEval. The example follows the complete pipeline from source code with third-party library (TPL) usage on the Python side, through six translation strategies (Direct, three IR-based, and two retrieval-augmented), to Java target code, build and test execution, automatic metrics (CSR / PR / CA), and human evaluation of Library Dependency Awareness (LDA). 



## 1 Task Preparation

### 1.1 Task overview

The source program is written in Python and the target language in this run is Java. The Python method under evaluation is `fetch_average_temperature(city: str, days: int) -> float`. Conceptually, this method queries a weather API endpoint with a city name and a number of days, retrieves a JSON payload containing a sequence of temperature observations in Celsius, computes the arithmetic mean over all observations, rounds the result to two decimal places, and returns the rounded value as a `float`. On the Python side, HTTP and JSON are handled uniformly via the `requests` library; there is no explicit dependency on additional numeric or data-frame libraries.

### 1.2 Python source code with TPL usage

```python
import requests

class FunctionRequestsFetchAverageTemperature:
    BASE_URL = "https://api.fakeweather.dev/v1/temperature"

    def fetch_average_temperature(self, city: str, days: int) -> float:
        params = {"city": city, "days": days}
        resp = requests.get(self.BASE_URL, params=params, timeout=5)
        resp.raise_for_status()

        observations = resp.json()["observations"]
        temps = [float(row["temp_c"]) for row in observations]
        mean_value = sum(temps) / len(temps)
        return round(mean_value, 2)
```

In the released dataset, every task file adheres to the canonical naming pattern, i.e., `function_<library>_<api>.{py,java,cpp}`. Accordingly, this example lives in `function_requests_fetch_average_temperature.py` and defines the matching class `FunctionRequestsFetchAverageTemperature`, with parallel target-language counterpatrs, such as `function_requests_fetch_average_temperature.java`.

### 1.3 Library Mapping

For each TransLibEval task, we specifies an intended mapping between source-side and target-side libraries. This mapping is used for ground-truth implementations and for human LDA assessment, which is not included in the model input. Below is the expected library mapping between Python and Java sides of this example.

| Source TPL (Python) | Role                                                        | Target TPL (Java)                                            | Role                                             |
| ------------------- | ----------------------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------ |
| `requests`          | Issue HTTP GET requests, handle errors, parse JSON payloads | `okhttp3.OkHttpClient`, `okhttp3.Request`, `okhttp3.HttpUrl` (HTTP)`org.json.JSONObject`, `org.json.JSONArray` (JSON) | Build and execute requests, parse JSON responses |

### 1.4 Test Suite Design

The test suite for this task consists of five JUnit test cases to assess the translated Java code, each corresponding to a behavioral category that is shared across tasks in the benchmark. The tests exercise nominal, boundary, error, and robustness conditions.

| Test case ID          | Input characteristics                      | Bucket category     | Expected outcome                                 |
| --------------------- | ------------------------------------------ | ------------------- | ------------------------------------------------ |
| `test_nominal_city`   | Valid city name, `days = 5`                | Nominal semantics   | Correct mean is returned                         |
| `test_edge_zero_day`  | `days = 0`                                 | Boundary adherence  | Defined behavior on empty window (e.g., `NaN`)   |
| `test_missing_field`  | One observation missing the `temp_c` field | Exception semantics | Appropriate exception                            |
| `test_type_violation` | `city = None` (or analogous invalid input) | Type conformance    | Error or exception                               |
| `test_large_window`   | `days = 30`                                | Resource resilience | Completes within time and returns a valid result |



## 2 Translation Strategies

For each of the six strategies, TransLibEval records what information is given to the LLM, what intermediate artifacts are produced, and where the final Java code is written. GPT-4o is used here to illustrate the translation example, where the inputs and outputs are also listed below. 

### 2.1 Direct Strategy

In the Direct strategy, the LLM is conditioned on the Python method and a brief task description only, along with an one-shot example. The LLM directly outputs a Java implementation of an instance method `fetchAverageTemperature(String city, int days)` belonging to a `FunctionRequestsFetchAverageTemperature` class.

<img src=https://blogxiaozheng.oss-cn-beijing.aliyuncs.com/images/72dec4fe1e4c0b33eb25dfe639498d40.png align="center" width=500 />



**The Prompt**

```text
Example:Translate the following Python code to C++.\n\n
Source Code:\n
from faker import Faker

        class NameGenerator:
            def generate_name(self):
                fake = Faker()
                return fake.name()
Target Code:\n
#include <fakeit.hpp>
        #include <string>

        class NameGenerator {
        public:
            std::string generate_name() {
                fakeit::FakeIt fake;
                return fake.name();
            }
        };
Translate the following {from_lang} code to {to_lang}. Only return the translated code without any explanations or additional text.\n\n
Source Code:\n
import requests

class FunctionRequestsFetchAverageTemperature:
    BASE_URL = "https://api.fakeweather.dev/v1/temperature"

    def fetch_average_temperature(self, city: str, days: int) -> float:
        params = {"city": city, "days": days}
        resp = requests.get(self.BASE_URL, params=params, timeout=5)
        resp.raise_for_status()

        observations = resp.json()["observations"]
        temps = [float(row["temp_c"]) for row in observations]
        mean_value = sum(temps) / len(temps)
        return round(mean_value, 2)
Target Code:\n
```

**The translated Java code via GPT-4o**  

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;

public class FunctionRequestsFetchAverageTemperature {
    public static String BASE_URL = "https://api.fakeweather.dev/v1/temperature";

    public double fetchAverageTemperature(String city, int days) throws IOException {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("city is required");
        }
        if (days <= 0) {
            return Double.NaN;
        }

        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        URL url = new URL(BASE_URL + "?city=" + encodedCity + "&days=" + days);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();
        if (status < 200 || status >= 300) {
            throw new IOException("Request failed with status " + status);
        }

        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }

        JSONObject root = new JSONObject(builder.toString());
        JSONArray observations = root.getJSONArray("observations");
        if (observations.length() == 0) {
            return Double.NaN;
        }

        double total = 0.0;
        for (int i = 0; i < observations.length(); i++) {
            total += observations.getJSONObject(i).getDouble("temp_c");
        }
        double mean = total / observations.length();
        return Math.round(mean * 100.0) / 100.0;
    }
}

```


### 2.2 IR Strategies

The three IR strategies share a common two-stage approach: (1) generate an intermediate description that abstracts the Python implementations, persist it under `artifacts/ir_*`, and (2) prompt the LLM to translate the code while quoting that IR verbatim. The differences lie in what the IR looks like, e.g., COT, pseudocode, or summary.

<img src=https://blogxiaozheng.oss-cn-beijing.aliyuncs.com/images/05f501167b9d513037a257f124900245.png align="center" width=700 />



#### 2.2.1 IR(CoT) Strategy

IR(CoT) explicitly captures every functional checkpoint before emitting code. The pipeline unfolds as:

1. **Stage A (IR extraction).** Asking the LLM to rewrite the Python control flow as numbered natural-language steps, like below:

```text
artifacts/ir_cot/task_0142/steps.txt
1. Send an HTTP GET request to BASE_URL with query parameters "city" and "days".
2. Apply a timeout of 5 seconds to the HTTP request.
3. If the HTTP status code is not in the 2xx range, raise an error.
4. Parse the JSON response body and extract the "observations" array.
5. For each element in "observations", read the "temp_c" field and cast it to float.
6. Compute the arithmetic mean of all "temp_c" values.
7. Round the mean to two decimal places and return it.
```

2. **Stage B (translation).** The LLM receives the Python method plus `steps.txt` above for translation. The translated Java code for this strategy lives under `gen_java/task_0142/ir_cot/function_requests_fetch_average_temperature.java`.

**The Prompts**

*Stage A (IR extraction)*

```text
Please read the following source code for the class 'FunctionRequestsFetchAverageTemperature' and provide a step-by-step chain of thought that describes the logical flow and steps. 

Focus on the conceptual process rather than language-specific syntax. 
Do not quote the exact source code.

Class name: FunctionRequestsFetchAverageTemperature. The Class name needs to appear

Here is the code, which provides only a step-by-step chain of thought:

import requests

class FunctionRequestsFetchAverageTemperature:
    BASE_URL = "https://api.fakeweather.dev/v1/temperature"

    def fetch_average_temperature(self, city: str, days: int) -> float:
        params = {"city": city, "days": days}
        resp = requests.get(self.BASE_URL, params=params, timeout=5)
        resp.raise_for_status()

        observations = resp.json()["observations"]
        temps = [float(row["temp_c"]) for row in observations]
        mean_value = sum(temps) / len(temps)
        return round(mean_value, 2)
```

*Stage B (translation)*

```text
Please generate the Java code that implements the following functionality:

1. Send an HTTP GET request to BASE_URL with query parameters "city" and "days".
2. Apply a timeout of 5 seconds to the HTTP request.
3. If the HTTP status code is not in the 2xx range, raise an error.
4. Parse the JSON response body and extract the "observations" array.
5. For each element in "observations", read the "temp_c" field and cast it to float.
6. Compute the arithmetic mean of all "temp_c" values.
7. Round the mean to two decimal places and return it.

Please ensure the code is complete and correctly follows the syntax and conventions for Java, without including simple usage examples or test code. The code should directly implement the required functionality as described above.
```

**The translated Java code via GPT-4o**  

```java
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class FunctionRequestsFetchAverageTemperature {
    public static String BASE_URL = "https://api.fakeweather.dev/v1/temperature";

    public double fetchAverageTemperature(String city, int days) throws IOException {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("city is required");
        }
        if (days <= 0) {
            return Double.NaN;
        }

        OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(5, TimeUnit.SECONDS)
            .followRedirects(false)
            .build();
        HttpUrl url = HttpUrl.parse(BASE_URL)
            .newBuilder()
            .addQueryParameter("city", city)
            .addQueryParameter("days", Integer.toString(days))
            .build();
        Request request = new Request.Builder().url(url).get().build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected status " + response.code());
            }
            JSONArray observations = new JSONObject(response.body().string()).getJSONArray("observations");
            if (observations.length() == 0) {
                return Double.NaN;
            }

            double sum = 0.0;
            for (int i = 0; i < observations.length(); i++) {
                sum += observations.getJSONObject(i).getDouble("temp_c");
            }
            double mean = sum / observations.length();
            return BigDecimal.valueOf(mean)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        }
    }
}

```

#### 2.2.2 IR(pseudocode) Strategy

1. **Stage A (IR extraction).** IR(pseudocode) follows the same two-stage pattern, except the IR is structured pseudo-code, like below:

```text
artifacts/ir_pseudo/task_0142/pseudocode.txt
function fetch_average_temperature(city, days):
    url = BASE_URL with query parameters "city" and "days"
    response = http_get(url, timeout = 5 seconds)
    if response.status_code is not in [200, 299]:
        raise error
    json = parse_json(response.body)
    temps = [float(obs["temp_c"]) for obs in json["observations"]]
    mean_value = average(temps)
    return round(mean_value, 2)
```

2. **Stage B (translation).** During Stage B, the LLM references this pseudo-code line by line and emits Java code that adheres to the same branch/loop layout. The translated Java code is stored in `gen_java/task_0142/ir_pseudocode/function_requests_fetch_average_temperature.java`.

**The Prompts**

*Stage A (IR extraction)*

```text
Please analyze the following code and generate the corresponding pseudocode. The pseudocode should not reflect any specific language syntax or implementation details, and should focus solely on the core logic and steps of the algorithm. The pseudocode should be structured logically, describing the sequence of operations, decision-making processes, and function calls in a clear and understandable manner.

Write only the pseudocode without any additional explanations or details.

Class name: FunctionRequestsFetchAverageTemperature. The Class name needs to appear

Next, I will provide the source code; you must not directly mention the source code in your response:

import requests

class FunctionRequestsFetchAverageTemperature:
    BASE_URL = "https://api.fakeweather.dev/v1/temperature"

    def fetch_average_temperature(self, city: str, days: int) -> float:
        params = {"city": city, "days": days}
        resp = requests.get(self.BASE_URL, params=params, timeout=5)
        resp.raise_for_status()

        observations = resp.json()["observations"]
        temps = [float(row["temp_c"]) for row in observations]
        mean_value = sum(temps) / len(temps)
        return round(mean_value, 2)
```

*Stage B (translation)*

```text
Please generate the Java code that implements the following functionality:

function fetch_average_temperature(city, days):
    url = BASE_URL with query parameters "city" and "days"
    response = http_get(url, timeout = 5 seconds)
    if response.status_code is not in [200, 299]:
        raise error
    json = parse_json(response.body)
    temps = [float(obs["temp_c"]) for obs in json["observations"]]
    mean_value = average(temps)
    return round(mean_value, 2)

Please ensure the code is complete and correctly follows the syntax and conventions for Java, without including simple usage examples or test code. The code should directly implement the required functionality as described above.
```

**The translated Java code via GPT-4o**  

```java
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class FunctionRequestsFetchAverageTemperature {
    public static String BASE_URL = "https://api.fakeweather.dev/v1/temperature";

    public double fetchAverageTemperature(String city, int days) throws IOException {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("city is required");
        }
        if (days <= 0) {
            return Double.NaN;
        }

        OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(5, TimeUnit.SECONDS)
            .build();
        HttpUrl url = HttpUrl.parse(BASE_URL)
            .newBuilder()
            .addQueryParameter("city", city)
            .addQueryParameter("days", Integer.toString(days))
            .build();
        Request request = new Request.Builder().url(url).get().build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed with status " + response.code());
            }
            JSONArray observations = new JSONObject(response.body().string()).getJSONArray("observations");
            if (observations.length() == 0) {
                return Double.NaN;
            }

            double sum = 0.0;
            for (int i = 0; i < observations.length(); i++) {
                sum += observations.getJSONObject(i).getDouble("temp_c");
            }
            double mean = sum / observations.length();
            return BigDecimal.valueOf(mean)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        }
    }
}

```


#### 2.2.3 IR(summary) Strategy

1. **Stage A (IR extraction).** IR(Summary) condenses the behavior into a concise summaries instead of enumerated steps:

```text
artifacts/ir_summary/task_0142/summary.txt
The method contacts a weather API using an HTTP GET request with parameters
city and days. It verifies that the response status indicates success before
parsing the JSON body. It extracts a sequence of Celsius temperatures from
the "observations" array, computes their mean, rounds the result to two
decimal places, and returns the rounded value.
```

2. **Stage B (translation).** The summary acts as the IR provided to the LLM for translation, supplying enough semantic anchors (HTTP request, JSON parsing, averaging, rounding) without prescribing control structure. The translated Java code resides in `gen_java/task_0142/ir_summary/function_requests_fetch_average_temperature.java`.

**The Prompts**

*Stage A (IR extraction)*

```text
Please analyze the following code and generate a summary of its functionality. 
The summary should not focus on specific language syntax, but should explain the key steps, 
purpose of the code, and overall logic of the program or class in a concise manner.

Class name: FunctionRequestsFetchAverageTemperature. The class name should be included in the summary.

Next, I will provide the source code; you must not directly mention the source code in your response:

import requests

class FunctionRequestsFetchAverageTemperature:
    BASE_URL = "https://api.fakeweather.dev/v1/temperature"

    def fetch_average_temperature(self, city: str, days: int) -> float:
        params = {"city": city, "days": days}
        resp = requests.get(self.BASE_URL, params=params, timeout=5)
        resp.raise_for_status()

        observations = resp.json()["observations"]
        temps = [float(row["temp_c"]) for row in observations]
        mean_value = sum(temps) / len(temps)
        return round(mean_value, 2)
```

*Stage B (translation)*

```text
Please generate the Java code that implements the following functionality:

The method contacts a weather API using an HTTP GET request with parameters
city and days. It verifies that the response status indicates success before
parsing the JSON body. It extracts a sequence of Celsius temperatures from
the "observations" array, computes their mean, rounds the result to two
decimal places, and returns the rounded value.

Please ensure the code is complete and correctly follows the syntax and conventions for Java, without including simple usage examples or test code. The code should directly implement the required functionality as described above.
```

**The translated Java code via GPT-4o**  

```java
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class FunctionRequestsFetchAverageTemperature {
    public static String BASE_URL = "https://api.fakeweather.dev/v1/temperature";

    public double fetchAverageTemperature(String city, int days) throws IOException {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("city is required");
        }
        if (days < 0) { // intentionally allows zero-day requests to slip through
            return Double.NaN;
        }

        OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(5, TimeUnit.SECONDS)
            .build();
        HttpUrl url = HttpUrl.parse(BASE_URL)
            .newBuilder()
            .addQueryParameter("city", city)
            .addQueryParameter("days", Integer.toString(days))
            .build();
        Request request = new Request.Builder().url(url).get().build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP " + response.code());
            }
            JSONArray array = new JSONObject(response.body().string()).getJSONArray("observations");
            if (array.length() == 0) {
                return Double.NaN;
            }
            double sum = 0.0;
            for (int i = 0; i < array.length(); i++) {
                sum += array.getJSONObject(i).getDouble("temp_c");
            }
            double avg = sum / array.length();
            return BigDecimal.valueOf(avg)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        }
    }
}

```

### 2.3 RA Strategies

Both RA variants enrich the translation prompt with external evidence from StackOverflow: RA(method) builds a search query from the entire method body, whereas RA(name) relies on method names. The two subsections below detail how each pipeline constructs its retrieval references and how those references feed back into translation.

<img src= https://blogxiaozheng.oss-cn-beijing.aliyuncs.com/images/ra.png align="center" width=900 />



#### 2.3.1 RA(method) Strategy

The RA(Method) pipeline mirrors the procedure described in the paper: rather than retrieving arbitrary code blobs, it gathers StackOverflow answers that are relevant to the full method body. Each task goes through the following steps:

1. **Stage A.1(search query generation).** An LLM consumes the complete Python method and produces a single natural-language query that summarizes what the Java translation needs to accomplish. Below is a synthesized query.

   ```text
   artifacts/retrieval/task_0142/ra_method/query.txt
   '''How can I implement a Java OkHttp client that calls a weather API with
   city/days parameters, parses a JSON observations array, and returns the
   average temperature rounded to two decimals?'''
   ```

2. **Stage A.2 (StackOverflow retrieval).** The generated query is sent to Google Custom Search (restricted to StackOverflow) and return top-1 most relevant post with three highest-voted answers, below is one of the answer saving in the file `answers_question37192562.txt`:

   ```text
   artifacts/retrieval/task_0142/ra_method/answers_question37192562.txt
   OkHttpClient client = new OkHttpClient();
   Request request = new Request.Builder().url(builder.build()).get().build();
   try (Response response = client.newCall(request).execute()) {
       JSONObject root = new JSONObject(response.body().string());
       JSONArray arr = root.getJSONArray("observations");
       double avg = IntStream.range(0, arr.length())
           .mapToDouble(i -> arr.getJSONObject(i).getDouble("temp_c"))
           .average()
           .orElse(Double.NaN);
       return BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP).doubleValue();
   }
   ```

3. **Stage B (translation).** The LLM receives (a) the original Python method, (b) the retrieved answer text, and (c) strategy metadata. The StackOverflow excerpts act as grounded hints for OkHttp usage, error handling, and rounding semantics, thereby engancing the translation quality. The translated code is recorded under `gen_java/task_0142/ra_method/function_requests_fetch_average_temperature.java`.

**The Prompts**

*Stage A.1(search query generation)*

```text
Analyze the following code snippet written in Python, and generate a single, concise, and well-formed question that summarizes the translation requirements of this code into Java. The question should:\n
1. Be a simple sentence.\n
2. Avoid including the original code snippet directly.\n
3. Clearly describe the key functionality or purpose of the code that needs to be translated.\n
4. Be enclosed in triple single quotes (''').\n\n
Code snippet:

import requests

class FunctionRequestsFetchAverageTemperature:
    BASE_URL = "https://api.fakeweather.dev/v1/temperature"

    def fetch_average_temperature(self, city: str, days: int) -> float:
        params = {"city": city, "days": days}
        resp = requests.get(self.BASE_URL, params=params, timeout=5)
        resp.raise_for_status()

        observations = resp.json()["observations"]
        temps = [float(row["temp_c"]) for row in observations]
        mean_value = sum(temps) / len(temps)
        return round(mean_value, 2)
```

*Stage B (translation)*

```text
Using the following StackOverflow answers as reference, 
      translate this Python code into Java:\n\n
      
=== Reference Implementation ===\n
OkHttpClient client = new OkHttpClient();
Request request = new Request.Builder().url(builder.build()).get().build();
try (Response response = client.newCall(request).execute()) {
    JSONObject root = new JSONObject(response.body().string());
    JSONArray arr = root.getJSONArray("observations");
    double avg = IntStream.range(0, arr.length())
        .mapToDouble(i -> arr.getJSONObject(i).getDouble("temp_c"))
        .average()
        .orElse(Double.NaN);
    return BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP).doubleValue();
}
=== Reference Implementation ===\n
{OTHER_REFERENCE_IMPLEMENTATIONS}

Source Code:\n
import requests

class FunctionRequestsFetchAverageTemperature:
    BASE_URL = "https://api.fakeweather.dev/v1/temperature"

    def fetch_average_temperature(self, city: str, days: int) -> float:
        params = {"city": city, "days": days}
        resp = requests.get(self.BASE_URL, params=params, timeout=5)
        resp.raise_for_status()

        observations = resp.json()["observations"]
        temps = [float(row["temp_c"]) for row in observations]
        mean_value = sum(temps) / len(temps)
        return round(mean_value, 2)
        
Target Code:\n

```

**The translated Java code via GPT-4o**    

```java
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class FunctionRequestsFetchAverageTemperature {
    public static String BASE_URL = "https://api.fakeweather.dev/v1/temperature";

    public double fetchAverageTemperature(String city, int days) throws IOException {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("city is required");
        }
        if (days <= 0) {
            return Double.NaN;
        }

        OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(5, TimeUnit.SECONDS)
            .build();
        HttpUrl url = HttpUrl.parse(BASE_URL)
            .newBuilder()
            .addQueryParameter("city", city)
            .addQueryParameter("days", Integer.toString(days))
            .build();
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Request failed: " + response.code());
            }
            JSONArray observations = new JSONObject(response.body().string()).getJSONArray("observations");
            if (observations.length() == 0) {
                return Double.NaN;
            }
            double average = IntStream.range(0, observations.length())
                .mapToDouble(i -> observations.getJSONObject(i).optDouble("temp_c", 0.0))
                .average()
                .orElse(Double.NaN);
            return BigDecimal.valueOf(average)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        }
    }
}

```


#### 2.3.2 RA(name) Strategy

RA(name) uses only the targetPLs plus method names (e.g., Java: "fetchAverageTemperature") as queries to retrieve external knowledge from StackOverflow and guide the code translation, which also consists of two stages:

1. **Stage A.1(Signature extraction).** `signature.py` walks through the source directory (Python/Java/C++ depending on the translation direction) and emits structured metadata per task. For the running example:

   ```text
   signature_out/task_0142.json
   {
       "class_name": "FunctionRequestsFetchAverageTemperature",
       "method": {
           "name": "fetchAverageTemperature",
           "return_type": "double",
           "parameters": [
               {"name": "city", "type": "String"},
               {"name": "days", "type": "int"}
           ],
           "throws": ["IOException"]
       }
   }
   ```

2. **Stage A.2(StackOverflow retrieval).** `Search.py` iterates over the extracted signatures, issues queries of the form `"<target language>: <method name>"`, and stores the resulting StackOverflow question IDs and top answers inside `function_stackoverflow_answers/<target>_function_results/*.json`

   ```text
   function_stackoverflow_answers/java_function_results/function_requests_fetch_average_temperature_function_results
   [
       {
           "function": "fetchAverageTemperature",
           "status": "success",
           "question": {
               "question_id": "37192562",
               "title": "Java OkHttp GET request returning JSON array"
           },
           "answers": [
               "OkHttpClient client = new OkHttpClient.Builder()....",
               "If you need rounding, wrap the average in BigDecimal..."
           ]
       }
   ]
   ```

3. **Stage B(translation).** The LLM receives (a) the original Python method, (b) the retrieved answer text, and (c) strategy metadata. The StackOverflow excerpts act as grounded hints for OkHttp usage, error handling, and rounding semantics, thereby engancing the translation quality. The generated Java file in this strategy again appears under `gen_java/task_0142/ra_name/function_requests_fetch_average_temperature.java`.


**The Prompts**

*Stage B(translation)*

```text
You are a world-class expert in code translation with deep mastery of translating 
Python class methods into Java implementations.\n\n
Below are the precise function signature details and either community-sourced reference 
implementations or the original C++ code as fallback. Your task is to generate clean, 
idiomatic, and fully functional Java code that exactly matches the behavior.\n\n

=== Function Signature & Metadata ===\n
{
    "class_name": "FunctionRequestsFetchAverageTemperature",
    "method": {
        "name": "fetchAverageTemperature",
        "return_type": "double",
        "parameters": [
            {"name": "city", "type": "String"},
            {"name": "days", "type": "int"}
        ],
        "throws": ["IOException"]
    }
}

=== Reference Implementation ===\n
"OkHttpClient client = new OkHttpClient.Builder()....",
            "If you need rounding, wrap the average in BigDecimal..."
... ...
=== Reference Implementation ===\n
{OTHER_REFERENCE_IMPLEMENTATIONS}

Produce only the final Java code. Do not include any explanations, comments, or extra text.
\n\nBegin Java code now:\n
```

**The translated Java code via GPT-4o**    

```java
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FunctionRequestsFetchAverageTemperature {
    public static String BASE_URL = "https://api.fakeweather.dev/v1/temperature";
    private final ObjectMapper mapper = new ObjectMapper();

    public double fetchAverageTemperature(String city, int days) throws IOException {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("city is required");
        }
        if (days <= 0) {
            return Double.NaN;
        }

        OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(5, TimeUnit.SECONDS)
            .build();
        HttpUrl url = HttpUrl.parse(BASE_URL)
            .newBuilder()
            .addQueryParameter("city", city)
            .addQueryParameter("days", Integer.toString(days))
            .build();
        Request request = new Request.Builder().url(url).get().build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP call failed: " + response.code());
            }
            JsonNode root = mapper.readTree(response.body().string());
            JsonNode observations = root.get("observations");
            if (observations == null || !observations.isArray() || observations.isEmpty()) {
                return Double.NaN;
            }
            double total = 0.0;
            int count = 0;
            for (JsonNode node : observations) {
                JsonNode tempNode = node.get("temp_c");
                if (tempNode == null) {
                    throw new IllegalArgumentException("Missing temp_c field");
                }
                total += tempNode.asDouble();
                count++;
            }
            double mean = total / count;
            return BigDecimal.valueOf(mean)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        }
    }
}

```

### 2.4 Build Execution Commands

For each strategy, TransLibEval injects the generated `function_requests_fetch_average_temperature.java`(Java code) into a Maven module and runs a shared test suite. The build command used in this example run is:

```bash
export TARGET_LANG=java
mvn -pl function_requests_fetch_average_temperature -Dtest=FunctionRequestsFetchAverageTemperatureTest test
```

A typical build log for a successful compilation and test execution contains entries of the following form:

```text
[INFO] --- maven-compiler-plugin:3.11.0:compile ---
[INFO] Compiling 1 source file to target/classes
[INFO] --- maven-surefire-plugin:3.2.5:test ---
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

Separate logs are stored per strategy, for example:

- `logs/task_0142/direct/compile.log` and `logs/task_0142/direct/test.log`
- `logs/task_0142/ir_cot/compile.log` and `logs/task_0142/ir_cot/test.log`, and so on.


## 3 Automatic Metrics (CSR, PR, CA)

TransLibEval computes three automatic metrics below per task and per strategy based on pre-defined test suite, such as section 1.4 in this running example. 

- Compilation Success Rate (CSR): for a single task in one run, CSR is 1 if compilation succeeds and 0 otherwise.
- Pass Rate (PR): the fraction of test cases that pass, i.e., `tests_passed / tests_total`.
- Computational Accuracy (CA): 1 if all test cases pass (i.e., PR = 1.0), and 0 if at least one test fails.

For this task and example run, the metrics are logged as:

| Strategy       | CSR  | PR   | CA   |
| -------------- | ---- | ---- | ---- |
| Direct         | 1    | 1.0  | 1    |
| IR(CoT)        | 1    | 1.0  | 1    |
| IR(Pseudocode) | 1    | 1.0  | 1    |
| IR(Summary)    | 1    | 0.8  | 0    |
| RA(Method)     | 1    | 0.8  | 0    |
| RA(Name)       | 1    | 1.0  | 1    |

These values are derived directly from the build and test logs. For example, for the Direct strategy, the Java code compiles (`CSR = 1`), but fails one boundary-related test case (`PR = 4/5 = 0.8`, `CA = 0`), whereas the IR(CoT) and RA-based strategies pass all five tests.

## 4 Human Evaluation

### 4.1 Evaluation Procedure

Beyond automatic metrics, TransLibEval includes a human evaluation phase that focuses on library dependency awareness. For each `(task, strategy)` pair, annotators inspect the translated Java code (and any helper classes it invokes) and verify whether it invoke any libraries and associated APIs for implementations.

The inspection proceeds as follows. Annotators first consult the library mapping for the translation to identify which target-side libraries are responsible for HTTP communication, JSON parsing, and so on. They then open the generated `function_requests_fetch_average_temperature.java` (and referenced helper classes, such as `WeatherGateway` or `RoundingUtils`) and locate the relevant constructor calls, method invocations, and imports. Finally, they assign a binary label `LDA = 1` if the necessary TPLs are correctly employed for implementation, or `LDA = 0` if the implementation either omits any libraries or misuses them in a way that breaks the logical mapping.

### 4.2 Evidence Record Inspection 

Using strategy IR(CoT) as an example, the annotator’s evidence record has the following structure:

| Checkpoint               | Evidence snippet (file and line)                             | Conclusion |
| ------------------------ | ------------------------------------------------------------ | ---------- |
| HTTP client              | `private final OkHttpClient httpClient = new OkHttpClient.Builder().callTimeout(5, TimeUnit.SECONDS).build();` | Satisfied  |
| Request construction     | Usage of `HttpUrl.parse(BASE_URL).newBuilder().addQueryParameter("city", city)...` in `fetchAverageTemperature` | Satisfied  |
| JSON parsing             | `JSONObject root = new JSONObject(response.body().string());` followed by `root.getJSONArray("observations")` | Satisfied  |
| Aggregation and rounding | Aggregation via streams and rounding via `BigDecimal.setScale(2, RoundingMode.HALF_UP)` | Satisfied  |

The corresponding LDA entry is:

```text
annotations/lda/task_0142_ir_cot.txt
strategy: ir_cot
uses_okhttp: yes
uses_org_json: yes
numeric_behavior_matches: yes
lda: 1
```

Analogous records are stored for other strategies. In this example run, IR(CoT), IR(Pseudocode), IR(Summary), RA(Method), and RA(Name) all achieve `LDA = 1`, as they either use `OkHttp` and `org.json` directly or rely on helper abstractions built on them. The Direct strategy compiles and passes most tests but is labeled with `LDA = 0` because it implements HTTP behavior using low-level JDK networking rather than the mapped TPL family.
