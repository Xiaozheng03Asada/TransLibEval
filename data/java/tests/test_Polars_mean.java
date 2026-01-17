package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_Polars_mean {

    @Test
    public void test_compute_mean_multiple_positive_numbers() {
        function_Polars_mean calc = new function_Polars_mean();
        Double result = calc.compute_mean(1f, 2f, 3f);
        assertEquals(2.0, result, 0.0001);
    }

    @Test
    public void test_compute_mean_multiple_negative_numbers() {
        function_Polars_mean calc = new function_Polars_mean();
        Double result = calc.compute_mean(-1f, -2f, -3f);
        assertEquals(-2.0, result, 0.0001);
    }

    @Test
    public void test_compute_mean_mixed_numbers() {
        function_Polars_mean calc = new function_Polars_mean();
        Double result = calc.compute_mean(1f, -1f, 2f);
        assertEquals(0.6666666666666666, result, 0.0001);
    }

    @Test
    public void test_compute_mean_single_number() {
        function_Polars_mean calc = new function_Polars_mean();
        Double result = calc.compute_mean(10f, null, null);
        assertEquals(10.0, result, 0.0001);
    }

    @Test
    public void test_compute_mean_no_input() {
        function_Polars_mean calc = new function_Polars_mean();
        Double result = calc.compute_mean(10f, null, null);
        assertEquals(10.0, result, 0.0001);
    }
}