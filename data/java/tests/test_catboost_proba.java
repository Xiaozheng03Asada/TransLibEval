package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_catboost_proba {

    @Test
    void testBasicProbability() {
        function_catboost_proba predictor = new function_catboost_proba();
        // 显式转换为float
        float result = predictor.predict_probability(
                1.0f,  // float而不是double
                2.0f,
                3.0f,
                4.0f,
                5.0f,
                0,
                0,
                1,
                1,
                1,
                3.5f
        );
        assertTrue(result >= 0.5f);
    }

    @Test
    void testHighProbability() {
        function_catboost_proba predictor = new function_catboost_proba();
        float result = predictor.predict_probability(
                10.0f,
                20.0f,
                30.0f,
                40.0f,
                50.0f,
                0,
                0,
                1,
                1,
                1,
                35.0f
        );
        assertTrue(result >= 0.5f);
    }

    @Test
    void testLowProbability() {
        function_catboost_proba predictor = new function_catboost_proba();
        float result = predictor.predict_probability(
                10.0f,
                20.0f,
                30.0f,
                40.0f,
                50.0f,
                0,
                0,
                1,
                1,
                1,
                5.0f
        );
        assertTrue(result < 0.5f);
    }

    @Test
    void testEdgeCase() {
        function_catboost_proba predictor = new function_catboost_proba();
        float result = predictor.predict_probability(
                1.0f,
                2.0f,
                3.0f,
                4.0f,
                5.0f,
                0,
                0,
                1,
                1,
                1,
                10.0f
        );
        assertInstanceOf(Float.class, result);
    }

    @Test
    void testInvalidInputs() {
        function_catboost_proba predictor = new function_catboost_proba();
        assertThrows(IllegalArgumentException.class, () -> {
            predictor.predict_probability(
                    1.0f,
                    2.0f,
                    Float.NaN,
                    4.0f,
                    5.0f,
                    0,
                    0,
                    1,
                    1,
                    1,
                    3.5f
            );
        });
    }
}