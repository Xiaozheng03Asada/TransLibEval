package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class test_spacy_load {

    private final function_spacy_load spacyLoad = new function_spacy_load();

    @Test
    public void test_normal_text() {
        // Test for normal text input
        String normalText = "This is a normal sentence.";
        String result = spacyLoad.spacy_text(normalText);
        assertEquals("This is a normal sentence .", result);
    }

    @Test
    public void test_text_with_duplicate_words() {
        // Test for text with duplicate words
        String duplicateText = "apple apple banana apple";
        String result = spacyLoad.spacy_text(duplicateText);
        assertEquals("apple apple banana apple", result);
    }

    @Test
    public void test_memory_error() {
        // Test memory error scenario by providing a huge string
        String hugeText = "a".repeat(100000000);
        String result = spacyLoad.spacy_text(hugeText);
        assertEquals("Insufficient memory error", result);
    }

    @Test
    public void test_other_error() {
        // Test for other errors with null input
        String result = spacyLoad.spacy_text(null);
        assertNotNull(result);
        assertTrue(result.startsWith("Other errors:"));
    }

    @Test
    public void test_multilingual_text() {
        // Test for multilingual text input
        String multiText = "Bonjour, this is a test. 你好";
        String result = spacyLoad.spacy_text(multiText);
        assertEquals("Bonjour , this is a test . 你好", result);
    }
}