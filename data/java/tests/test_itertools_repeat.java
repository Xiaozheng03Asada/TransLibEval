package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_itertools_repeat {

    @Test
    void test_repeat_integer() {
        function_itertools_repeat calc = new function_itertools_repeat();
        String result = calc.repeat_element("5", 3);
        assertEquals("5, 5, 5", result);
    }

    @Test
    void test_repeat_string() {
        function_itertools_repeat calc = new function_itertools_repeat();
        String result = calc.repeat_element("apple", 2);
        assertEquals("apple, apple", result);
    }

    @Test
    void test_repeat_zero_times() {
        function_itertools_repeat calc = new function_itertools_repeat();
        String result = calc.repeat_element("10", 0);
        assertEquals("", result);
    }

    @Test
    void test_non_numeric() {
        function_itertools_repeat calc = new function_itertools_repeat();
        String result = calc.repeat_element("banana", 4);
        assertTrue(result instanceof String);
    }

    @Test
    void test_invalid_input() {
        function_itertools_repeat calc = new function_itertools_repeat();
        assertThrows(IllegalArgumentException.class, () ->
                calc.repeat_element("apple", -1)
        );
    }
}