package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_sklearn_mean_squared_error {

    @Test
    public void test_perfect_fit() {
        String X = "1,2,3";
        String y = "2,4,6";
        function_sklearn_mean_squared_error calculator = new function_sklearn_mean_squared_error();
        String result = calculator.calculate(X, y);
        assertEquals(0.0, Double.parseDouble(result), 1e-6);
    }

    @Test
    public void test_non_linear_data() {
        String X = "1,2,3,4";
        String y = "1,4,9,16";
        function_sklearn_mean_squared_error calculator = new function_sklearn_mean_squared_error();
        String result = calculator.calculate(X, y);
        assertTrue(Double.parseDouble(result) > 0.0);
    }

    @Test
    public void test_random_data_shape_check() {
        String X = "0.1,0.2,0.3,0.4,0.5";
        String y = "0.2,0.4,0.6,0.8,1.0";
        function_sklearn_mean_squared_error calculator = new function_sklearn_mean_squared_error();
        String result = calculator.calculate(X, y);
        assertTrue(Double.parseDouble(result) > 0.0);
    }

    @Test
    public void test_random_data_value() {
        String X = "0.2,0.4,0.6,0.8,1.0";
        String y = "0.1,0.3,0.5,0.7,0.9";
        function_sklearn_mean_squared_error calculator = new function_sklearn_mean_squared_error();
        String result = calculator.calculate(X, y);
        assertTrue(Double.parseDouble(result) >= 0.0);
    }

    @Test
    public void test_incompatible_shapes() {
        String X = "1,2";
        String y = "1,2,3";
        function_sklearn_mean_squared_error calculator = new function_sklearn_mean_squared_error();
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(X, y));
    }
}