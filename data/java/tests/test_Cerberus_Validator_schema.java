package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class test_Cerberus_Validator_schema {

    private function_Cerberus_Validator_schema validator;

    @BeforeEach
    void setUp() {
        validator = new function_Cerberus_Validator_schema();
    }

    @Test
    void testValidData() {
        String dataStr = "{'name': 'John', 'age': 25}";
        String result = validator.validate_data(dataStr);
        assertEquals("Valid data: {name={type=string, minlength=3}, age={type=integer, min=18, max=100}}", result);
    }

    @Test
    void testInvalidNameLength() {
        String dataStr = "{'name': 'Jo', 'age': 25}";
        String result = validator.validate_data(dataStr);
        assertEquals("Error: Invalid data - {name=[min length is 3]}", result);
    }

    @Test
    void testInvalidAge() {
        String dataStr = "{'name': 'John', 'age': 17}";
        String result = validator.validate_data(dataStr);
        assertEquals("Error: Invalid data - {age=[min value is 18]}", result);
    }

    @Test
    void testNonStringInput() {
        String dataStr = "{'name': 123, 'age': 25}";
        String result = validator.validate_data(dataStr);
        assertEquals("Error: Invalid data - {name=[must be of string type]}", result);
    }

    @Test
    void testInvalidFormat() {
        String dataStr = "{'name': 'Alice', 'age': 'invalid_age'}";
        String result = validator.validate_data(dataStr);
        assertTrue(result.contains("Error: Invalid data"));
    }
}