package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_Schema_validate {

    @Test
    void test_valid_data() {
        function_Schema_validate validator = new function_Schema_validate();
        String result = validator.validate("Alice", "30", "New York");
        assertEquals("Valid data: name = Alice, age = 30, city = New York", result);
    }

    @Test
    void test_invalid_age() {
        function_Schema_validate validator = new function_Schema_validate();
        String result = validator.validate("Alice", "15", "New York");
        assertEquals("Invalid data: One or more fields are incorrect", result);
    }

    @Test
    void test_invalid_city() {
        function_Schema_validate validator = new function_Schema_validate();
        String result = validator.validate("Bob", "25", "12345");
        assertEquals("Invalid data: One or more fields are incorrect", result);
    }

    @Test
    void test_missing_city() {
        function_Schema_validate validator = new function_Schema_validate();
        String result = validator.validate("Charlie", "28", "");
        assertEquals("Invalid data: One or more fields are incorrect", result);
    }

    @Test
    void test_invalid_name() {
        function_Schema_validate validator = new function_Schema_validate();
        String result = validator.validate("", "45", "London");
        assertEquals("Invalid data: One or more fields are incorrect", result);
    }
}