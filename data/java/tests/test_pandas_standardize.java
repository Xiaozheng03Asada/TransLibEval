package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_pandas_standardize {

    @Test
    public void test_success() {
        function_pandas_standardize calc_standardize = new function_pandas_standardize();
        String result = calc_standardize.standardize(1.0, 2.0);
        assertTrue(result.startsWith("A:"));
    }

    @Test
    public void test_result_format() {
        function_pandas_standardize calc_standardize = new function_pandas_standardize();
        String result = calc_standardize.standardize(1.0, 2.0);
        assertInstanceOf(String.class, result);
    }

    @Test
    public void test_empty_data() {
        function_pandas_standardize calc_standardize = new function_pandas_standardize();
        String result = calc_standardize.standardize(null, null);
        assertTrue(result.contains("A: None"));
        assertTrue(result.contains("B: None"));
    }

    @Test
    public void test_column_consistency() {
        function_pandas_standardize calc_standardize = new function_pandas_standardize();
        String result = calc_standardize.standardize(1.0, 2.0);
        assertTrue(result.contains("A:"));
        assertTrue(result.contains("B:"));
    }

    @Test
    public void test_same_value_input() {
        function_pandas_standardize calc_standardize = new function_pandas_standardize();
        String result = calc_standardize.standardize(5.0, 5.0);
        assertTrue(result.contains("A: 0"));
        assertTrue(result.contains("B: 0"));
    }
}