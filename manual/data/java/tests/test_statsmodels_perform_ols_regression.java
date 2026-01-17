package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import java.util.NoSuchElementException;

public class test_statsmodels_perform_ols_regression {

    static String data;

    @BeforeAll
    public static void setUpClass() {
        // 测试数据，与python中相同
        data = "{'X1': [1, 2, 3, 4, 5], 'X2': [2, 4, 6, 8, 10], 'Y': [1.2, 2.4, 3.1, 4.0, 5.1]}";
    }

    @Test
    public void test_simple_regression() {
        function_statsmodels_perform_ols_regression func = new function_statsmodels_perform_ols_regression();
        String result = func.perform_ols_regression(data, "Y", "X1");
        assertTrue(result.contains("R-squared"));
        assertTrue(result.contains("Coefficients"));
    }

    @Test
    public void test_multiple_regression() {
        function_statsmodels_perform_ols_regression func = new function_statsmodels_perform_ols_regression();
        String result = func.perform_ols_regression(data, "Y", "X1,X2");
        assertTrue(result.contains("R-squared"));
    }

    @Test
    public void test_missing_columns() {
        function_statsmodels_perform_ols_regression func = new function_statsmodels_perform_ols_regression();
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            func.perform_ols_regression(data, "Z", "X1");
        });
        assertTrue(exception.getMessage().contains("KeyError"));
    }

    @Test
    public void test_insufficient_data() {
        function_statsmodels_perform_ols_regression func = new function_statsmodels_perform_ols_regression();
        String smallData = "{'X1': [1], 'Y': [2]}";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            func.perform_ols_regression(smallData, "Y", "X1");
        });
        assertEquals("Insufficient data for regression.", exception.getMessage());
    }

    @Test
    public void test_mixed_data_types() {
        function_statsmodels_perform_ols_regression func = new function_statsmodels_perform_ols_regression();
        String mixData = "{'X1': [1, 'b', 3, 4, 'e'], 'Y': [1, 2, 3, 4, 5]}";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            func.perform_ols_regression(mixData, "Y", "X1");
        });
        assertEquals("Dependent variable contains non-numeric data.", exception.getMessage());
    }
}