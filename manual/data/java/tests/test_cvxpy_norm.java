package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_cvxpy_norm {

    @Test
    void test_norm_type() {
        String vector = "[1, 2, 3]";
        String normValue = new function_cvxpy_norm().compute_norm(vector, 2);
        assertNotNull(normValue);
        assertTrue(normValue instanceof String);
    }

    @Test
    void test_norm_computation_2_norm() {
        String vector = "[3, 4, 0]";
        String normValue = new function_cvxpy_norm().compute_norm(vector, 2);
        assertEquals("5.0", normValue);
    }

    @Test
    void test_norm_computation_1_norm() {
        String vector = "[1, -2, 3]";
        String normValue = new function_cvxpy_norm().compute_norm(vector, 1);
        assertEquals("6.0", normValue);
    }

    @Test
    void test_norm_computation_inf_norm() {
        String vector = "[-1, 2, -3, 4]";
        String normValue = new function_cvxpy_norm().compute_norm(vector, Integer.MAX_VALUE);
        assertEquals("4.0", normValue);
    }

    @Test
    void test_norm_zero_vector() {
        String vector = "[0, 0, 0, 0, 0]";
        String normValue = new function_cvxpy_norm().compute_norm(vector, 2);
        assertEquals("0.0", normValue);
    }
}