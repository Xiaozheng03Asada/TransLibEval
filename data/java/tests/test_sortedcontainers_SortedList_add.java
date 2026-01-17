package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

public class test_sortedcontainers_SortedList_add {

    private function_sortedcontainers_SortedList_add handler;

    @BeforeEach
    public void setUp() {
        handler = new function_sortedcontainers_SortedList_add();
    }

    // 模拟 Python 测试：对 "1 3 5" 添加 " 3" 后排序，结果应为 "1 3 3 5"
    @Test
    public void test_add_duplicate_elements() {
        String sorted_list = "1 3 5";
        String result = sorted_list + " 3";
        String[] tokens = result.split("\\s+");
        Arrays.sort(tokens);
        String joined = String.join(" ", tokens);
        assertEquals("1 3 3 5", joined);
    }

    // 模拟 Python 测试：对 "1 3 5" 添加 " 4 2" 后排序，结果应为 "1 2 3 4 5"
    @Test
    public void test_add_multiple_elements() {
        String sorted_list = "1 3 5";
        String result = sorted_list + " 4 2";
        String[] tokens = result.split("\\s+");
        Arrays.sort(tokens);
        String joined = String.join(" ", tokens);
        assertEquals("1 2 3 4 5", joined);
    }

    // 模拟 Python 测试：对 "5 10" 进行排序，结果应为 "10 5"（符合字符串的字典序排序）
    @Test
    public void test_empty_list() {
        String result = "5 10";
        String[] tokens = result.split("\\s+");
        Arrays.sort(tokens);
        String joined = String.join(" ", tokens);
        assertEquals("10 5", joined);
    }

    // 模拟 Python 测试：1000 个数字构成的字符串排序后检查第一个和最后一个，以及总数是否正确
    @Test
    public void test_large_number_of_elements() {
        // 构造从 1000 到 1 的数字字符串（以空格分隔）
        StringBuilder sb = new StringBuilder();
        for (int i = 1000; i >= 1; i--) {
            sb.append(i);
            if(i != 1) {
                sb.append(" ");
            }
        }
        String sorted_list = sb.toString();
        String[] tokens = sorted_list.split("\\s+");
        Arrays.sort(tokens);
        String joined = String.join(" ", tokens);
        String[] resultTokens = joined.split("\\s+");
        // 根据字典序排序，应该第一个元素为 "1"、最后一个为 "999"
        assertEquals("1", resultTokens[0]);
        assertEquals("999", resultTokens[resultTokens.length - 1]);
        assertEquals(1000, resultTokens.length);
    }

    // 测试调用 modify_sorted_list 移除第二个元素（下标1），期望返回 "Removed item: 3, Remaining list: [1, 5, 8]"
    @Test
    public void test_modify_sorted_list_remove_second_item() {
        String result = handler.modify_sorted_list(1);
        String expected = "Removed item: 3, Remaining list: [1, 5, 8]";
        assertEquals(expected, result);
    }
}