package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_numpy_where {
    @Test
    void test_check_number_positive() {
        function_numpy_where calc = new function_numpy_where();
        String result = calc.check_number(5);
        assertEquals("positive", result);
    }

    @Test
    void test_check_number_negative() {
        function_numpy_where calc = new function_numpy_where();
        String result = calc.check_number(-3);
        assertEquals("negative", result);
    }

    @Test
    void test_check_number_zero() {
        function_numpy_where calc = new function_numpy_where();
        String result = calc.check_number(0);
        assertEquals("zero", result);
    }

    @Test
    void test_check_number_large_positive() {
        function_numpy_where calc = new function_numpy_where();
        String result = calc.check_number(100);
        assertEquals("positive", result);
    }

    @Test
    void test_check_number_large_negative() {
        function_numpy_where calc = new function_numpy_where();
        String result = calc.check_number(-100);
        assertEquals("negative", result);
    }
}