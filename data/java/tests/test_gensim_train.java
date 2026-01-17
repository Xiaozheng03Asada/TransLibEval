package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class test_gensim_train {

    private function_gensim_train gensimTrain;

    @BeforeEach
    public void setUp() {
        // 实例化 function_gensim_train 对象
        gensimTrain = new function_gensim_train();
    }

    @Test
    public void test_train_word2vec_model_valid() {
        String result = gensimTrain.train_word2vec_model("dog barks at cat;cat meows at dog", 50, 2, 1);
        assertTrue(result.contains("dog"), "结果中应包含 'dog'");
        assertTrue(result.contains("cat"), "结果中应包含 'cat'");
    }

    @Test
    public void test_train_word2vec_model_invalid_data() {
        String result = gensimTrain.train_word2vec_model("", 50, 2, 1);
        assertEquals("Training failed", result);
    }

    @Test
    public void test_train_word2vec_model_min_count() {
        String result = gensimTrain.train_word2vec_model("dog barks;cat meows;dog and cat", 50, 2, 2);
        assertFalse(result.contains("barks"), "结果中不应包含 'barks'");
        assertFalse(result.contains("meows"), "结果中不应包含 'meows'");
    }

    @Test
    public void test_train_word2vec_model_custom_params() {
        String result = gensimTrain.train_word2vec_model("dog barks at cat;cat meows at dog", 100, 3, 1);
        assertTrue(result.contains("dog"), "结果中应包含 'dog'");
        assertTrue(result.contains("cat"), "结果中应包含 'cat'");
    }

    @Test
    public void test_train_word2vec_model_with_special_characters() {
        String result = gensimTrain.train_word2vec_model("dog barks;cat@home meows;hello! world", 50, 2, 1);
        assertTrue(result.contains("cat@home"), "结果中应包含 'cat@home'");
        assertTrue(result.contains("hello!"), "结果中应包含 'hello!'");
    }
}