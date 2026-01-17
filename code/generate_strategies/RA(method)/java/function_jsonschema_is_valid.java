package com.example;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;

public class JSONValidator {

    // 按行翻译实现 validate_json 方法
    public String validate_json(String data_str, String schema_str) {
        try {
            // 解析data_str为JSONObject（对应python的 json.loads(data_str)）
            JSONObject data = new JSONObject(data_str);
            // 解析schema_str为JSONObject（对应python的 json.loads(schema_str)）
            JSONObject schemaJson = new JSONObject(schema_str);
            // 使用 SchemaLoader 加载 schema（对应python的 Draft7Validator(schema)）
            Schema schema = SchemaLoader.load(schemaJson);
            // 校验data数据，如果不符合则会抛出异常（对应python的 validator.is_valid(data)）
            schema.validate(data);
            // 如果校验通过，返回 "JSON is valid"
            return "JSON is valid";
        } catch (Exception e) {
            // 若有异常或校验不通过，返回 "JSON is invalid"
            return "JSON is invalid";
        }
    }
}