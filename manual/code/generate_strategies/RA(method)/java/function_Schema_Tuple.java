package com.example;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;

public class ValidateTuple {
    public String validate(String input_str) {
        try {
            String[] parts = input_str.split(",");
            if (parts.length != 2) {
                return "Invalid input: Must be a pair of values";
            }

            // 创建JSON Schema来验证输入
            JSONObject jsonSchema = new JSONObject()
                    .put("type", "object")
                    .put("properties", new JSONObject()
                            .put("first", new JSONObject()
                                    .put("type", "integer"))
                            .put("second", new JSONObject()
                                    .put("type", "string")))
                    .put("required", new JSONArray()
                            .put("first")
                            .put("second"));

            // 创建要验证的数据
            JSONObject jsonData = new JSONObject();
            try {
                jsonData.put("first", Integer.parseInt(parts[0].trim()));
            } catch (NumberFormatException e) {
                return "Invalid input: First value must be an integer";
            }
            jsonData.put("second", parts[1].trim());

            // 使用Schema验证
            Schema schema = SchemaLoader.load(jsonSchema);
            schema.validate(jsonData);

            // 如果验证通过，返回格式化的结果
            return String.format("Valid tuple: (%s, %s)",
                    jsonData.get("first"),
                    jsonData.get("second"));

        } catch (ValidationException e) {
            return "Invalid input: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}