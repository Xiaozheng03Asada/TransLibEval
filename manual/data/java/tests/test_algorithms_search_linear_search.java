package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_algorithms_search_linear_search {
    private function_algorithms_search_linear_search function;

    @BeforeEach
    void setUp() {
        function = new function_algorithms_search_linear_search();
    }

    @Test
    void test_element_found() {
        String arrStr = "10,20,30,40,50";
        String targetStr = "30";
        assertEquals(2, function.linear_search_from_string(arrStr, targetStr));
    }

    @Test
    void test_element_not_found() {
        String arrStr = "10,20,30,40,50";
        String targetStr = "60";
        assertEquals(-1, function.linear_search_from_string(arrStr, targetStr));
    }

    @Test
    void test_empty_array() {
        String arrStr = "";
        String targetStr = "30";
        assertEquals(-1, function.linear_search_from_string(arrStr, targetStr));
    }

    @Test
    void test_first_element() {
        String arrStr = "10,20,30,40,50";
        String targetStr = "10";
        assertEquals(0, function.linear_search_from_string(arrStr, targetStr));
    }

    @Test
    void test_last_element() {
        String arrStr = "10,20,30,40,50";
        String targetStr = "50";
        assertEquals(4, function.linear_search_from_string(arrStr, targetStr));
    }
}