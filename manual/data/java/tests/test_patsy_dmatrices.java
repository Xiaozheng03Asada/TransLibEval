package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class test_patsy_dmatrices {
    private String data;
    private function_patsy_dmatrices processor;

    @BeforeEach
    public void setUp() {
        data = "{" +
                "    \"y\": [1, 2, 3, 4]," +
                "    \"x1\": [2, 4, 6, 8]," +
                "    \"x2\": [1, 3, 5, 7]," +
                "    \"category\": [\"A\", \"B\", \"A\", \"B\"]" +
                "}";
        processor = new function_patsy_dmatrices();
    }

    @Test
    public void test_simple_formula() {
        String formula = "y ~ x1 + x2";
        String result = processor.process_formula(data, formula);
        // 检查结果包含正确的形状信息
        assertTrue(result.contains("\"y_shape\":[4,1]"));
        assertTrue(result.contains("\"X_shape\":[4,3]"));
    }

    @Test
    public void test_interaction_formula() {
        String formula = "y ~ x1 * x2";
        String result = processor.process_formula(data, formula);
        // 检查交互项模型的形状
        assertTrue(result.contains("\"X_shape\":[4,4]"));
    }

    @Test
    public void test_polynomial_formula() {
        String formula = "y ~ x1 + I(x1 ** 2) + I(x1 ** 3)";
        String result = processor.process_formula(data, formula);
        // 检查多项式模型的形状
        assertTrue(result.contains("\"X_shape\":[4,4]"));
    }

    @Test
    public void test_invalid_formula() {
        String formula = "y ~ non_existent_column";
        String result = processor.process_formula(data, formula);
        assertEquals("Error: invalid input", result);
    }

    @Test
    public void test_malformed_json() {
        String malformed_data = "{\"y\": [1, 2, 3, 4], \"x1\": [2, 4, 6, 8], \"x2\": [1, 3, 5, 7],";
        String formula = "y ~ x1 + x2";
        String result = processor.process_formula(malformed_data, formula);
        assertEquals("Error: invalid input", result);
    }
}