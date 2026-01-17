package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_pyphonetics_phonetics {
    private function_pyphonetics_phonetics processor;

    @BeforeEach
    void setUp() {
        processor = new function_pyphonetics_phonetics();
    }

    @Test
    void test_basic_string() {
        String result = processor.generate_soundex("hello");
        assertEquals("H400", result);
    }

    @Test
    void test_same_sound() {
        String result = processor.generate_soundex("halo");
        assertEquals("H400", result);
    }

    @Test
    void test_different_sound() {
        String result = processor.generate_soundex("world");
        assertEquals("W643", result);
    }

    @Test
    void test_empty_string() {
        String result = processor.generate_soundex("");
        assertEquals("The given string is empty.", result);
    }

    @Test
    void test_single_character() {
        String result = processor.generate_soundex("a");
        assertEquals("A000", result);
    }
}