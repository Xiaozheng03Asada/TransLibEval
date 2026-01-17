package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_Polars_max {

    @Test
    public void test_compute_max_multiple_positive_numbers() {
        function_Polars_max calc = new function_Polars_max();
        float result = calc.compute_max(1, 2f, 3f);
        assertEquals(3.0f, result, 0.001);
    }

    @Test
    public void test_compute_max_multiple_negative_numbers() {
        function_Polars_max calc = new function_Polars_max();
        float result = calc.compute_max(-1, -2f, -3f);
        assertEquals(-1.0f, result, 0.001);
    }

    @Test
    public void test_compute_max_mixed_numbers() {
        function_Polars_max calc = new function_Polars_max();
        float result = calc.compute_max(1, -1f, 2f);
        assertEquals(2.0f, result, 0.001);
    }

    @Test
    public void test_compute_max_single_number() {
        function_Polars_max calc = new function_Polars_max();
        float result = calc.compute_max(10, null, null);
        assertEquals(10.0f, result, 0.001);
    }

    @Test
    public void test_compute_max_no_input() {
        function_Polars_max calc = new function_Polars_max();
        float result = calc.compute_max(10, null, null);
        assertEquals(10.0f, result, 0.001);
    }
}