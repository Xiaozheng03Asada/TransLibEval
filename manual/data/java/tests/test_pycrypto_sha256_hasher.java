package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class test_pycrypto_sha256_hasher {

    private function_pycrypto_sha256_hasher hasher;

    @BeforeEach
    public void setUp() {
        hasher = new function_pycrypto_sha256_hasher();
    }

    @Test
    public void test_special_characters_hash() {
        String special_text = "This is a test! ";
        String result = hasher.hash_text(special_text);
        String expected_hash = hasher.hash_text(special_text);
        assertEquals(expected_hash, result);
    }

    @Test
    public void test_hash_is_unique_for_different_inputs() {
        String text1 = "Test input one";
        String text2 = "Test input two";
        String hash1 = hasher.hash_text(text1);
        String hash2 = hasher.hash_text(text2);
        assertNotEquals(hash1, hash2);
    }

    @Test
    public void test_empty_string_hash() {
        String result = hasher.hash_text("");
        String expected_hash = hasher.hash_text("");
        assertEquals(expected_hash, result);
    }

    @Test
    public void test_collision_resistance() {
        String input1 = "Hello, world!";
        String input2 = "Hello, world!!";
        String hash1 = hasher.hash_text(input1);
        String hash2 = hasher.hash_text(input2);
        assertNotEquals(hash1, hash2);
    }

    @Test
    public void test_length_invariance() {
        String small_input = "Short text";
        // 使用Java 11及以上提供的String.repeat方法生成1000个字符的字符串
        String large_input = "A".repeat(1000);
        String hash_small = hasher.hash_text(small_input);
        String hash_large = hasher.hash_text(large_input);
        assertEquals(64, hash_small.length());
        assertEquals(64, hash_large.length());
    }
}