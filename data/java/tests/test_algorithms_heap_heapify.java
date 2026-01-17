package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_algorithms_heap_heapify {
    private function_algorithms_heap_heapify function;

    @BeforeEach
    void setUp() {
        function = new function_algorithms_heap_heapify();
    }

    @Test
    void test_standard_case() {
        String arr_str = "5,3,8,4,2,7,1,6";
        String heap = function.heapify_from_string(arr_str);

        // 将输出字符串转换回数组以验证堆属性
        String[] heapArray = heap.split(",");
        int[] heapList = new int[heapArray.length];
        for (int i = 0; i < heapArray.length; i++) {
            heapList[i] = Integer.parseInt(heapArray[i]);
        }

        // 检查堆的根是最小的数
        assertEquals(1, heapList[0]);

        // 验证堆属性
        for (int i = 0; i < heapList.length; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < heapList.length) {
                assertTrue(heapList[i] <= heapList[left]);
            }
            if (right < heapList.length) {
                assertTrue(heapList[i] <= heapList[right]);
            }
        }
    }

    @Test
    void test_single_element() {
        String arr_str = "10";
        assertEquals("10", function.heapify_from_string(arr_str));
    }

    @Test
    void test_sorted_array() {
        String arr_str = "1,2,3,4,5";
        assertEquals("1,2,3,4,5", function.heapify_from_string(arr_str));
    }

    @Test
    void test_all_identical_elements() {
        String arr_str = "7,7,7,7,7";
        assertEquals("7,7,7,7,7", function.heapify_from_string(arr_str));
    }

    @Test
    void test_large_numbers_with_negatives() {
        String arr_str = "1000000,-5000000,3000000,-2000000,4000000";
        String expected_result = "-5000000,-2000000,3000000,1000000,4000000";
        assertEquals(expected_result, function.heapify_from_string(arr_str));
    }
}