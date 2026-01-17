package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class test_catboost_importance {
    private function_catboost_importance importance_calculator;

    @BeforeEach
    public void setUp() {
        importance_calculator = new function_catboost_importance();
    }

    @Test
    public void test_prediction_values_change() {
        // 训练，label 为 0
        importance_calculator.process("train", 1.0f, 2.0f, 3.0f, 0, null);
        // importance 测试，使用 PredictionValuesChange 类型
        float result = importance_calculator.process("importance", 1.0f, 2.0f, 3.0f, null, "PredictionValuesChange");
        // 检查返回值为 float 类型（Java中使用基本类型 float）
        assertTrue(((Object)result) instanceof Float);
    }

    @Test
    public void test_loss_function_change() {
        // 训练，label 为 1
        importance_calculator.process("train", 4.0f, 5.0f, 6.0f, 1, null);
        // 使用 LossFunctionChange 类型计算 importance
        float result = importance_calculator.process("importance", 4.0f, 5.0f, 6.0f, null, "LossFunctionChange");
        assertTrue(((Object)result) instanceof Float);
    }

    @Test
    public void test_shap_values() {
        // 训练，label 为 0
        importance_calculator.process("train", 7.0f, 8.0f, 9.0f, 0, null);
        // 使用 ShapValues 类型计算 importance
        float result = importance_calculator.process("importance", 7.0f, 8.0f, 9.0f, null, "ShapValues");
        assertTrue(((Object)result) instanceof Float);
    }

    @Test
    public void test_invalid_importance_type() {
        // 训练，label 为 1
        importance_calculator.process("train", 10.0f, 11.0f, 12.0f, 1, null);
        // 应抛出异常
        assertThrows(IllegalArgumentException.class, () -> {
            importance_calculator.process("importance", 10.0f, 11.0f, 12.0f, null, "InvalidType");
        });
    }

    @Test
    public void test_different_inputs() {
        // 第一次训练
        importance_calculator.process("train", 1.5f, 2.5f, 3.5f, 0, null);
        float result_1 = importance_calculator.process("importance", 1.5f, 2.5f, 3.5f, null, "PredictionValuesChange");
        // 第二次训练，覆盖之前的状态
        importance_calculator.process("train", 4.5f, 5.5f, 6.5f, 1, null);
        float result_2 = importance_calculator.process("importance", 4.5f, 5.5f, 6.5f, null, "PredictionValuesChange");
        assertNotEquals(result_1, result_2);
    }
}