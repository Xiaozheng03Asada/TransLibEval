package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class test_jsonschema_descend {
    @Test
    public void test_case_1() {
        function_jsonschema_descend validator = new function_jsonschema_descend();
        String data = "{\"name\": \"John\", \"age\": 30}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"age\": {\"type\": \"integer\"}}, \"required\": [\"name\", \"age\"]}";
        assertEquals("JSON is valid", validator.validate_json(data, schema));
    }

    @Test
    public void test_case_2() {
        function_jsonschema_descend validator = new function_jsonschema_descend();
        String data = "{\"name\": \"John\"}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"age\": {\"type\": \"integer\"}}, \"required\": [\"name\", \"age\"]}";
        assertEquals("Validation failed", validator.validate_json(data, schema));
    }

    @Test
    public void test_case_3() {
        function_jsonschema_descend validator = new function_jsonschema_descend();
        String data = "{\"name\": \"John\", \"age\": \"30\"}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"age\": {\"type\": \"integer\"}}, \"required\": [\"name\", \"age\"]}";
        assertEquals("Validation failed", validator.validate_json(data, schema));
    }

    @Test
    public void test_case_4() {
        function_jsonschema_descend validator = new function_jsonschema_descend();
        String data = "{\"name\": \"John\"}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}}, \"required\": [\"name\"]}";
        assertEquals("JSON is valid", validator.validate_json(data, schema));
    }

    @Test
    public void test_case_5() {
        function_jsonschema_descend validator = new function_jsonschema_descend();
        String data = "{\"name\": \"John\", \"address\": {\"street\": \"123 Main St\", \"city\": \"New York\"}}";
        String schema = "{\"type\": \"object\", \"properties\": {\"name\": {\"type\": \"string\"}, \"address\": {\"type\": \"object\", \"properties\": {\"street\": {\"type\": \"string\"}, \"city\": {\"type\": \"string\"}}, \"required\": [\"street\", \"city\"]}},\"required\": [\"name\", \"address\"]}";
        assertEquals("JSON is valid", validator.validate_json(data, schema));
    }
}