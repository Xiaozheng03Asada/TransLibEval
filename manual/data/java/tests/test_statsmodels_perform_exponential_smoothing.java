package com.example;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class test_statsmodels_perform_exponential_smoothing {

    @Test
    public void test_additive_seasonality() throws Exception {
        String data = "[10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10, 10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10]";
        function_statsmodels_perform_exponential_smoothing processor = new function_statsmodels_perform_exponential_smoothing();
        String result = processor.perform_exponential_smoothing(data, 5, "add", 12);
        assertTrue(result.contains("Forecast"));
    }

    @Test
    public void test_multiplicative_seasonality() throws Exception {
        String data = "[10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10, 10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10]";
        function_statsmodels_perform_exponential_smoothing processor = new function_statsmodels_perform_exponential_smoothing();
        String result = processor.perform_exponential_smoothing(data, 5, "mul", 12);
        assertTrue(result.contains("Forecast"));
    }

    @Test
    public void test_input_type_error() {
        function_statsmodels_perform_exponential_smoothing processor = new function_statsmodels_perform_exponential_smoothing();
        assertThrows(IllegalArgumentException.class, () -> {
            processor.perform_exponential_smoothing("invalid_input", 3, "add", 12);
        });
    }

    @Test
    public void test_invalid_seasonal_type() {
        String data = "[10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10, 10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10]";
        function_statsmodels_perform_exponential_smoothing processor = new function_statsmodels_perform_exponential_smoothing();
        assertThrows(IllegalArgumentException.class, () -> {
            processor.perform_exponential_smoothing(data, 5, "invalid", 12);
        });
    }

    @Test
    public void test_invalid_forecast_steps() {
        String data = "[10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10, 10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10]";
        function_statsmodels_perform_exponential_smoothing processor = new function_statsmodels_perform_exponential_smoothing();
        assertThrows(IllegalArgumentException.class, () -> {
            processor.perform_exponential_smoothing(data, -1, "add", 12);
        });
    }
}