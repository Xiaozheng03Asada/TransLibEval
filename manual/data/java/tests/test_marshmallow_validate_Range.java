package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_marshmallow_validate_Range {

    private function_marshmallow_validate_Range validator;

    @BeforeEach
    public void setUp() {
        validator = new function_marshmallow_validate_Range();
    }

    @Test
    public void test_valid_data() {
        String user_data = "{\"age\": 25, \"score\": 85.5}";
        String result = validator.validate_user_data(user_data);
        // 检查返回字符串中包含正确的字段和值
        assertTrue(result.contains("\"age\":25"));
        assertTrue(result.contains("\"score\":85.5"));
    }

    @Test
    public void test_invalid_age_too_low_or_high() {
        String user_data = "{\"age\": -1, \"score\": 85.5}";
        String result = validator.validate_user_data(user_data);
        assertEquals("Error: invalid input", result);

        user_data = "{\"age\": 150, \"score\": 85.5}";
        result = validator.validate_user_data(user_data);
        assertEquals("Error: invalid input", result);
    }

    @Test
    public void test_invalid_score_too_low_or_high() {
        String user_data = "{\"age\": 25, \"score\": -5.0}";
        String result = validator.validate_user_data(user_data);
        assertEquals("Error: invalid input", result);

        user_data = "{\"age\": 25, \"score\": 105.0}";
        result = validator.validate_user_data(user_data);
        assertEquals("Error: invalid input", result);
    }

    @Test
    public void test_additional_unexpected_field() {
        String user_data = "{\"age\": 25, \"score\": 85.0, \"name\": \"John Doe\"}";
        String result = validator.validate_user_data(user_data);
        assertEquals("Error: invalid input", result);
    }

    @Test
    public void test_both_fields_missing() {
        String user_data = "{}";
        String result = validator.validate_user_data(user_data);
        assertEquals("Error: invalid input", result);
    }
}