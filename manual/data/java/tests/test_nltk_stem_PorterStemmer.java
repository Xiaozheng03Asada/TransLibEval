package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_nltk_stem_PorterStemmer {

    @Test
    public void test_stem_running() {
        function_nltk_stem_PorterStemmer stemmer = new function_nltk_stem_PorterStemmer();
        String result = stemmer.test_stem("running");
        assertEquals("run", result);
    }

    @Test
    public void test_stem_played() {
        function_nltk_stem_PorterStemmer stemmer = new function_nltk_stem_PorterStemmer();
        String result = stemmer.test_stem("played");
        assertEquals("plai", result);
    }

    @Test
    public void test_stem_dogs() {
        function_nltk_stem_PorterStemmer stemmer = new function_nltk_stem_PorterStemmer();
        String result = stemmer.test_stem("dogs");
        assertEquals("dog", result);
    }

    @Test
    public void test_stem_empty_string() {
        function_nltk_stem_PorterStemmer stemmer = new function_nltk_stem_PorterStemmer();
        String result = stemmer.test_stem("");
        assertEquals("", result);
    }

    @Test
    public void test_stem_word_with_special_characters() {
        function_nltk_stem_PorterStemmer stemmer = new function_nltk_stem_PorterStemmer();
        String result = stemmer.test_stem("$#@!word");
        assertEquals("$#@!word", result);
    }
}