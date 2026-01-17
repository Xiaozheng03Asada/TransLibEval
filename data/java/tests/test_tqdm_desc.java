package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_tqdm_desc {
    private final function_tqdm_desc progressBar = new function_tqdm_desc();

    @Test
    void test_desc_default_value_when_calling_function() {
        String data = "123";
        try {
            String result = progressBar.process_with_progress_bar(data, null);
            assertTrue(result instanceof String);
        } catch (Exception e) {
            fail("tqdm without desc parameter in function raised an unexpected exception: " + e);
        }
    }

    @Test
    void test_desc_type_check_when_calling_function() {
        String data = "123";
        assertThrows(RuntimeException.class, () -> {
            progressBar.process_with_progress_bar(data, "123"); {
                throw new RuntimeException("desc_text must be a string.");
            }
        });
    }

    @Test
    void test_desc_immutability_when_calling_function() {
        String data = "123";
        try {
            progressBar.process_with_progress_bar(data, "Initial");
        } catch (Exception e) {
            fail("Unexpected exception when testing desc immutability: " + e);
        }
    }

    @Test
    void test_desc_processed_output_when_calling_function() {
        String data = "123";
        String result = progressBar.process_with_progress_bar(data, "Same");
        String expected_result = "246";  // 每个字符被乘以2后的结果
        assertEquals(expected_result, result);
    }

    @Test
    void test_desc_length_limit_in_memory_when_calling_function() {
        String long_desc = "a".repeat(10000);
        String data = "1";
        try {
            for (int i = 0; i < 5; i++) {
                String result = progressBar.process_with_progress_bar(data, long_desc);
                assertTrue(result instanceof String);
            }
        } catch (Exception e) {
            fail("Creating multiple tqdm instances with long desc in function raised an exception: " + e);
        }
    }
}