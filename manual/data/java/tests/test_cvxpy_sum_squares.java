package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_cvxpy_sum_squares {
    private function_cvxpy_sum_squares solver = new function_cvxpy_sum_squares();

    @Test
    public void test_sum_of_squares_computation() {
        String vector_values_str = "1,-2,3";
        String result = solver.solve_sum_of_squares(vector_values_str);
        assertEquals(14.0, Double.parseDouble(result), 1e-10);
    }

    @Test
    public void test_floating_point_precision() {
        String vector_values_str = "1.0000000001,2.0000000002,3.0000000003";
        String result = solver.solve_sum_of_squares(vector_values_str);
        double expected = Math.pow(1.0000000001, 2) + Math.pow(2.0000000002, 2) + Math.pow(3.0000000003, 2);
        assertEquals(expected, Double.parseDouble(result), 1e-10);
    }

    @Test
    public void test_single_element_vector() {
        String vector_values_str = "5";
        String result = solver.solve_sum_of_squares(vector_values_str);
        assertEquals("25.0", result);
    }

    @Test
    public void test_small_values_in_vector() {
        String vector_values_str = "1e-6,2e-6,3e-6";
        String result = solver.solve_sum_of_squares(vector_values_str);
        double expected = Math.pow(1e-6, 2) + Math.pow(2e-6, 2) + Math.pow(3e-6, 2);
        assertEquals(expected, Double.parseDouble(result), 1e-10);
    }

    @Test
    public void test_negative_input() {
        String vector_values_str = "-1,-4,-2";
        String result = solver.solve_sum_of_squares(vector_values_str);
        double expected = Math.pow(-1, 2) + Math.pow(-4, 2) + Math.pow(-2, 2);
        assertEquals(expected, Double.parseDouble(result), 1e-10);
    }
}