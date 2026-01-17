package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

class test_joblib_parallel {

    @Test
    void test_parallel_function_basic() {
        function_joblib_parallel processor = new function_joblib_parallel();
        String result_1 = processor.run_parallel(0, 1.0f, 2.0, 3, 4, null, null, null, null, null, 2);
        assertEquals(5, result_1.split(", ").length);
    }

    @Test
    void test_parallel_function_empty_data() {
        function_joblib_parallel processor = new function_joblib_parallel();
        String result_6 = processor.run_parallel(null, null, null, null, null, null, null, null, null, null, 2);
        assertEquals("", result_6);
    }

    @Test
    void test_parallel_function_mixed_data_types() {
        function_joblib_parallel processor = new function_joblib_parallel();
        String result_10 = processor.run_parallel(1, 2.5f, 3.0, null, null, null, null, null, null, null, 2);
        assertEquals(3, result_10.split(", ").length);
    }

    @Test
    void test_parallel_function_data_with_max_int() {
        function_joblib_parallel processor = new function_joblib_parallel();
        String result_13 = processor.run_parallel(1, (float) (Math.pow(2, 31) - 1), null, null, null, null, null, null, null, null, 2);
        assertEquals(2, result_13.split(", ").length);
    }

    @Test
    void test_parallel_function_random_data() {
        Random random = new Random();
        function_joblib_parallel processor = new function_joblib_parallel();
        int data_14_0 = random.nextInt(2001) - 1000;
        int data_14_1 = random.nextInt(2001) - 1000;
        String result_14 = processor.run_parallel(data_14_0, (float)data_14_1, null, null, null, null, null, null, null, null, -1);
        assertEquals(2, result_14.split(", ").length);
    }
}