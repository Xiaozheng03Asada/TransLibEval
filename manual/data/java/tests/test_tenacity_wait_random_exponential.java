package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_tenacity_wait_random_exponential {

    private function_tenacity_wait_random_exponential func;

    @BeforeEach
    void setUp() {
        func = new function_tenacity_wait_random_exponential();
    }

    @Test
    void test_success_after_retry() throws Exception {
        int result = func.might_fail_function(5, 0, false);
        assertEquals(5, result);
    }

    @Test
    void test_zero_input() {
        assertThrows(Exception.class, () -> {
            func.might_fail_function(0, 0, false);
        });
    }

    @Test
    void test_retry_with_random_exponential_backoff() {
        long start_time = System.currentTimeMillis();
        assertThrows(Exception.class, () -> {
            func.might_fail_function(2, 2, false);
        });
        long end_time = System.currentTimeMillis();
        long total_time = end_time - start_time;
        System.out.println("Test execution time: " + (total_time / 1000.0) + " seconds");
        assertTrue(total_time > 6000);
        assertTrue(total_time < 30000);
    }

    @Test
    void test_success_on_first_try() throws Exception {
        int result = func.might_fail_function(6, 0, false);
        assertEquals(6, result);
    }

    @Test
    void test_failure_after_max_attempts() {
        assertThrows(Exception.class, () -> {
            func.might_fail_function(2, 0, false);
        });
    }
}