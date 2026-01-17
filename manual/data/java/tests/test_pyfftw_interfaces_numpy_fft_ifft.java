package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_pyfftw_interfaces_numpy_fft_ifft {

    private function_pyfftw_interfaces_numpy_fft_ifft fftw;  // 添加类实例声明

    @BeforeEach
    public void setUp() {
        fftw = new function_pyfftw_interfaces_numpy_fft_ifft();  // 实例化对象
    }

    @Test
    public void test_ifft_simple_input() {
        String input_array = "np.array([1 + 2j, 3 + 4j, 5 + 6j, 7 + 8j])";
        String result = fftw.compute_ifft(input_array);
        assertEquals("(4,)", result);
    }

    @Test
    public void test_ifft_zero_input() {
        String input_array = "np.zeros(4, dtype=complex)";
        String result = fftw.compute_ifft(input_array);
        assertEquals("(4,)", result);
    }

    @Test
    public void test_ifft_invalid_input_real() {
        String input_array = "np.array([1.0, 2.0, 3.0, 4.0])";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fftw.compute_ifft(input_array);
        });
        String expectedMessage = "Input array must contain complex numbers.";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void test_ifft_invalid_input_string() {
        String input_array = "np.array([\"a\", \"b\", \"c\", \"d\"])";
        assertThrows(IllegalArgumentException.class, () -> {
            fftw.compute_ifft(input_array);
        });
    }

    @Test
    public void test_ifft_large_input() {
        String input_array = "np.random.random(1000) + 1j * np.random.random(1000)";
        String result = fftw.compute_ifft(input_array);
        assertEquals("(1000,)", result);
    }
}