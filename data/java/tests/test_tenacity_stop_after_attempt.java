// 测试代码保持不变
package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_tenacity_stop_after_attempt {
    private function_tenacity_stop_after_attempt func;

    @BeforeEach
    void setUp() {
        func = new function_tenacity_stop_after_attempt();
    }

    @Test
    void test_success_on_first_try() {
        int result = func.might_fail_function(6, 0.0f, false);
        assertEquals(6, result);
    }

    @Test
    void test_success_after_retry() {
        int result = func.might_fail_function(5, 0.0f, false);
        assertEquals(5, result);
    }

    @Test
    void test_no_retry_on_too_small_value() {
        assertThrows(RuntimeException.class, () ->
                func.might_fail_function(1, 0.0f, false)
        );
    }

    @Test
    void test_runtimeerror_on_too_small_value() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                func.might_fail_function(1, 0.0f, true)
        );
        assertTrue(exception.getMessage().contains("Operation failed after retries"));
    }

    @Test
    void test_runtimeerror_with_extra_sleep() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                func.might_fail_function(4, 0.5f, true)
        );
        assertTrue(exception.getMessage().contains("Operation failed after retries"));
    }
}