// 测试类路径：src/test/java/com/example/test_collections_counter.java
package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_collections_counter {
    @Test
    void test_count_single_element() {
        function_collections_counter calc = new function_collections_counter();
        assertEquals("apple:1", calc.count_elements("apple"));
    }

    @Test
    void test_count_multiple_elements() {
        function_collections_counter calc = new function_collections_counter();
        String expected = "apple:2, orange:1, banana:1";
        assertEquals(expected, calc.count_elements("apple orange apple banana"));
    }

    @Test
    void test_empty_input() {
        function_collections_counter calc = new function_collections_counter();
        assertEquals("failed", calc.count_elements(""));
    }

    @Test
    void test_no_repeat_elements() {
        function_collections_counter calc = new function_collections_counter();
        String expected = "apple:1, orange:1, banana:1";
        assertEquals(expected, calc.count_elements("apple orange banana"));
    }

    @Test
    void test_non_numeric() {
        function_collections_counter calc = new function_collections_counter();
        assertInstanceOf(String.class, calc.count_elements("a b c a b c"));
    }
}