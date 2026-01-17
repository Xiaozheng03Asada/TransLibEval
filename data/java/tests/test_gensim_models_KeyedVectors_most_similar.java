package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_gensim_models_KeyedVectors_most_similar {

    @Test
    public void test_find_most_similar_words_valid() {
        function_gensim_models_KeyedVectors_most_similar finder = new function_gensim_models_KeyedVectors_most_similar();
        String result = finder.find_most_similar_words("king", 3);
        assertTrue(result.contains("queen: 1.00"), "Result should contain 'queen: 1.00'");
        assertTrue(result.contains("man: 1.00"), "Result should contain 'man: 1.00'");
    }

    @Test
    public void test_find_most_similar_words_invalid() {
        function_gensim_models_KeyedVectors_most_similar finder = new function_gensim_models_KeyedVectors_most_similar();
        String result = finder.find_most_similar_words("nonexistent", 3);
        assertEquals("Error", result);
    }

    @Test
    public void test_find_most_similar_words_topn() {
        function_gensim_models_KeyedVectors_most_similar finder = new function_gensim_models_KeyedVectors_most_similar();
        String result = finder.find_most_similar_words("king", 2);
        String[] parts = result.split(", ");
        assertEquals(2, parts.length);
    }

    @Test
    public void test_find_most_similar_words_edge_case() {
        function_gensim_models_KeyedVectors_most_similar finder = new function_gensim_models_KeyedVectors_most_similar();
        String result = finder.find_most_similar_words("not_in_vocab", 3);
        assertEquals("Error", result);
    }

    @Test
    public void test_find_most_similar_words_empty_model() {
        function_gensim_models_KeyedVectors_most_similar finder = new function_gensim_models_KeyedVectors_most_similar();
        // Clear the model to simulate an empty model.
        finder.word_vectors = new java.util.LinkedHashMap<>();
        String result = finder.find_most_similar_words("king", 3);
        // Now the method reinitializes the model, so the result should be based on the default data.
        assertEquals("queen: 1.00, man: 1.00, woman: 1.00", result);
    }
}