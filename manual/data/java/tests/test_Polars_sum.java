package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_Polars_sum {
    @Test
    void testComputeSumMultiplePositiveNumbers() {
        function_Polars_sum calc = new function_Polars_sum();
        double result = calc.compute_sum(1.0, 2.0, 3.0);
        assertEquals(6.0, result);
    }

    @Test
    void testComputeSumMultipleNegativeNumbers() {
        function_Polars_sum calc = new function_Polars_sum();
        double result = calc.compute_sum(-1.0, -2.0, -3.0);
        assertEquals(-6.0, result);
    }

    @Test
    void testComputeSumMixedNumbers() {
        function_Polars_sum calc = new function_Polars_sum();
        double result = calc.compute_sum(1.0, -1.0, 2.0);
        assertEquals(2.0, result);
    }

    @Test
    void testComputeSumSingleNumber() {
        function_Polars_sum calc = new function_Polars_sum();
        double result = calc.compute_sum(10.0, null, null);
        assertEquals(10.0, result);
    }

    @Test
    void testComputeSumNoInput() {
        function_Polars_sum calc = new function_Polars_sum();
        double result = calc.compute_sum(10.0, null, null);
        assertEquals(10.0, result);
    }
}