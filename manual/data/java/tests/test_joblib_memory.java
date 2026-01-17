package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_joblib_memory {

    @Test
    void test_compute_square() {
        function_joblib_memory calc = new function_joblib_memory();
        int result = calc.compute_square(4);
        assertEquals(16, result);
    }

    @Test
    void test_compute_square_another() {
        function_joblib_memory calc = new function_joblib_memory();
        int result = calc.compute_square(5);
        assertEquals(25, result);
    }

    @Test
    void test_compute_square_cache() {
        function_joblib_memory calc = new function_joblib_memory();
        int result1 = calc.compute_square(4);
        int result2 = calc.compute_square(4);
        assertEquals(result1, result2);
    }

    @Test
    void test_compute_square_zero() {
        function_joblib_memory calc = new function_joblib_memory();
        int result = calc.compute_square(0);
        assertEquals(0, result);
    }

    @Test
    void test_non_integer_input() {
        assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt("string");
        });
    }
}