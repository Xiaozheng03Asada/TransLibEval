package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_Schema_is_valid {
    private function_Schema_is_valid validator;

    @BeforeEach
    void setUp() {
        validator = new function_Schema_is_valid();
    }

    @Test
    void test_valid_data() {
        String result = validator.validate_user("Alice", "25", "alice@example.com");
        assertEquals("Valid data", result);
    }

    @Test
    void test_missing_optional_field() {
        String result = validator.validate_user("Bob", "30", null);
        assertEquals("Valid data", result);
    }

    @Test
    void test_invalid_email_format() {
        String result = validator.validate_user("Diana", "22", "invalid_email");
        assertEquals("Invalid data", result);
    }

    @Test
    void test_additional_unexpected_field() {
        String result = validator.validate_user("Eve", "30", "extra_field");
        assertEquals("Invalid data", result);
    }

    @Test
    void test_null_email() {
        String result = validator.validate_user("Frank", "40", null);
        assertEquals("Valid data", result);
    }
}