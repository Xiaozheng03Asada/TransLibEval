package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_Schema_And {
    private function_Schema_And validator;

    @BeforeEach
    void setUp() {
        validator = new function_Schema_And();
    }

    @Test
    void testValidData() {
        String result = validator.validate_user_input("admin", "securepassword123");
        assertEquals("Validation passed: username = admin, password = securepassword123", result);
    }

    @Test
    void testMissingUsername() {
        String result = validator.validate_user_input("", "securepassword123");
        assertEquals("Validation failed", result);
    }

    @Test
    void testShortPassword() {
        String result = validator.validate_user_input("admin", "short");
        assertEquals("Validation failed", result);
    }

    @Test
    void testNonStringUsername() {
        String result = validator.validate_user_input(null, "securepassword123");
        assertEquals("Validation failed", result);
    }

    @Test
    void testValidDataNoErrors() {
        String result = validator.validate_user_input("admin", "securepass123");
        assertEquals("Validation passed: username = admin, password = securepass123", result);
    }
}