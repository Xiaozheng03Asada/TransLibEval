package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_lightgbm_train {

    @Test
    void test_train_params() {
        function_lightgbm_train trainer = new function_lightgbm_train();
        float result = trainer.train("gbdt", "binary", "binary_logloss",
                31, 0.05f, 0.9f, 0.8f,
                5, 0, 1, 100, 5);
        assertTrue(result >= 0 && result <= 1);
    }

    @Test
    void test_model_training() {
        function_lightgbm_train trainer = new function_lightgbm_train();
        float result = trainer.train("gbdt", "binary", "binary_logloss",
                31, 0.05f, 0.9f, 0.8f,
                5, 0, 1, 100, 5);
        assertTrue(result >= 0 && result <= 1);
    }

    @Test
    void test_prediction_length() {
        function_lightgbm_train trainer = new function_lightgbm_train();
        float predictions = trainer.train("gbdt", "binary", "binary_logloss",
                31, 0.05f, 0.9f, 0.8f,
                5, 0, 1, 100, 5);
        assertTrue(predictions >= 0 && predictions <= 1);
    }

    @Test
    void test_missing_param_error() {
        function_lightgbm_train trainer = new function_lightgbm_train();
        assertThrows(IllegalArgumentException.class, () -> {
            trainer.train("gbdt", "", "binary_logloss",
                    31, 0.05f, 0.9f, 0.8f,
                    5, 0, 1, 100, 5);
        });
    }

    @Test
    void test_invalid_round_type_error() {
        function_lightgbm_train trainer = new function_lightgbm_train();
        assertThrows(IllegalArgumentException.class, () -> {
            trainer.train(null, "binary", "binary_logloss",
                    31, 0.05f, 0.9f, 0.8f,
                    5, 0, 1, 100, 5);
        });
    }
}