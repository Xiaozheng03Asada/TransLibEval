package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_decimal_as_tuple {
    private function_decimal_as_tuple getDecimalTupleFunc;

    @BeforeEach
    void setUp() {
        getDecimalTupleFunc = new function_decimal_as_tuple();
    }

    @Test
    void test_positive_integer() {
        String result = getDecimalTupleFunc.check_discount_for_large_order("12345");
        assertEquals("0,12345,0", result);
    }

    @Test
    void test_negative_integer() {
        String result = getDecimalTupleFunc.check_discount_for_large_order("-12345");
        assertEquals("1,12345,0", result);
    }

    @Test
    void test_positive_decimal() {
        String result = getDecimalTupleFunc.check_discount_for_large_order("123.45");
        assertEquals("0,12345,-2", result);
    }

    @Test
    void test_negative_decimal() {
        String result = getDecimalTupleFunc.check_discount_for_large_order("-123.45");
        assertEquals("1,12345,-2", result);
    }

    @Test
    void test_zero_value() {
        String result = getDecimalTupleFunc.check_discount_for_large_order("0");
        assertEquals("0,0,0", result);
    }
}