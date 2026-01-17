package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserSchema {

    // 该方法对应 Python 函数 serialize_user_data，逐行翻译实现如下：
    public String serialize_user_data(String user_data) {
        // 使用 try-catch 模拟 Python 中的 try/except 块
        try {
            // 使用 Jackson 的 ObjectMapper 进行 JSON 解析和序列化，相当于 Python 中的 json.loads 和 json.dumps
            ObjectMapper mapper = new ObjectMapper();

            // 将输入的 JSON 字符串解析为 Map，类似于 Python 中的 json.loads(user_data)
            Map<String, Object> inputMap = mapper.readValue(user_data, new TypeReference<Map<String, Object>>() {});

            // 模拟 marshmallow 的 required=True 校验，判断必须包含 age 和 score 字段
            if (!inputMap.containsKey("age") || !inputMap.containsKey("score")) {
                throw new Exception("Missing required fields");
            }

            // 提取 age 字段，并转换为 int（Python 中定义为 fields.Int(required=True)）
            Object ageObj = inputMap.get("age");
            int age;
            if (ageObj instanceof Number) {
                age = ((Number) ageObj).intValue();
            } else {
                throw new Exception("Invalid type for age");
            }

            // 提取 score 字段，并转换为 double（对应 Python 中的 float 类型）
            Object scoreObj = inputMap.get("score");
            double score;
            if (scoreObj instanceof Number) {
                score = ((Number) scoreObj).doubleValue();
            } else {
                throw new Exception("Invalid type for score");
            }

            // 模拟 post_dump 功能：将输出包装为 {"result": data}
            Map<String, Object> dataMap = new LinkedHashMap<>();
            dataMap.put("age", age);
            dataMap.put("score", score);
            Map<String, Object> finalResult = new LinkedHashMap<>();
            finalResult.put("result", dataMap);

            // 将结果 Map 序列化为 JSON 字符串，并返回
            return mapper.writeValueAsString(finalResult);
        } catch (Exception e) {
            // 捕获所有异常（包括 JSON 解析错误和校验失败），返回错误提示
            return "Error: invalid input";
        }
    }
}