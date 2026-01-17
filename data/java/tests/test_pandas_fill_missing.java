package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_pandas_fill_missing {

    @Test
    public void test_success() {
        function_pandas_fill_missing fill_missing = new function_pandas_fill_missing();
        String result = fill_missing.fill_missing_values(1.0, null);
        assertTrue(result.startsWith("A: 1"));
    }

    @Test
    public void test_filled_values() {
        function_pandas_fill_missing fill_missing = new function_pandas_fill_missing();
        String result = fill_missing.fill_missing_values(null, null);
        assertEquals("A: nan, B: nan", result);
    }

    @Test
    public void test_result_format() {
        function_pandas_fill_missing fill_missing = new function_pandas_fill_missing();
        String result = fill_missing.fill_missing_values(5.0, 20.0);
        assertInstanceOf(String.class, result);
    }

    @Test
    public void test_column_names() {
        function_pandas_fill_missing fill_missing = new function_pandas_fill_missing();
        String result = fill_missing.fill_missing_values(7.0, 14.0);
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
    }

    @Test
    public void test_partial_filled_values() {
        function_pandas_fill_missing fill_missing = new function_pandas_fill_missing();
        String result = fill_missing.fill_missing_values(null, 50.0);
        assertEquals("A: nan, B: 50.0", result);
    }
}