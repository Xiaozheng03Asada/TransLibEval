package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_Pydantic_basemodel {

    @Test
    void test_valid_user() {
        function_Pydantic_basemodel validator = new function_Pydantic_basemodel();
        String result = validator.create_user("John Doe", 30, "john@example.com");
        assertEquals("name='John Doe' age=30 email='john@example.com'", result);
    }

    @Test
    void test_invalid_age() {
        function_Pydantic_basemodel validator = new function_Pydantic_basemodel();
        String result = validator.create_user("John Doe", -1, "john@example.com");
        assertTrue(result.contains("age"));
    }

    @Test
    void test_invalid_email() {
        function_Pydantic_basemodel validator = new function_Pydantic_basemodel();
        String result = validator.create_user("John Doe", 30, "invalid-email");
        assertTrue(result.contains("email"));
    }

    @Test
    void test_missing_field() {
        function_Pydantic_basemodel validator = new function_Pydantic_basemodel();
        String result = validator.create_user("John Doe", 30, "");
        assertTrue(result.contains("email"));
    }

    @Test
    void test_invalid_email_type() {
        function_Pydantic_basemodel validator = new function_Pydantic_basemodel();
        String result = validator.create_user("John Doe", 30, "12345");
        assertNotNull(result);
        assertTrue(result.contains("email"));
    }
}