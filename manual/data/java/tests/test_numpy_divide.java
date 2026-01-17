package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_numpy_divide {

    @Test
    public void test_divide_same_shape_1d_arrays() {
        Double a = 4.0; // Using 4.0 as a substitute for the 1D array element.
        Double b = 2.0; // Using 2.0 as a substitute for the 1D array element.
        function_numpy_divide division_calculator = new function_numpy_divide();
        double result = division_calculator.divide(a, b, null);
        double expected_result = 2.0;
        assertEquals(expected_result, result, 0.000001);
    }

    @Test
    public void test_divide_same_shape_2d_arrays() {
        Double c = 4.0;
        Double d = 2.0;
        function_numpy_divide division_calculator = new function_numpy_divide();
        double result = division_calculator.divide(c, d, null);
        double expected_result = 2.0;
        assertEquals(expected_result, result, 0.000001);
    }

    @Test
    public void test_divide_1d_array_by_scalar() {
        Double e = 6.0;
        Double scalar = 2.0;
        function_numpy_divide division_calculator = new function_numpy_divide();
        double result = division_calculator.divide(e, null, scalar);
        double expected_result = 3.0;
        assertEquals(expected_result, result, 0.000001);
    }

    @Test
    public void test_divide_1d_array_with_zero_by_non_zero_scalar() {
        Double e = 0.0;
        Double scalar = 2.0;
        function_numpy_divide division_calculator = new function_numpy_divide();
        double result = division_calculator.divide(e, null, scalar);
        double expected_result = 0.0;
        assertEquals(expected_result, result, 0.000001);
    }

    @Test
    public void test_divide_broadcastable_arrays() {
        Double f = 6.0;
        Double g = 2.0;
        function_numpy_divide division_calculator = new function_numpy_divide();
        double result = division_calculator.divide(f, g, null);
        double expected_result = 3.0;
        assertEquals(expected_result, result, 0.000001);
    }
}