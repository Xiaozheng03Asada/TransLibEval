package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_bitarray_extend {

    // 测试有效输入：扩展"101"与"010"应返回"101010"
    @Test
    public void test_extend_with_valid_bits() {
        function_bitarray_extend extender = new function_bitarray_extend();
        String result = extender.extend_bits("101", "010");
        assertEquals("101010", result, "Expected extended bit string to be '101010'");
    }

    // 测试无效输入：当values中含有非二进制字符时应抛出异常
    @Test
    public void test_invalid_input_non_binary() {
        function_bitarray_extend extender = new function_bitarray_extend();
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            extender.extend_bits("101", "102");
        });
        assertTrue(ex.getMessage().contains("All elements in the iterable must be '0' or '1'"));
    }

    // 测试非字符串输入：由于Java为静态类型，这里用null模拟非字符串输入的情况
    @Test
    public void test_non_string_input() {
        function_bitarray_extend extender = new function_bitarray_extend();
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            // 模拟非字符串输入，使用null作为bits
            extender.extend_bits(null, "010");
        });
        assertTrue(ex.getMessage().contains("Input must be a string"));
    }

    // 测试values为空字符串时，结果应与原bits相同
    @Test
    public void test_extend_with_empty_string() {
        function_bitarray_extend extender = new function_bitarray_extend();
        String result = extender.extend_bits("101", "");
        assertEquals("101", result, "Expected extended bit string to be '101'");
    }

    // 测试bits为空字符串时，结果应只返回values
    @Test
    public void test_extend_empty_string_with_bits() {
        function_bitarray_extend extender = new function_bitarray_extend();
        String result = extender.extend_bits("", "010");
        assertEquals("010", result, "Expected extended bit string to be '010'");
    }
}