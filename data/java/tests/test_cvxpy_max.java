package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_cvxpy_max {

    @Test
    public void test_max_type() {
        String vector = "[1, 2, 3, 4]";
        String maxValue = new function_cvxpy_max().compute_max_value(vector);
        assertNotNull(maxValue);
        assertTrue(maxValue instanceof String);
    }

    @Test
    public void test_max_mixed_values() {
        String vector = "[-10, 0, 5, -3, 9, -1]";
        String maxValue = new function_cvxpy_max().compute_max_value(vector);
        assertEquals("9.0", maxValue);
    }

    @Test
    public void test_max_single_value() {
        String vector = "[42]";
        String maxValue = new function_cvxpy_max().compute_max_value(vector);
        assertEquals("42.0", maxValue);
    }

    @Test
    public void test_max_with_floats() {
        String vector = "[1.5, 2.75, 0.5, 2.74]";
        String maxValue = new function_cvxpy_max().compute_max_value(vector);
        assertEquals("2.75", maxValue);
    }

    @Test
    public void test_input_with_nan() {
        String vector = "[1, NaN, 2, 3]";
        assertThrows(IllegalArgumentException.class, () -> {
            new function_cvxpy_max().compute_max_value(vector);
        });
    }
}