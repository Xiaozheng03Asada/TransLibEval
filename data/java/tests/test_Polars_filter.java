package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_Polars_filter {

    @Test
    public void test_filter_numbers_greater_than_threshold() {
        function_Polars_filter calc = new function_Polars_filter();
        Float result = calc.filter_numbers(2, 1, 2f, 3f);
        assertEquals(3.0f, result);
    }

    @Test
    public void test_filter_numbers_multiple_values() {
        function_Polars_filter calc = new function_Polars_filter();
        Float result = calc.filter_numbers(1, 5, 2f, 7f);
        assertEquals(7.0f, result);
    }

    @Test
    public void test_filter_numbers_empty_result() {
        function_Polars_filter calc = new function_Polars_filter();
        Float result = calc.filter_numbers(10, 1, 2f, 3f);
        assertNull(result);
    }

    @Test
    public void test_filter_numbers_single_value_greater_than_threshold() {
        function_Polars_filter calc = new function_Polars_filter();
        Float result = calc.filter_numbers(0, 1, null, null);
        assertEquals(1.0f, result);
    }

    @Test
    public void test_filter_numbers_no_values_above_threshold() {
        function_Polars_filter calc = new function_Polars_filter();
        Float result = calc.filter_numbers(100, 10, 20f, 30f);
        assertNull(result);
    }
}