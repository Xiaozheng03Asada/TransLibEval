package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_lightgbm_eval {
    private function_lightgbm_eval evalFunction;
    private String testData;

    @BeforeEach
    void setUp() {
        evalFunction = new function_lightgbm_eval();
        testData = "0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5";
    }

    @Test
    void testModelTrainingWithEvalMetric() {
        assertDoesNotThrow(() -> evalFunction.evaluate(testData));
    }

    @Test
    void testEvalMetricLogloss() {
        String modifiedTestData = "0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5;0.1,0.2,0.3,0.4,0.5";
        assertDoesNotThrow(() -> {
            float result = evalFunction.evaluate(modifiedTestData);
            assertTrue(result > 0.0f);
        });
    }

    @Test
    void testEvalMetricAuc() {
        String modifiedTestData = "0.3,0.5,0.1,0.2,0.4;0.4,0.3,0.2,0.1,0.5;0.6,0.1,0.5,0.4,0.3;0.1,0.2,0.3,0.4,0.5;0.2,0.4,0.1,0.5,0.3;0.3,0.5,0.2,0.1,0.4";
        assertDoesNotThrow(() -> {
            float result = evalFunction.evaluate(modifiedTestData);
            assertTrue(result > 0.4f);
        });
    }

    @Test
    void testEvalWithHighPrecision() {
        String highPrecisionData = "0.998,0.999,0.997,0.996,0.995;0.994,0.995,0.996,0.997,0.998;0.995,0.996,0.997,0.998,0.999";
        assertDoesNotThrow(() -> {
            float result = evalFunction.evaluate(highPrecisionData);
            assertTrue(result > 0.5f);
        });
    }

    @Test
    void testEvalWithInvalidData() {
        String invalidTestData = "0.1,0.2,0.3,0.4;0.1,0.2,0.3";
        assertThrows(IllegalArgumentException.class, () -> evalFunction.evaluate(invalidTestData));
    }
}