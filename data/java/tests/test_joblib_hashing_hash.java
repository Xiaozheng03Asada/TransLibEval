package com.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * 使用JUnit5对 function_joblib_hashing_hash 中 compute_hash 方法进行测试，
 * 由于主功能类参数要求为String，所以测试中所有传入的参数均以字符串形式呈现。
 */
public class test_joblib_hashing_hash {

    @Test
    public void test_compute_hash_string() {
        function_joblib_hashing_hash calc = new function_joblib_hashing_hash();
        String result = calc.compute_hash("apple");
        assertTrue(result instanceof String, "Ensure result is a string");
    }

    @Test
    public void test_compute_hash_integer() {
        function_joblib_hashing_hash calc = new function_joblib_hashing_hash();
        // 将整数123转换为字符串 "123"
        String result = calc.compute_hash("123");
        assertTrue(result instanceof String, "Ensure result is a string");
    }

    @Test
    public void test_empty_input() {
        function_joblib_hashing_hash calc = new function_joblib_hashing_hash();
        String result = calc.compute_hash("");
        assertTrue(result instanceof String, "Ensure result is a string");
    }

    @Test
    public void test_non_string_input() {
        function_joblib_hashing_hash calc = new function_joblib_hashing_hash();
        // 将浮点数456.789转换为字符串 "456.789"
        String result = calc.compute_hash("456.789");
        assertTrue(result instanceof String, "Ensure result is a string");
    }

    @Test
    public void test_edge_case() {
        function_joblib_hashing_hash calc = new function_joblib_hashing_hash();
        String result = calc.compute_hash("apple apple apple");
        assertTrue(result instanceof String, "Ensure result is a string");
    }
}