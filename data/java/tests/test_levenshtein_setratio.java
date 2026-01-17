package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_levenshtein_setratio {
    private function_levenshtein_setratio processor;

    @BeforeEach
    void setUp() {
        processor = new function_levenshtein_setratio();
    }

    @Test
    void test_equal_strings() {
        double result = processor.calculate_setratio("test", "test");
        assertEquals(1.0, result);
    }

    @Test
    void test_empty_strings() {
        double result = processor.calculate_setratio("", "");
        assertEquals(1.0, result);
    }

    @Test
    void test_completely_different_strings() {
        double result = processor.calculate_setratio("abc", "xyz");
        assertEquals(0.0, result);
    }

    @Test
    void test_partial_match() {
        double result = processor.calculate_setratio("kitten", "sitting");
        assertEquals(0.6153846153846154, result, 0.1);
    }

    @Test
    void test_single_character_difference() {
        double result = processor.calculate_setratio("a", "b");
        assertEquals(0.0, result);
    }
}