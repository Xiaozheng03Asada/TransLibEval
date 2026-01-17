package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_jsonschema_validate {
    private function_jsonschema_validate validator;

    @BeforeEach
    void setUp() {
        validator = new function_jsonschema_validate();
    }

    @Test
    void test_case_1() {
        String data = "{\"name\": \"John\", \"age\": 30}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"age\": {\"type\": \"integer\"}}, \"required\": [\"name\", \"age\"]}";
        assertEquals("True", validator.validate_json(data, schema));
    }

    @Test
    void test_case_2() {
        String data = "{\"name\": \"John\"}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"age\": {\"type\": \"integer\"}}, \"required\": [\"name\", \"age\"]}";
        assertEquals("False", validator.validate_json(data, schema));
    }

    @Test
    void test_case_3() {
        String data = "{\"name\": \"John\", \"age\": \"30\"}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"age\": {\"type\": \"integer\"}}, \"required\": [\"name\", \"age\"]}";
        assertEquals("False", validator.validate_json(data, schema));
    }

    @Test
    void test_case_4() {
        String data = "{}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"age\": {\"type\": \"integer\"}}, \"required\": [\"name\", \"age\"]}";
        assertEquals("False", validator.validate_json(data, schema));
    }

    @Test
    void test_case_5() {
        String data = "{\"name\": \"John\", \"age\": 30}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}}, \"required\": [\"name\"]}";
        assertEquals("True", validator.validate_json(data, schema));
    }
}