package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_Cerberus_Validator_validate {
    private function_Cerberus_Validator_validate comparer;

    @BeforeEach
    void setUp() {
        comparer = new function_Cerberus_Validator_validate();
    }

    @Test
    void test_greater_numbers() {
        String result = comparer.compare_numbers(10.0, 5.0);
        assertEquals("Greater", result);
    }

    @Test
    void test_smaller_numbers() {
        String result = comparer.compare_numbers(5.0, 10.0);
        assertEquals("Smaller", result);
    }

    @Test
    void test_equal_numbers() {
        String result = comparer.compare_numbers(10.0, 10.0);
        assertEquals("Equal", result);
    }

    @Test
    void test_non_numeric_input() {
        String result = comparer.compare_numbers(null, 5.0);
        assertEquals("Error: Invalid input. {'num1': ['must be of number type']}", result);
    }

    @Test
    void test_non_numeric_input2() {
        String result = comparer.compare_numbers(10.0, null);
        assertEquals("Error: Invalid input. {'num2': ['must be of number type']}", result);
    }
}