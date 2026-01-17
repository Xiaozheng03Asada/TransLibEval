package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_tqdm_mininterval {
    private function_tqdm_mininterval progressBar;

    @BeforeEach
    void setUp() {
        progressBar = new function_tqdm_mininterval();
    }

    @Test
    void test_mininterval_very_small() {
        long startTime = System.currentTimeMillis();
        int result = progressBar.might_fail_function(0.01f);
        long elapsedTime = System.currentTimeMillis() - startTime;

        // 修改断言，考虑到实际执行时间
        assertTrue(elapsedTime <= 10 * 100 + 500); // 10次循环 * 100ms + 500ms缓冲
        assertEquals(10, result);
    }

    @Test
    void test_mininterval_very_large() {
        long startTime = System.currentTimeMillis();
        int result = progressBar.might_fail_function(2.0f);
        long elapsedTime = System.currentTimeMillis() - startTime;

        assertTrue(elapsedTime <= 2 * 10 * 1000); // 最大允许2秒 * 10次
        assertEquals(10, result);
    }

    @Test
    void test_mininterval_zero() {
        long startTime = System.currentTimeMillis();
        int result = progressBar.might_fail_function(0.0f);
        long elapsedTime = System.currentTimeMillis() - startTime;

        assertTrue(elapsedTime > 0 && elapsedTime <= 2000);
        assertEquals(10, result);
    }

    @Test
    void test_mininterval_non_numeric() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            progressBar.might_fail_function(Float.NaN);
        });
        assertTrue(exception.getMessage().contains("mininterval must be a number"));
    }

    @Test
    void test_mininterval_modify_during_loop() {
        int result1 = progressBar.might_fail_function(0.3f);
        assertEquals(10, result1);

        int result2 = progressBar.might_fail_function(0.1f);
        assertEquals(10, result2);
    }
}