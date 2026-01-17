package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class test_pyfftw_fftn {

    // Test 1: Input 3x3 array, expecting "(3, 3)"
    @Test
    public void test_compute_fftn_3x3() {
        function_pyfftw_fftn processor = new function_pyfftw_fftn();
        String input = "[[1, 2, 3], [4, 5, 6], [7, 8, 9]]";
        String result = processor.compute_fftn(input);
        assertEquals("(3, 3)", result);
    }

    // Test 2: Input a 3x3 array of zeros, expecting "(3, 3)"
    @Test
    public void test_fftn_zero_3x3() {
        function_pyfftw_fftn processor = new function_pyfftw_fftn();
        String input = "[[0, 0, 0], [0, 0, 0], [0, 0, 0]]";
        String result = processor.compute_fftn(input);
        assertEquals("(3, 3)", result);
    }

    // Test 3: Input a 2x2 array, expecting "(2, 2)"
    @Test
    public void test_fftn_2x2() {
        function_pyfftw_fftn processor = new function_pyfftw_fftn();
        String input = "[[1, 2], [3, 4]]";
        String result = processor.compute_fftn(input);
        assertEquals("(2, 2)", result);
    }

    // Test 4: Input a complex array (in string form), expecting "(2, 2)"
    @Test
    public void test_fftn_complex_input() {
        function_pyfftw_fftn processor = new function_pyfftw_fftn();
        String input = "[[1+1j, 2+2j], [3+3j, 4+4j]]";
        String result = processor.compute_fftn(input);
        assertEquals("(2, 2)", result);
    }

    // Test 5: Input a 4-dimensional array, expecting "(1, 2, 2, 2)"
    @Test
    public void test_fftn_high_dimensional_large_input() {
        function_pyfftw_fftn processor = new function_pyfftw_fftn();
        String input = "[[[[0.1, 0.2], [0.3, 0.4]], [[0.5, 0.6], [0.7, 0.8]]]]";
        String result = processor.compute_fftn(input);
        assertEquals("(1, 2, 2, 2)", result);
    }
}