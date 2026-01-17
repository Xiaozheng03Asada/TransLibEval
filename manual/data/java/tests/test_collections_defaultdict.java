package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_collections_defaultdict {
    private function_collections_defaultdict counter;

    @BeforeEach
    void setUp() {
        counter = new function_collections_defaultdict();
    }

    @Test
    void test_count_words_normal_case() {
        String word_string = "apple,banana,apple,cherry,banana,apple";
        String result = counter.count_words(word_string);
        assertEquals("apple:3;banana:2;cherry:1", result);
    }

    @Test
    void test_count_words_empty_string() {
        String word_string = "";
        String result = counter.count_words(word_string);
        assertEquals("", result);
    }

    @Test
    void test_count_words_with_non_string_elements() {
        String word_string = "apple,123,banana,apple,true,cherry,banana,apple";
        String result = counter.count_words(word_string);
        assertEquals("apple:3;123:1;banana:2;true:1;cherry:1", result);
    }

    @Test
    void test_count_words_case_sensitivity() {
        String word_string = "Apple,apple,Banana,banana";
        String result = counter.count_words(word_string);
        assertEquals("apple:2;banana:2", result);
    }

    @Test
    void test_count_words_error_handling() {
        assertThrows(IllegalArgumentException.class, () -> counter.count_words(null));
    }
}