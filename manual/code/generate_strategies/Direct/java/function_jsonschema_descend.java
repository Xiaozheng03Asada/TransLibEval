package com.example;

import com.google.gson.*;
import java.util.Map;

public class JSONValidator {
    // 唯一的方法 validate_json 包含所有校验逻辑
    public String validate_json(String data_str, String schema_str) {
        try {
            // 解析 JSON 字符串
            JsonParser parser = new JsonParser();
            JsonElement dataElem = parser.parse(data_str);
            JsonElement schemaElem = parser.parse(schema_str);

            // 如果 data 不是 JSON 对象，则返回验证失败
            if (!dataElem.isJsonObject()) {
                return "Validation failed";
            }
            JsonObject dataObj = dataElem.getAsJsonObject();
            JsonObject schemaObj = schemaElem.getAsJsonObject();

            // 从 schema 中提取 properties 字段
            JsonObject properties = new JsonObject();
            if (schemaObj.has("properties") && schemaObj.get("properties").isJsonObject()) {
                properties = schemaObj.get("properties").getAsJsonObject();
            }

            // 遍历 data 中的每个字段
            for (Map.Entry<String, JsonElement> entry : dataObj.entrySet()) {
                String key = entry.getKey();
                JsonElement value = entry.getValue();
                if (properties.has(key)) {
                    JsonObject fieldSchema = properties.get(key).getAsJsonObject();
                    // 如果 schema 中明确指定了字段类型，则进行类型校验
                    if (fieldSchema.has("type")) {
                        String expectedType = fieldSchema.get("type").getAsString();
                        boolean typeMatches = false;
                        if ("string".equals(expectedType)) {
                            typeMatches = value.isJsonPrimitive() && value.getAsJsonPrimitive().isString();
                        } else if ("integer".equals(expectedType)) {
                            if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
                                double d = value.getAsDouble();
                                typeMatches = (d == Math.floor(d));
                            }
                        } else if ("boolean".equals(expectedType)) {
                            typeMatches = value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean();
                        } else if ("object".equals(expectedType)) {
                            typeMatches = value.isJsonObject();
                        } else if ("array".equals(expectedType)) {
                            typeMatches = value.isJsonArray();
                        } else if ("number".equals(expectedType)) {
                            typeMatches = value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber();
                        }
                        if (!typeMatches) {
                            return "Validation failed";
                        }
                    }
                    // 如果字段值为 JSON 对象，此时递归调用当前方法对嵌套数据进行校验
                    if (value.isJsonObject()) {
                        String nestedValidation = this.validate_json(value.toString(), fieldSchema.toString());
                        if (!"JSON is valid".equals(nestedValidation)) {
                            return "Validation failed";
                        }
                    }
                } else {
                    // data 中出现 schema 未定义的字段时校验失败
                    return "Validation failed";
                }
            }

            // 检查 schema 中 required 字段是否全部存在
            if (schemaObj.has("required") && schemaObj.get("required").isJsonArray()) {
                for (JsonElement reqElem : schemaObj.get("required").getAsJsonArray()) {
                    String reqField = reqElem.getAsString();
                    if (!dataObj.has(reqField)) {
                        return "Validation failed";
                    }
                }
            }
            // 通过所有校验则返回成功
            return "JSON is valid";
        } catch (Exception e) {
            return "Validation failed";
        }
    }
}