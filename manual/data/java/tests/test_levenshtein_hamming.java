package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_levenshtein_hamming {
    private function_levenshtein_hamming processor;

    @BeforeEach
    void setUp() {
        processor = new function_levenshtein_hamming();
    }

    @Test
    void test_equal_strings() {
        String result = processor.calculate_hamming_distance("test", "test");
        assertEquals("0", result);
    }

    @Test
    void test_single_character_difference() {
        String result = processor.calculate_hamming_distance("objective", "objection");
        assertEquals("2", result);
    }

    @Test
    void test_insertions_and_deletions() {
        String result = processor.calculate_hamming_distance("hello", "helo");
        assertEquals("Strings must be of the same length for Hamming distance.", result);
    }

    @Test
    void test_single_substitution() {
        String result = processor.calculate_hamming_distance("flaw", "lawn");
        assertEquals("4", result);
    }

    @Test
    void test_non_string_input() {
        String result = processor.calculate_hamming_distance(null, "hello");
        assertEquals("Error: Both inputs must be strings", result);
    }
}