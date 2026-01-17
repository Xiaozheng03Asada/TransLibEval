package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

public class test_statsmodels_perform_seasonal_decomposition {

    static String additive_data;

    @BeforeAll
    public static void setUpClass() {
        additive_data = "10.248357, 10.248557, 10.248757, 10.248957, 10.249157, 10.249357, 10.249557, 10.249757, 10.249957, 10.250157";
    }

    @Test
    public void test_additive_decomposition() {
        function_statsmodels_perform_seasonal_decomposition func = new function_statsmodels_perform_seasonal_decomposition();
        String result = func.perform_seasonal_decomposition(additive_data, "additive", 12);
        assertTrue(result.contains("Trend"));
        assertTrue(result.contains("Seasonal"));
        assertTrue(result.contains("Residual"));
    }

    @Test
    public void test_multiplicative_decomposition() {
        function_statsmodels_perform_seasonal_decomposition func = new function_statsmodels_perform_seasonal_decomposition();
        String result = func.perform_seasonal_decomposition(additive_data, "multiplicative", 12);
        assertTrue(result.contains("Trend"));
        assertTrue(result.contains("Seasonal"));
        assertTrue(result.contains("Residual"));
    }

    @Test
    public void test_invalid_model_type() {
        function_statsmodels_perform_seasonal_decomposition func = new function_statsmodels_perform_seasonal_decomposition();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            func.perform_seasonal_decomposition(additive_data, "invalid", 12);
        });
        assertEquals("Model must be 'additive' or 'multiplicative'.", exception.getMessage());
    }

    @Test
    public void test_invalid_period() {
        function_statsmodels_perform_seasonal_decomposition func = new function_statsmodels_perform_seasonal_decomposition();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            func.perform_seasonal_decomposition(additive_data, "additive", -1);
        });
        assertEquals("Period must be a positive integer.", exception.getMessage());
    }

    @Test
    public void test_non_string_input() {
        function_statsmodels_perform_seasonal_decomposition func = new function_statsmodels_perform_seasonal_decomposition();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            func.perform_seasonal_decomposition(null, "additive", 12);
        });
        assertEquals("Input data must be a string.", exception.getMessage());
    }
}