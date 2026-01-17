package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_collections_ordereddict {

    // 测试单个键值对
    @Test
    public void test_single_key_value_pair() {
        function_collections_ordereddict calc = new function_collections_ordereddict();
        String result = calc.process_key_value_pairs("apple:1");
        assertEquals("apple:1", result);
    }

    // 测试多个键值对
    @Test
    public void test_multiple_key_value_pairs() {
        function_collections_ordereddict calc = new function_collections_ordereddict();
        String result = calc.process_key_value_pairs("apple:1, banana:2, cherry:3");
        assertEquals("apple:1, banana:2, cherry:3", result);
    }

    // 测试空输入
    @Test
    public void test_empty_input() {
        function_collections_ordereddict calc = new function_collections_ordereddict();
        String result = calc.process_key_value_pairs("");
        assertEquals("failed", result);
    }

    // 测试存在空白键值对项
    @Test
    public void test_no_key_value_pair() {
        function_collections_ordereddict calc = new function_collections_ordereddict();
        String result = calc.process_key_value_pairs("apple:1, , cherry:3");
        assertEquals("apple:1, cherry:3", result);
    }

    // 测试非数字的值，确保返回结果类型为 String
    @Test
    public void test_non_numeric() {
        function_collections_ordereddict calc = new function_collections_ordereddict();
        String result = calc.process_key_value_pairs("apple:one, banana:two");
        assertTrue(result instanceof String);
    }
}