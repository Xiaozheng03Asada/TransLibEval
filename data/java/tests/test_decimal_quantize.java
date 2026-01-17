package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_decimal_quantize {
    private function_decimal_quantize set_decimal_precision_func;

    @BeforeEach
    void setUp() {
        set_decimal_precision_func = new function_decimal_quantize();
    }

    @Test
    void test_round_to_three_decimal_places() {
        String result = set_decimal_precision_func.check_discount_for_large_order("2.71828", 3);
        assertEquals("2.718", result);
    }

    @Test
    void test_round_up() {
        String result = set_decimal_precision_func.check_discount_for_large_order("1.2345", 2);
        assertEquals("1.23", result);
    }

    @Test
    void test_round_down() {
        String result = set_decimal_precision_func.check_discount_for_large_order("0.99999", 3);
        assertEquals("1.000", result);
    }

    @Test
    void test_invalid_value() {
        String result = set_decimal_precision_func.check_discount_for_large_order("invalid", 2);
        assertEquals("Error", result);
    }

    @Test
    void test_negative_number() {
        String result = set_decimal_precision_func.check_discount_for_large_order("-5.6789", 2);
        assertEquals("-5.68", result);
    }
}