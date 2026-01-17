package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class test_pyfftw_interfaces_numpy_fft_rfft {

    private function_pyfftw_interfaces_numpy_fft_rfft processor;

    @BeforeEach
    public void setUp() {
        processor = new function_pyfftw_interfaces_numpy_fft_rfft();
    }

    @Test
    public void test_rfft_simple_array() {
        String input_array = "np.array([1.0, 2.0, 3.0, 4.0])";
        String result = processor.compute_rfft(input_array);
        assertEquals("(3,)", result);
    }

    @Test
    public void test_rfft_zeros() {
        String input_array = "np.zeros(8)";
        String result = processor.compute_rfft(input_array);
        assertEquals("(5,)", result);
    }

    @Test
    public void test_rfft_large_input() {
        String input_array = "np.random.random(1024)";
        String result = processor.compute_rfft(input_array);
        assertEquals("(513,)", result);
    }

    @Test
    public void test_rfft_invalid_input() {
        String input_array = "np.array([\"a\", \"b\", \"c\", \"d\"])";
        Exception exception = assertThrows(RuntimeException.class, () -> {
            processor.compute_rfft(input_array);
        });
        String expectedMessage = "Error in computing RFFT:";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void test_rfft_empty_input() {
        String input_array = "np.array([])";
        Exception exception = assertThrows(RuntimeException.class, () -> {
            processor.compute_rfft(input_array);
        });
        String expectedMessage = "Error in computing RFFT:";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}