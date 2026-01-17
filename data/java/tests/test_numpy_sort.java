package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_numpy_sort {

    @Test
    public void test_valid_input() {
        function_numpy_sort calc_sort = new function_numpy_sort();
        String result = calc_sort.sort(10.0, 5.0, 15.0);
        assertTrue(result.contains("Sorted Values: 5"));
        assertTrue(result.contains("10"));
        assertTrue(result.contains("15"));
    }

    @Test
    public void test_empty_input() {
        function_numpy_sort calc_sort = new function_numpy_sort();
        String result = calc_sort.sort(null, null, null);
        assertTrue(result.contains("Sorted Values: 5"));
        assertTrue(result.contains("10"));
        assertTrue(result.contains("15"));
    }

    @Test
    public void test_single_value_input() {
        function_numpy_sort calc_sort = new function_numpy_sort();
        String result = calc_sort.sort(100.0, 100.0, 100.0);
        assertTrue(result.contains("Sorted Values: 100"));
    }

    @Test
    public void test_large_numbers() {
        function_numpy_sort calc_sort = new function_numpy_sort();
        String result = calc_sort.sort(1e10, 1e12, 1e11);
        assertTrue(result.contains("Sorted Values: 10000000000"));
        assertTrue(result.contains("1000000000000"));
        assertTrue(result.contains("100000000000"));
    }

    @Test
    public void test_mixed_sign_values() {
        function_numpy_sort calc_sort = new function_numpy_sort();
        String result = calc_sort.sort(-10.0, 0.0, 5.0);
        assertTrue(result.contains("Sorted Values: -10"));
        assertTrue(result.contains("0"));
        assertTrue(result.contains("5"));
    }
}