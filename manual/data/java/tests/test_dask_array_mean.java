package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_dask_array_mean {
    private function_dask_array_mean meanFunc;

    @BeforeEach
    void setUp() {
        meanFunc = new function_dask_array_mean();
    }

    @Test
    void test_basic_array() {
        String result = meanFunc.calculate_mean("1,2,3,4", 2);
        assertEquals("2.5", result);
    }

    @Test
    void test_floating_point_array() {
        String result = meanFunc.calculate_mean("1.5,2.5,3.5,4.5", 2);
        assertEquals("3.0", result);
    }

    @Test
    void test_high_dimensional_array() {
        String result = meanFunc.calculate_mean("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24", 4);
        assertEquals("12.5", result);
    }

    @Test
    void test_non_numeric_data() {
        String result = meanFunc.calculate_mean("a,b,c", 2);
        assertTrue(result.startsWith("Error:"));
    }

    @Test
    void test_empty_string() {
        String result = meanFunc.calculate_mean("", 2);
        assertTrue(result.startsWith("Error:"));
    }
}