package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_spacy_pos {

    @Test
    void testGetPosTagsEmptyText() {
        function_spacy_pos processor = new function_spacy_pos();
        String result = processor.test_pos("");
        assertTrue(result instanceof String);
    }

    @Test
    void testNounVerbAdjectiveAdverbPunctuation() {
        function_spacy_pos processor = new function_spacy_pos();
        String text = "The quick brown fox jumps quickly. And it's a good day!";
        String result = processor.test_pos(text);

        // Print actual output for debugging
        System.out.println("Actual output: " + result);

        // 使用contains而不是完全匹配，因为单词的顺序可能不同
        assertTrue(result.contains("fox (NOUN)"), "Should contain 'fox (NOUN)'");
        assertTrue(result.contains("day (NOUN)"), "Should contain 'day (NOUN)'");
        assertTrue(result.contains("jumps (VERB)"), "Should contain 'jumps (VERB)'");
        assertTrue(result.contains("quick (ADJ)"), "Should contain 'quick (ADJ)'");
        assertTrue(result.contains("good (ADJ)"), "Should contain 'good (ADJ)'");
        assertTrue(result.contains("quickly (ADV)"), "Should contain 'quickly (ADV)'");
        assertTrue(result.contains(". (PUNCT)"), "Should contain '. (PUNCT)'");
        assertTrue(result.contains("! (PUNCT)"), "Should contain '! (PUNCT)'");
    }

    @Test
    void testGetPosTagsSentence() {
        function_spacy_pos processor = new function_spacy_pos();
        String text = "The quick brown fox jumps over the lazy dog.";
        String result = processor.test_pos(text);
        assertEquals(10, result.split(", ").length);
    }

    @Test
    void testGetPosTagsSentenceType() {
        function_spacy_pos processor = new function_spacy_pos();
        String text = "The quick brown fox jumps over the lazy dog.";
        String result = processor.test_pos(text);

        for (String wordPos : result.split(", ")) {
            String[] parts = wordPos.split(" ");
            assertTrue(parts[0] instanceof String);
            assertTrue(parts[1] instanceof String);
        }
    }

    @Test
    void testGetPosTagsNonStringInput() {
        function_spacy_pos processor = new function_spacy_pos();
        assertThrows(IllegalArgumentException.class, () -> {
            processor.test_pos(null);
        });
    }
}