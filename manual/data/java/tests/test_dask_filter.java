package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class test_dask_filter {

    private function_dask_filter filterFunc;

    @BeforeEach
    void setUp() {
        filterFunc = new function_dask_filter();
    }

    @Test
    void test_mixed_numbers() {
        String data = "1,2,3,4,5";
        String result = filterFunc.check_discount_for_large_order(data);
        assertEquals("2,4", result);
    }

    @Test
    void test_negative_numbers() {
        String data = "-10,-9,-8,-7,-6,-5,-4,-3,-2,-1";
        String result = filterFunc.check_discount_for_large_order(data);
        assertEquals("-10,-8,-6,-4,-2", result);
    }

    @Test
    void test_invalid_format() {
        String data = "2,4,a,6";
        String result = filterFunc.check_discount_for_large_order(data);
        assertEquals("Error", result);
    }

    @Test
    void test_large_dataset() {
        String data = IntStream.rangeClosed(1, 10000)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
        String expectedResult = IntStream.rangeClosed(1, 10000)
                .filter(n -> n % 2 == 0)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
        String result = filterFunc.check_discount_for_large_order(data);
        assertEquals(expectedResult, result);
    }

    @Test
    void test_empty_input() {
        String data = "";
        String result = filterFunc.check_discount_for_large_order(data);
        assertEquals("", result);
    }
}