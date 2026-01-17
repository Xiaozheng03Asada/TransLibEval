package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_functools_partial {

    @Test
    void test_apply_partial_function() {
        function_functools_partial calc = new function_functools_partial();
        int result = calc.apply_partial_function(5, 10);
        assertEquals(15, result);
    }

    @Test
    void test_apply_partial_function_with_negative() {
        function_functools_partial calc = new function_functools_partial();
        int result = calc.apply_partial_function(-3, 7);
        assertEquals(4, result);
    }

    @Test
    void test_apply_partial_function_zero() {
        function_functools_partial calc = new function_functools_partial();
        int result = calc.apply_partial_function(0, 5);
        assertEquals(5, result);
    }

    @Test
    void test_non_numeric_input() {
        function_functools_partial calc = new function_functools_partial();
        assertThrows(IllegalArgumentException.class, () -> {
            calc.apply_partial_function(Integer.valueOf((String)"five"), 10);
        });
    }

    @Test
    void test_edge_case() {
        function_functools_partial calc = new function_functools_partial();
        int result = calc.apply_partial_function(0, 0);
        assertEquals(0, result);
    }
}