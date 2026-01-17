package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_levenshtein_editops {
    private function_levenshtein_editops processor;

    @BeforeEach
    void setUp() {
        processor = new function_levenshtein_editops();
    }

    @Test
    void test_equal_strings() {
        String result = processor.calculate_editops("test", "test");
        assertEquals("[]", result);
    }

    @Test
    void test_single_substitution() {
        String result = processor.calculate_editops("flaw", "lawn");
        assertEquals("[('delete', 0, 0), ('insert', 4, 3)]", result);
    }

    @Test
    void test_insertions_and_deletions() {
        String result = processor.calculate_editops("hello", "helo");
        assertEquals("[('delete', 3, 3)]", result);

        result = processor.calculate_editops("helo", "hello");
        assertEquals("[('insert', 3, 3)]", result);
    }

    @Test
    void test_multiple_operations() {
        String result = processor.calculate_editops("kitten", "sitting");
        assertEquals("[('replace', 0, 0), ('replace', 4, 4), ('insert', 6, 6)]", result);
    }

    @Test
    void test_non_string_input() {
        String result = processor.calculate_editops(null, "hello");
        assertEquals("Error: Both inputs must be strings", result);

        result = processor.calculate_editops("hello", null);
        assertEquals("Error: Both inputs must be strings", result);
    }
}