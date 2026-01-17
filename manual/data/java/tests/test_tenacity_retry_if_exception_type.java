package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_tenacity_retry_if_exception_type {
    private function_tenacity_retry_if_exception_type func;

    @BeforeEach
    public void setUp() {
        func = new function_tenacity_retry_if_exception_type();
    }

    @Test
    public void test_success_after_retry() {
        int result = func.might_fail_function(5, false);
        assertEquals(5, result);
    }

    @Test
    public void test_failure_after_max_attempts() {
        assertThrows(RuntimeException.class, () -> {
            func.might_fail_function(-1, true);
        });
    }

    @Test
    public void test_retry_on_value_error_with_custom_message() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            func.might_fail_function(2, true);
        });
        assertTrue(exception.getMessage().contains("Value is too small"),
                "Expected exception message to contain 'Value is too small', but was: " + exception.getMessage());
    }

    @Test
    public void test_no_retry_on_exception() {
        assertThrows(RuntimeException.class, () -> {
            func.might_fail_function(5, true);
        });
    }

    @Test
    public void test_retry_on_multiple_value_errors() {
        assertThrows(RuntimeException.class, () -> {
            for (int i = 0; i < 4; i++) {
                func.might_fail_function(2, true);
            }
        });
    }
}