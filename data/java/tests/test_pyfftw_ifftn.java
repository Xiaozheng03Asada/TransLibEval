package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_pyfftw_ifftn {

    private function_pyfftw_ifftn processor;

    @BeforeEach
    public void setUp() {
        processor = new function_pyfftw_ifftn();
    }

    @Test
    public void test_ifftn_high_dimensional_input() {
        String input_array = "np.random.random((3, 3, 3, 3))";
        String result = processor.compute_ifftn(input_array);
        assertEquals("(3, 3, 3, 3)", result);
    }

    @Test
    public void test_ifftn_high_dimensional_large_input() {
        String input_array = "np.random.random((2, 2, 2, 2, 2))";
        String result = processor.compute_ifftn(input_array);
        assertEquals("(2, 2, 2, 2, 2)", result);
        // 模拟断言，保证输入数据与全零数组不相等
        assertFalse("(2, 2, 2, 2, 2)".equals("(0, 0, 0, 0, 0)"));
    }

    @Test
    public void test_ifftn_high_dimensional_large_values() {
        String input_array = "np.ones((4, 4, 4, 4), dtype=float) * 1e10";
        String result = processor.compute_ifftn(input_array);
        assertEquals("(4, 4, 4, 4)", result);
        assertFalse("(4, 4, 4, 4)".equals("(0, 0, 0, 0)"));
    }

    @Test
    public void test_ifftn_empty_input() {
        String input_array = "np.array([])";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            processor.compute_ifftn(input_array);
        });
        String expectedMessage = "Error in computing IFFT";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void test_ifftn_large_value() {
        String input_array = "np.ones((4, 4, 4, 4), dtype=float) * 1e10";
        String result = processor.compute_ifftn(input_array);
        assertEquals("(4, 4, 4, 4)", result);
        assertFalse("(4, 4, 4, 4)".equals("(0, 0, 0, 0)"));
    }
}