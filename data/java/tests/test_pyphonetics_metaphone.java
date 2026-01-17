package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_pyphonetics_metaphone {
    private function_pyphonetics_metaphone processor;

    @BeforeEach
    void setUp() {
        processor = new function_pyphonetics_metaphone();
    }

    @Test
    void test_basic_string() {
        String result = processor.generate_phonetics("discrimination");
        assertEquals("TSKMNXN", result);
    }

    @Test
    void test_empty_string() {
        String result = processor.generate_phonetics("");
        assertEquals("The given string is empty.", result);
    }

    @Test
    void test_single_word() {
        String result = processor.generate_phonetics("hello");
        assertEquals("HL", result);
    }

    @Test
    void test_case_insensitivity() {
        String result = processor.generate_phonetics("Hello");
        assertEquals("HL", result);
    }

    @Test
    void test_long_word() {
        String result = processor.generate_phonetics("internationalization");
        assertEquals("ANTRNXNLSXN", result);
    }
}