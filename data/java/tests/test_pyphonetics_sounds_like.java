package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_pyphonetics_sounds_like {
    private function_pyphonetics_sounds_like processor;

    @BeforeEach
    void setUp() {
        processor = new function_pyphonetics_sounds_like();
    }

    @Test
    void test_identical_strings() {
        String result = processor.compare_strings("hello", "hello");
        assertEquals("true", result);
    }

    @Test
    void test_similar_sounding_words() {
        String result = processor.compare_strings("hello", "halo");
        assertEquals("true", result);
    }

    @Test
    void test_different_sounding_words() {
        String result = processor.compare_strings("hello", "world");
        assertEquals("false", result);
    }

    @Test
    void test_empty_strings() {
        String result = processor.compare_strings("", "");
        assertEquals("The given string is empty.", result);
    }

    @Test
    void test_empty_and_non_empty() {
        String result = processor.compare_strings("hello", "");
        assertEquals("The given string is empty.", result);
    }
}