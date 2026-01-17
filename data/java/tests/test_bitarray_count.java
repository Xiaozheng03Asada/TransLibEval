package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_bitarray_count {

    // test_count_bits_random_pattern: 测试随机模式下统计1和0的数量
    @Test
    public void test_count_bits_random_pattern() {
        function_bitarray_count func = new function_bitarray_count();
        String ba = "1010101111001101";
        assertEquals(10, func.count_bits(ba, 1), "Count of 1's should be 10");
        assertEquals(6, func.count_bits(ba, 0), "Count of 0's should be 6");
    }

    // test_count_all: 测试全1和全0的情况
    @Test
    public void test_count_all() {
        function_bitarray_count func = new function_bitarray_count();
        String ba1 = "1111111";
        assertEquals(7, func.count_bits(ba1, 1), "Count of 1's should be 7");

        String ba2 = "0000000";
        assertEquals(7, func.count_bits(ba2, 0), "Count of 0's should be 7");
    }

    // test_invalid_value: 使用无效的bit值（2）应抛出异常
    @Test
    public void test_invalid_value() {
        function_bitarray_count func = new function_bitarray_count();
        String ba = "1100";
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            func.count_bits(ba, 2);
        });
        assertTrue(ex.getMessage().contains("Invalid bit value"));
    }

    // test_non_string_input: Java中参数类型已限定为String，这里用null模拟非字符串输入，应抛出异常
    @Test
    public void test_non_string_input() {
        function_bitarray_count func = new function_bitarray_count();
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            func.count_bits(null, 1);
        });
        assertTrue(ex.getMessage().contains("non-null"));
    }

    // test_count_bits_subrange: 测试对字符串子串的统计
    @Test
    public void test_count_bits_subrange() {
        function_bitarray_count func = new function_bitarray_count();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("101010");
        }
        String ba = sb.toString();
        // 截取子串（下标范围1000-2000）
        String sub_ba = ba.substring(1000, 2000);
        assertEquals(500, func.count_bits(sub_ba, 1), "Subrange count of 1's should be 500");
        assertEquals(500, func.count_bits(sub_ba, 0), "Subrange count of 0's should be 500");
    }
}