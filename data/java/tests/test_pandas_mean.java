package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_pandas_mean {

    @Test
    public void test_success() {
        function_pandas_mean calc_mean = new function_pandas_mean();
        String result = calc_mean.calculate_mean(1.0, 2.0);
        assertTrue(result.startsWith("A:"));
    }

    @Test
    public void test_mean_values() {
        function_pandas_mean calc_mean = new function_pandas_mean();
        String result = calc_mean.calculate_mean(1.0, 2.0);
        if (result.startsWith("A:")) {
            String[] mean_values_str = result.split(", ");
            double mean_A = Double.parseDouble(mean_values_str[0].split(":")[1].trim());
            double mean_B = Double.parseDouble(mean_values_str[1].split(":")[1].trim());
            assertEquals(1.0, mean_A, 0.1);
            assertEquals(2.0, mean_B, 0.1);
        }
    }

    @Test
    public void test_result_format() {
        function_pandas_mean calc_mean = new function_pandas_mean();
        String result = calc_mean.calculate_mean(1.0, 2.0);
        assertNotNull(result);
        assertTrue(result instanceof String);
    }

    @Test
    public void test_empty_data() {
        function_pandas_mean calc_mean = new function_pandas_mean();
        String result = calc_mean.calculate_mean(null, null);
        assertTrue(result.contains("A: None"));
        assertTrue(result.contains("B: None"));
    }

    @Test
    public void test_column_consistency() {
        function_pandas_mean calc_mean = new function_pandas_mean();
        String result = calc_mean.calculate_mean(1.0, 2.0);
        assertTrue(result.contains("A:"));
        assertTrue(result.contains("B:"));
    }
}