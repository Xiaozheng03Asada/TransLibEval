package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_jsonschema_iter_errors {
    private function_jsonschema_iter_errors validator;

    @BeforeEach
    void setUp() {
        validator = new function_jsonschema_iter_errors();
    }

    @Test
    void test_case_1() {
        String data = "{\"name\": \"John\", \"age\": 30}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"age\": {\"type\": \"integer\"}}, \"required\": [\"name\", \"age\"]}";
        assertEquals("JSON is valid", validator.validate_json(data, schema));
    }

    @Test
    void test_case_2() {
        String data = "{\"name\": \"John\"}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"age\": {\"type\": \"integer\"}}, \"required\": [\"name\", \"age\"]}";
        assertEquals("Validation failed: $.age: is missing but it is required", validator.validate_json(data, schema));
    }

    @Test
    void test_case_3() {
        String data = "{\"name\": \"John\", \"age\": \"30\"}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"age\": {\"type\": \"integer\"}}, \"required\": [\"name\", \"age\"]}";
        assertEquals("Validation failed: '30' is not of type 'integer'", validator.validate_json(data, schema));
    }

    @Test
    void test_case_4() {
        String data = "{}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"age\": {\"type\": \"integer\"}}, \"required\": [\"name\", \"age\"]}";
        assertEquals("Validation failed: $.name: is missing but it is required", validator.validate_json(data, schema));
    }

    @Test
    void test_case_5() {
        String data = "{\"name\": \"John\", \"age\": 30}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}}, \"required\": [\"name\"]}";
        assertEquals("JSON is valid", validator.validate_json(data, schema));
    }
}