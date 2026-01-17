package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class test_lightgbm_feature_importance {
    private function_lightgbm_feature_importance feature_importance;

    @BeforeEach
    void setUp() {
        feature_importance = new function_lightgbm_feature_importance();
    }

    @Test
    void test_feature_importance_length() {
        String input_str = "100,5";
        String result = feature_importance.quick_sort_from_string(input_str);
        String[] importance_scores = result.split(",");
        assertEquals(5, importance_scores.length);
    }

    @Test
    void test_feature_importance_non_negative() {
        String input_str = "100,5";
        String result = feature_importance.quick_sort_from_string(input_str);
        boolean allNonNegative = Arrays.stream(result.split(","))
                .mapToInt(Integer::parseInt)
                .allMatch(score -> score >= 0);
        assertTrue(allNonNegative);
    }

    @Test
    void test_feature_importance_sum() {
        String input_str = "100,5";
        String result = feature_importance.quick_sort_from_string(input_str);
        int sum = Arrays.stream(result.split(","))
                .mapToInt(Integer::parseInt)
                .sum();
        assertTrue(sum > 0);
    }

    @Test
    void test_feature_importance_type() {
        String input_str = "100,5";
        String result = feature_importance.quick_sort_from_string(input_str);
        assertInstanceOf(String.class, result);
    }

    @Test
    void test_feature_importance_large_number_of_features() {
        String input_str = "100,1000";
        String result = feature_importance.quick_sort_from_string(input_str);
        String[] importance_scores = result.split(",");
        assertEquals(1000, importance_scores.length);
    }
}