package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_nltk_jaccard_distance {

    @Test
    public void test_compute_jaccard_distance_identical_strings() {
        function_nltk_jaccard_distance calc = new function_nltk_jaccard_distance();
        double result = calc.compute_jaccard_distance("apple", "apple");
        // Identical strings should return a Jaccard distance of 0.0
        assertEquals(0.0, result, 1e-6);
    }

    @Test
    public void test_compute_jaccard_distance_different_strings() {
        function_nltk_jaccard_distance calc = new function_nltk_jaccard_distance();
        double result = calc.compute_jaccard_distance("apple", "orange");
        // Different strings should yield a positive distance
        assertTrue(result > 0.0);
    }

    @Test
    public void test_compute_jaccard_distance_empty_strings() {
        function_nltk_jaccard_distance calc = new function_nltk_jaccard_distance();
        double result = calc.compute_jaccard_distance("", "");
        // Both empty strings should return 0.0
        assertEquals(0.0, result, 1e-6);
    }

    @Test
    public void test_compute_jaccard_distance_partial_overlap() {
        function_nltk_jaccard_distance calc = new function_nltk_jaccard_distance();
        double result = calc.compute_jaccard_distance("apple", "app");
        // Partial overlap should yield a positive distance
        assertTrue(result > 0.0);
    }

    @Test
    public void test_non_string_input() {
        function_nltk_jaccard_distance calc = new function_nltk_jaccard_distance();
        // Passing a null value simulates an invalid input and should throw a RuntimeException.
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            calc.compute_jaccard_distance("apple", null);
        });
        assertTrue(ex.getMessage().contains("non-null strings"));
    }
}