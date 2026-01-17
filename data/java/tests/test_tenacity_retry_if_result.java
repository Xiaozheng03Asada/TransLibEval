package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_tenacity_retry_if_result {
    private function_tenacity_retry_if_result testInstance;

    @BeforeEach
    void setUp() {
        testInstance = new function_tenacity_retry_if_result();
    }

    @Test
    void test_success_on_first_try() {
        int result = testInstance.might_fail_function(6, false);
        assertEquals(6, result);
    }

    @Test
    void test_failure_after_max_attempts() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            testInstance.might_fail_function(2, true);
        });
        assertTrue(exception.getMessage().contains("Value is too small"));
    }

    @Test
    void test_retry_on_result() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            testInstance.might_fail_function(2, true);
        });
        assertTrue(exception.getMessage().contains("Value is too small"));
    }

    @Test
    void test_no_retry_on_result() {
        int result = testInstance.might_fail_function(5, false);
        assertEquals(5, result);
    }

    @Test
    void test_retry_with_custom_logic() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            testInstance.might_fail_function(3, true);
        });
        assertTrue(exception.getMessage().contains("Value is too small"));
    }
}