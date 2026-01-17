package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_itertools_permutations {
    private function_itertools_permutations processor;

    @BeforeEach
    void setUp() {
        processor = new function_itertools_permutations();
    }

    @Test
    void test_get_permutations_content() {
        String data = "abc";
        String result = processor.get_permutations(data);
        String expectedResult = "abc, acb, bac, bca, cab, cba\nabc: 1, acb: 1, bac: 1, bca: 1, cab: 1, cba: 1";
        assertEquals(expectedResult, result);
    }

    @Test
    void test_permutation_duplicates() {
        String data = "aab";
        String result = processor.get_permutations(data);
        String expectedResult = "aab, aab, aba, aba, baa, baa\naab: 2, aba: 2, baa: 2";
        assertEquals(expectedResult, result);
    }

    @Test
    void test_invalid_type_error() {
        String data = null;  // 模拟非String类型输入
        assertThrows(IllegalArgumentException.class, () -> {
            processor.get_permutations(data);
        });
    }

    @Test
    void test_non_iterable_error() {
        assertThrows(IllegalArgumentException.class, () -> {
            // 在Java中，我们通过传入null来模拟Python中的非字符串输入
            processor.get_permutations(null);
        });
    }

    @Test
    void test_permutations_case_sensitivity() {
        String data = "Aa";
        String result = processor.get_permutations(data);
        String expectedResult = "Aa, aA\nAa: 1, aA: 1";
        assertEquals(expectedResult, result);
    }
}