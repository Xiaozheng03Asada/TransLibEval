package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_decimal_infinite {
    private function_decimal_infinite infiniteCheckFunc;

    @BeforeEach
    void setUp() {
        infiniteCheckFunc = new function_decimal_infinite();
    }

    @Test
    void test_positive_infinity() {
        String value = "Infinity";
        assertEquals("True", infiniteCheckFunc.check_discount_for_large_order(value));
    }

    @Test
    void test_negative_infinity() {
        String value = "-Infinity";
        assertEquals("True", infiniteCheckFunc.check_discount_for_large_order(value));
    }

    @Test
    void test_zero() {
        String value = "0.0";
        assertEquals("False", infiniteCheckFunc.check_discount_for_large_order(value));
    }

    @Test
    void test_finite_number() {
        String value = "10.5";
        assertEquals("False", infiniteCheckFunc.check_discount_for_large_order(value));
    }

    @Test
    void test_nan() {
        String value = "NaN";
        assertEquals("False", infiniteCheckFunc.check_discount_for_large_order(value));
    }
}