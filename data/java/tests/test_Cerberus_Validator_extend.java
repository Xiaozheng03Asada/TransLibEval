package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_Cerberus_Validator_extend {
    private String schema_str;
    private function_Cerberus_Validator_extend validator;

    @BeforeEach
    void setUp() {
        schema_str = "{'age': {'type': 'integer', 'min': 18, 'max': 100}, 'name': {'type': 'string', 'maxlength': 50}}";
        validator = new function_Cerberus_Validator_extend();
    }

    @Test
    void test_valid_data() {
        String data_str = "{\"age\": 25, \"name\": \"John Doe\"}";
        String result = validator.validate_data(data_str, schema_str);
        assertEquals("Valid data", result);
    }

    @Test
    void test_invalid_age() {
        String data_str = "{\"age\": 17, \"name\": \"John Doe\"}";
        String result = validator.validate_data(data_str, schema_str);
        assertEquals("Invalid data: {\"age\":[\"min value is 18\"]}", result);
    }

    @Test
    void test_invalid_name_length() {
        String data_str = "{\"age\": 30, \"name\": \"" + "a".repeat(51) + "\"}";
        String result = validator.validate_data(data_str, schema_str);
        assertEquals("Invalid data: {\"name\":[\"max length is 50\"]}", result);
    }

    @Test
    void test_invalid_name_type() {
        String data_str = "{\"age\": 25, \"name\": 12345}";
        String result = validator.validate_data(data_str, schema_str);
        assertEquals("Invalid data: {\"name\":[\"must be of string type\"]}", result);
    }

    @Test
    void test_multiple_errors() {
        String data_str = "{\"age\": 17, \"name\": \"" + "a".repeat(51) + "\"}";
        String result = validator.validate_data(data_str, schema_str);
        assertEquals("Invalid data: {\"age\":[\"min value is 18\"],\"name\":[\"max length is 50\"]}", result);
    }
}