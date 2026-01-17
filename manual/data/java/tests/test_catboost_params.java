package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_catboost_params {
    private function_catboost_params paramFetcher;

    @BeforeEach
    void setUp() {
        paramFetcher = new function_catboost_params();
    }

    @Test
    void test_return_type() {
        String result = paramFetcher.get_model_params(150, 7, 0.1f);
        assertNotNull(result);
        assertTrue(result instanceof String);
    }

    @Test
    void test_check_iterations() {
        String result = paramFetcher.get_model_params(200, 6, 0.1f);
        assertTrue(result.contains("Iterations: 200"));
    }

    @Test
    void test_check_depth() {
        String result = paramFetcher.get_model_params(100, 10, 0.05f);
        assertTrue(result.contains("Depth: 10"));
    }

    @Test
    void test_check_learning_rate() {
        String result = paramFetcher.get_model_params(100, 6, 0.01f);
        assertTrue(result.contains("Learning Rate: 0.01"));
    }

    @Test
    void test_multiple_conditions() {
        String result = paramFetcher.get_model_params(250, 8, 0.15f);
        assertTrue(result.contains("Iterations: 250"));
        assertTrue(result.contains("Depth: 8"));
        assertTrue(result.contains("Learning Rate: 0.15"));
    }
}