package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_algorithms_sort_quick_sort {
    private function_algorithms_sort_quick_sort function;

    @BeforeEach
    void setUp() {
        function = new function_algorithms_sort_quick_sort();
    }

    @Test
    void test_normal_list() {
        String dataStr = "4,2,9,1,7";
        String expected = "1,2,4,7,9";
        assertEquals(expected, function.quick_sort_from_string(dataStr));
    }

    @Test
    void test_already_sorted() {
        String dataStr = "1,2,3,4,5";
        String expected = "1,2,3,4,5";
        assertEquals(expected, function.quick_sort_from_string(dataStr));
    }

    @Test
    void test_reverse_sorted() {
        String dataStr = "5,4,3,2,1";
        String expected = "1,2,3,4,5";
        assertEquals(expected, function.quick_sort_from_string(dataStr));
    }

    @Test
    void test_empty_list() {
        String dataStr = "";
        String expected = "";
        assertEquals(expected, function.quick_sort_from_string(dataStr));
    }

    @Test
    void test_single_element() {
        String dataStr = "42";
        String expected = "42";
        assertEquals(expected, function.quick_sort_from_string(dataStr));
    }
}