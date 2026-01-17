package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class test_lightgbm_predict {
    private function_lightgbm_predict predictor;

    @BeforeEach
    void setUp() {
        predictor = new function_lightgbm_predict();
    }

    @Test
    void test_predict_multiple_samples() {
        String input_str = "0.1,0.2,0.3,0.4,0.5;0.6,0.7,0.8,0.9,1.0";
        String result = predictor.quick_sort_from_string(input_str);
        double[] resultValues = Arrays.stream(result.split(","))
                .mapToDouble(Double::parseDouble)
                .toArray();
        assertEquals(2, resultValues.length);
    }

    @Test
    void test_predict_result_type() {
        String input_str = "0.1,0.2,0.3,0.4,0.5";
        String result = predictor.quick_sort_from_string(input_str);
        double value = Double.parseDouble(result.split(",")[0]);
        assertTrue(value >= 0.0);
    }

    @Test
    void test_predict_result_range() {
        String input_str = "0.1,0.2,0.3,0.4,0.5";
        String result = predictor.quick_sort_from_string(input_str);
        double[] resultValues = Arrays.stream(result.split(","))
                .mapToDouble(Double::parseDouble)
                .toArray();
        for (double r : resultValues) {
            assertTrue(r >= 0 && r <= 1);
        }
    }

    @Test
    void test_predict_invalid_dimension_data() {
        String input_str = "0.1,0.2,0.3,0.4;0.1,0.2,0.3";
        assertThrows(IllegalArgumentException.class, () ->
                predictor.quick_sort_from_string(input_str)
        );
    }

    @Test
    void test_predict_non_numeric_data() {
        String input_str = "a,b,c,d,e;f,g,h,i,j";
        assertThrows(IllegalArgumentException.class, () ->
                predictor.quick_sort_from_string(input_str)
        );
    }
}