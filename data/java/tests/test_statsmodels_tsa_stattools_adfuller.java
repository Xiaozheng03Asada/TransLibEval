package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

public class test_statsmodels_tsa_stattools_adfuller {

    static String stationary_series;
    static String non_stationary_series;
    static String invalid_series;

    @BeforeAll
    public static void setUpClass() {
        stationary_series = "1,1.2,0.9,1.1,0.95,1.05,0.9,1.1,0.97,1.02";
        non_stationary_series = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15";
        invalid_series = "1,2,3";
    }

    // 测试非平稳时间序列，期待返回值中包含"Non-Stationary"
    @Test
    public void test_non_stationary_series() {
        function_statsmodels_tsa_stattools_adfuller instance = new function_statsmodels_tsa_stattools_adfuller();
        String result = instance.perform_adf_test(non_stationary_series, 0.05f);
        assertTrue(result.contains("Non-Stationary"));
    }

    // 测试数据不足的情况，期待抛出异常
    @Test
    public void test_short_series() {
        String short_series = "1,2,3";
        function_statsmodels_tsa_stattools_adfuller instance = new function_statsmodels_tsa_stattools_adfuller();
        assertThrows(IllegalArgumentException.class, () -> {
            instance.perform_adf_test(short_series, 0.05f);
        });
    }

    // 测试无效数字输入，期待抛出异常
    @Test
    public void test_invalid_input_type() {
        // 这里传入的字符串虽为数字，但数量不足（5个数据少于要求的10个），故应抛出异常
        String invalid_series_input = "1,2,3,4,5";
        function_statsmodels_tsa_stattools_adfuller instance = new function_statsmodels_tsa_stattools_adfuller();
        assertThrows(IllegalArgumentException.class, () -> {
            instance.perform_adf_test(invalid_series_input, 0.05f);
        });
    }

    // 测试使用自定义显著性水平时，对于平稳序列应返回"Stationary"
    @Test
    public void test_custom_significance_level() {
        function_statsmodels_tsa_stattools_adfuller instance = new function_statsmodels_tsa_stattools_adfuller();
        String result = instance.perform_adf_test(stationary_series, 0.1f);
        assertTrue(result.contains("Stationary"));
    }

    // 测试空字符串时，期待抛出异常
    @Test
    public void test_empty_series() {
        String empty_series = "";
        function_statsmodels_tsa_stattools_adfuller instance = new function_statsmodels_tsa_stattools_adfuller();
        assertThrows(IllegalArgumentException.class, () -> {
            instance.perform_adf_test(empty_series, 0.05f);
        });
    }

    @AfterAll
    public static void tearDownClass() {
        // 清理操作（如果有必要）
    }
}