// 文件路径：src/test/java/com/example/test_algorithms_binary_search.java
package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_algorithms_binary_search {
    private function_algorithms_binary_search function;

    @BeforeEach
    void setUp() {
        function = new function_algorithms_binary_search();
    }

    @Test
    void test_target_present_in_string() {
        assertEquals(2, function.binary_search_index("246810", "6"));
    }

    @Test
    void test_target_absent_in_string() {
        assertEquals(-1, function.binary_search_index("246810", "5"));
    }

    @Test
    void test_empty_string() {
        assertEquals(-1, function.binary_search_index("", "1"));
    }

    @Test
    void test_single_character_found() {
        assertEquals(0, function.binary_search_index("7", "7"));
    }

    @Test
    void test_single_character_not_found() {
        assertEquals(-1, function.binary_search_index("7", "3"));
    }
}