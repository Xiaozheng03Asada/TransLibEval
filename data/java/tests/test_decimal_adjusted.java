package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_decimal_adjusted {

    private function_decimal_adjusted getDecimalAdjustedFunc;

    @BeforeEach
    void setUp() {
        getDecimalAdjustedFunc = new function_decimal_adjusted();
    }

    @Test
    void test_large_value() {
        String result = getDecimalAdjustedFunc.check_discount_for_large_order("12345.6789");
        assertEquals("4", result);
    }

    @Test
    void test_small_value() {
        String result = getDecimalAdjustedFunc.check_discount_for_large_order("0.00012345");
        assertEquals("-4", result);
    }

    @Test
    void test_no_decimal() {
        String result = getDecimalAdjustedFunc.check_discount_for_large_order("12345");
        assertEquals("4", result);
    }

    @Test
    void test_large_negative_value() {
        String result = getDecimalAdjustedFunc.check_discount_for_large_order("-9876.54321");
        assertEquals("3", result);
    }

    @Test
    void test_large_negative_range_value() {
        String result = getDecimalAdjustedFunc.check_discount_for_large_order("-1E+100");
        assertEquals("100", result);
    }
}