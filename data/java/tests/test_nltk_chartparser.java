package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class test_nltk_chartparser {

    @Test
    public void test_correct_sentence_parsing() {
        String sentence = "the dog chased the cat";
        function_nltk_chartparser parser = new function_nltk_chartparser();
        String result = parser.test_chartparser(sentence);
        // 对于正确的句法，返回的解析树字符串应不为空
        assertTrue(result.length() > 0);
    }

    @Test
    public void test_wrong_word_order() {
        String sentence = "chased the dog the cat";
        function_nltk_chartparser parser = new function_nltk_chartparser();
        String result = parser.test_chartparser(sentence);
        // 错误的词序应返回空字符串
        assertEquals("", result);
    }

    @Test
    public void test_missing_word() {
        String sentence = "the dog the cat";
        function_nltk_chartparser parser = new function_nltk_chartparser();
        String result = parser.test_chartparser(sentence);
        // 缺少关键单词时应返回空字符串
        assertEquals("", result);
    }

    @Test
    public void test_duplicate_nouns() {
        String sentence = "the dog the dog chased the cat";
        function_nltk_chartparser parser = new function_nltk_chartparser();
        String result = parser.test_chartparser(sentence);
        // 多余的单词（例如重复的名词）应返回空字符串
        assertEquals("", result);
    }

    @Test
    public void test_single_word_sentence() {
        String sentence = "dog";
        function_nltk_chartparser parser = new function_nltk_chartparser();
        String result = parser.test_chartparser(sentence);
        // 单个词的句子不符合规则，应返回空字符串
        assertEquals("", result);
    }
}
