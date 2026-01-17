package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_levenshtein_distance {
    private function_levenshtein_distance processor;

    @BeforeEach
    public void setUp() {
        processor = new function_levenshtein_distance();
    }

    @Test
    public void test_equal_strings() {
        int result = processor.calculate_levenshtein_distance("test", "test");
        assertEquals(0, result);
    }

    @Test
    public void test_insertions_and_deletions() {
        int result = processor.calculate_levenshtein_distance("hello", "helo");
        assertEquals(1, result);
        result = processor.calculate_levenshtein_distance("helo", "hello");
        assertEquals(1, result);
    }

    @Test
    public void test_single_substitution() {
        int result = processor.calculate_levenshtein_distance("flaw", "lawn");
        assertEquals(2, result);
    }

    @Test
    public void test_multiple_operations() {
        int result = processor.calculate_levenshtein_distance("kitten", "sitting");
        assertEquals(3, result);
    }



    @Test
    public void test_non_string_input() {
        // Test when the first argument is null
        assertThrows(IllegalArgumentException.class, () -> {
            processor.calculate_levenshtein_distance(null, "hello");
        });

        // Test when the second argument is null
        assertThrows(IllegalArgumentException.class, () -> {
            processor.calculate_levenshtein_distance("hello", null);
        });
    }
}