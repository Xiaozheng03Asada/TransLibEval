package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_nltk_probability {

    @Test
    public void test_compute_frequency() {
        function_nltk_probability calc = new function_nltk_probability();
        String result = calc.compute_frequency("apple");
        assertEquals("a:1, e:1, l:1, p:2", result);
    }

    @Test
    public void test_compute_frequency_with_repeated_chars() {
        function_nltk_probability calc = new function_nltk_probability();
        String result = calc.compute_frequency("banana");
        assertEquals("a:3, b:1, n:2", result);
    }

    @Test
    public void test_empty_input() {
        function_nltk_probability calc = new function_nltk_probability();
        String result = calc.compute_frequency("");
        assertEquals("", result);
    }

    @Test
    public void test_non_string_input() {
        function_nltk_probability calc = new function_nltk_probability();
        assertThrows(RuntimeException.class, () -> {
            calc.compute_frequency(null);
        });
    }

    @Test
    public void test_edge_case() {
        function_nltk_probability calc = new function_nltk_probability();
        String result = calc.compute_frequency("aabbcc");
        assertEquals("a:2, b:2, c:2", result);
    }
}