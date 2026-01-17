package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class test_boltons_iterutils_flatten {

    private function_boltons_iterutils_flatten function;

    @BeforeEach
    void setUp() {
        this.function = new function_boltons_iterutils_flatten();
    }

    @Test
    void test_flatten_data_valid_input() {
        String dataStr = "1,2,3;4,5,6;7,8,9";
        String result = function.flatten_data(dataStr);
        String expected = "1,2,3,4,5,6,7,8,9";
        assertEquals(expected, result);
    }

    @Test
    void test_flatten_data_empty_string() {
        String dataStr = "";
        String result = function.flatten_data(dataStr);
        String expected = "Error";
        assertEquals(expected, result);
    }

    @Test
    void test_flatten_data_invalid_input() {
        String dataStr = null;  // Invalid input type (not a string)
        String result = function.flatten_data(dataStr); // Convert to string for testing
        String expected = "Error";
        assertEquals(expected, result);
    }

    @Test
    void test_flatten_data_single_element() {
        String dataStr = "1";
        String result = function.flatten_data(dataStr);
        String expected = "1";
        assertEquals(expected, result);
    }

    @Test
    void test_flatten_data_mixed_structure() {
        String dataStr = "1,2,3;4,5;6,7,8";
        String result = function.flatten_data(dataStr);
        String expected = "1,2,3,4,5,6,7,8";
        assertEquals(expected, result);
    }
}