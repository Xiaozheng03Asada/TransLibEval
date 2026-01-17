package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_catboost_predict {

    @Test
    public void test_basic_classification() {
        function_catboost_predict predictor = new function_catboost_predict();
        int result = predictor.predict_class(1f, 2f, 3f, 4f, 5f, 0, 0, 1, 1, 1, 3f);
        assertEquals(1, result);
    }

    @Test
    public void test_custom_threshold() {
        function_catboost_predict predictor = new function_catboost_predict();
        int result = predictor.predict_class(10f, 15f, 20f, 25f, 30f, 0, 0, 1, 1, 1, 18f);
        assertEquals(1, result);
    }

    @Test
    public void test_edge_case_low_value() {
        function_catboost_predict predictor = new function_catboost_predict();
        int result = predictor.predict_class(5f, 6f, 7f, 8f, 9f, 0, 0, 1, 1, 1, 4f);
        assertEquals(0, result);
    }

    @Test
    public void test_edge_case_high_value() {
        function_catboost_predict predictor = new function_catboost_predict();
        int result = predictor.predict_class(10f, 20f, 30f, 40f, 50f, 0, 0, 1, 1, 1, 60f);
        assertEquals(1, result);
    }

    @Test
    public void test_invalid_inputs() {
        function_catboost_predict predictor = new function_catboost_predict();

        // 测试无效的 float 值
        assertThrows(IllegalArgumentException.class, () -> {
            predictor.predict_class(1f, 2f, 3f, Float.NaN, 5f, 0, 1, 1, 1, 1, 4f);
        });

        // 测试无效的整数标签（非 0 或 1）
        assertThrows(IllegalArgumentException.class, () -> {
            predictor.predict_class(1f, 2f, 3f, 4f, 5f, 0, -1, 1, 1, 1, 4f);
        });
    }
}