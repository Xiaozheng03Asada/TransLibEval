package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class test_gensim_models_Phrases {

    @Test
    public void test_phrase_detection() {
        String input = "new york is great;new york city is big;san francisco is beautiful;los angeles is sunny";
        // 对于参数：min_count=1, threshold=5.0；应该检测到 "new_york"
        function_gensim_models_Phrases model = new function_gensim_models_Phrases();
        String result = model.train_phrase_model(input, 1, 5.0);
        assertTrue(result.contains("new_york"), "Expected result to contain new_york");
    }

    @Test
    public void test_no_short_phrases() {
        String input = "new york is great;new york city is big;san francisco is beautiful;los angeles is sunny";
        // 对于参数：min_count=2, threshold=20.0；"los_angeles" 只出现过一次，所以不应被检测到。
        function_gensim_models_Phrases model = new function_gensim_models_Phrases();
        String result = model.train_phrase_model(input, 2, 20.0);
        assertFalse(result.contains("los_angeles"), "Expected result not to contain los_angeles");
    }

    @Test
    public void test_high_threshold() {
        String input = "new york is great;new york city is big;san francisco is beautiful;los angeles is sunny";
        // 对于参数：min_count=1, threshold=50.0；阈值太高，"new_york" 不应被检测到。
        function_gensim_models_Phrases model = new function_gensim_models_Phrases();
        String result = model.train_phrase_model(input, 1, 50.0);
        assertFalse(result.contains("new_york"), "Expected result not to contain new_york");
    }

    @Test
    public void test_single_sentence() {
        String input = "this is a test case";
        // 单个句子：每个相邻词对只出现一次，因此不应该有短语合并。
        function_gensim_models_Phrases model = new function_gensim_models_Phrases();
        String result = model.train_phrase_model(input, 1, 1.0);
        assertEquals("No phrases detected", result);
    }

    @Test
    public void test_overlapping_phrases() {
        String input = "new york city is new york;new york is big";
        // 对于重叠短语，由于重复出现，预期可以检测到 "new_york"
        function_gensim_models_Phrases model = new function_gensim_models_Phrases();
        String result = model.train_phrase_model(input, 1, 1.0);
        assertTrue(result.contains("new_york"), "Expected result to contain new_york");
    }
}