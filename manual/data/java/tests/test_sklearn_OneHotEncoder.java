package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_sklearn_OneHotEncoder {

    private function_sklearn_OneHotEncoder encoder = new function_sklearn_OneHotEncoder();

    @Test
    public void test_function_call() {
        String data = "category1 5";
        try {
            String result = encoder.test_one_hot_encoding(data);
            assertTrue(result instanceof String);
        } catch (Exception e) {
            fail("Function call failed with exception: " + e);
        }
    }

    @Test
    public void test_correct_data() {
        String result = encoder.test_one_hot_encoding("category1 5");
        assertTrue(result instanceof String);
    }

    @Test
    public void test_encoded_data() {
        String result = encoder.test_one_hot_encoding("category1 5");
        String[] resultSplit = result.split(",");
        assertEquals("5", resultSplit[0]);
        assertEquals("1.0", resultSplit[1]);
    }

    @Test
    public void test_invalid_data_type() {
        // 在 Java 中由于静态类型限制，无法传入非 String，因此模拟传入 null
        Exception exception = assertThrows(RuntimeException.class, () -> {
            encoder.test_one_hot_encoding(null);
        });
        assertEquals("Input data must be a string.", exception.getMessage());
    }

    @Test
    public void test_missing_categorical_data() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            encoder.test_one_hot_encoding("12");
        });
        assertEquals("The input data has incorrect structure.", exception.getMessage());
    }
}