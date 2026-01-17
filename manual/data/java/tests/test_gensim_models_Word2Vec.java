package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_gensim_models_Word2Vec {

    @Test
    public void test_train_word2vec_model() {
        String input = "dog barks;cat meows;dog chases cat";
        function_gensim_models_Word2Vec model = new function_gensim_models_Word2Vec();
        String result = model.train_word2vec_model(input);
        // Assert that the vocabulary contains both "dog" and "cat".
        assertTrue(result.contains("dog") && result.contains("cat"), "Expected result to contain both dog and cat");
    }

    @Test
    public void test_similarity_words_exist() {
        String input = "apple orange;banana grape";
        function_gensim_models_Word2Vec model = new function_gensim_models_Word2Vec();
        String result = model.train_word2vec_model(input);
        // Assert that the vocabulary contains "apple" and "orange".
        assertTrue(result.contains("apple"), "Expected result to contain apple");
        assertTrue(result.contains("orange"), "Expected result to contain orange");
    }

    @Test
    public void test_empty_input() {
        String input = "";
        function_gensim_models_Word2Vec model = new function_gensim_models_Word2Vec();
        String result = model.train_word2vec_model(input);
        assertEquals("Training failed", result, "Expected Training failed for empty input");
    }

    @Test
    public void test_single_word_sentences() {
        String input = "hello;world;test";
        function_gensim_models_Word2Vec model = new function_gensim_models_Word2Vec();
        String result = model.train_word2vec_model(input);
        // Assert that the vocabulary contains each individual word.
        assertTrue(result.contains("hello"), "Expected result to contain hello");
        assertTrue(result.contains("world"), "Expected result to contain world");
        assertTrue(result.contains("test"), "Expected result to contain test");
    }

    @Test
    public void test_large_sentence() {
        // Create a sentence with 100 words: word0 word1 ... word99
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("word").append(i);
            if(i != 99) {
                sb.append(" ");
            }
        }
        String sentence = sb.toString();
        function_gensim_models_Word2Vec model = new function_gensim_models_Word2Vec();
        String result = model.train_word2vec_model(sentence);
        // Assert that the vocabulary contains "word0" and "word99".
        assertTrue(result.contains("word0"), "Expected result to contain word0");
        assertTrue(result.contains("word99"), "Expected result to contain word99");
    }
}