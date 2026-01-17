package com.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class test_patsy_dmatrix {

    private String data;
    private function_patsy_dmatrix dmatrix;  // 添加类实例声明

    @BeforeEach
    public void setUp() {
        data = "{\n" +
                "    \"x\": [1, 2, 3, 4],\n" +
                "    \"y\": [2, 4, 6, 8],\n" +
                "    \"z\": [5, 6, 7, 8],\n" +
                "    \"w\": [10, 15, 20, 25]\n" +
                "}";
        dmatrix = new function_patsy_dmatrix();  // 在setUp中实例化
    }

    @Test
    public void test_polynomial_formula() {
        String formula = "x + I(x ** 2)";
        String result = dmatrix.generate_matrix(data, formula);
        assertTrue(result.contains("\"matrix_shape\":[4,3]") || result.contains("\"matrix_shape\": [4, 3]"));
    }

    @Test
    public void test_categorical_variable() {
        String formula = "C(z)";
        String result = dmatrix.generate_matrix(data, formula);
        assertTrue(result.contains("\"matrix_shape\":[4,4]") || result.contains("\"matrix_shape\": [4, 4]"));  // 修改断言，检查完整的shape
    }

    @Test
    public void test_multiple_interaction_terms() {
        String formula = "x * z * w";
        String result = dmatrix.generate_matrix(data, formula);
        assertTrue(result.contains("\"matrix_shape\":[4,8]") || result.contains("\"matrix_shape\": [4, 8]"));
    }

    @Test
    public void test_conditional_expression() {
        String formula = "x + (z > 6)";
        String result = dmatrix.generate_matrix(data, formula);
        assertTrue(result.contains("\"matrix_shape\":[4,3]") || result.contains("\"matrix_shape\": [4, 3]"));
    }

    @Test
    public void test_dummy_variable_encoding() {
        String formula = "C(w > 15)";
        String result = dmatrix.generate_matrix(data, formula);
        assertTrue(result.contains("\"matrix_shape\":[4,2]") || result.contains("\"matrix_shape\": [4, 2]"));
    }
}