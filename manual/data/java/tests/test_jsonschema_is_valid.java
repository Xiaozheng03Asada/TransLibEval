package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_jsonschema_is_valid {

    private String result;
    private function_jsonschema_is_valid validator;

    @BeforeEach
    public void setUp() {
        // 实例化 function_jsonschema_is_valid 对象
        validator = new function_jsonschema_is_valid();
    }

    @Test
    public void test_case_1() {
        // 测试数据1：完整数据符合 schema
        String data = "{\"name\": \"John\", \"age\": 30}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"age\": {\"type\": \"integer\"}}, \"required\": [\"name\", \"age\"]}";
        result = validator.validate_json(data, schema);
        assertEquals("JSON is valid", result);
    }

    @Test
    public void test_case_2() {
        // 测试数据2：缺少必填字段 age
        String data = "{\"name\": \"John\"}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"age\": {\"type\": \"integer\"}}, \"required\": [\"name\", \"age\"]}";
        result = validator.validate_json(data, schema);
        assertEquals("JSON is invalid", result);
    }

    @Test
    public void test_case_3() {
        // 测试数据3：age 类型错误（字符串而非整数）
        String data = "{\"name\": \"John\", \"age\": \"30\"}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"age\": {\"type\": \"integer\"}}, \"required\": [\"name\", \"age\"]}";
        result = validator.validate_json(data, schema);
        assertEquals("JSON is invalid", result);
    }

    @Test
    public void test_case_4() {
        // 测试数据4：空数据
        String data = "{}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"age\": {\"type\": \"integer\"}}, \"required\": [\"name\", \"age\"]}";
        result = validator.validate_json(data, schema);
        assertEquals("JSON is invalid", result);
    }

    @Test
    public void test_case_5() {
        // 测试数据5：数据中只包含必填字段 name 的 schema
        String data = "{\"name\": \"John\", \"age\": 30}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}}, \"required\": [\"name\"]}";
        result = validator.validate_json(data, schema);
        assertEquals("JSON is valid", result);
    }
}