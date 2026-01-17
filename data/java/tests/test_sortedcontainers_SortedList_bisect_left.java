package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class test_sortedcontainers_SortedList_bisect_left {

    @Test
    public void test_value_in_middle() {
        function_sortedcontainers_SortedList_bisect_left instance = new function_sortedcontainers_SortedList_bisect_left();
        int position = instance.find_insert_position(4, null);
        assertEquals(2, position);
    }

    @Test
    public void test_value_at_start() {
        function_sortedcontainers_SortedList_bisect_left instance = new function_sortedcontainers_SortedList_bisect_left();
        int position = instance.find_insert_position(0, null);
        assertEquals(0, position);
    }

    @Test
    public void test_value_at_end() {
        function_sortedcontainers_SortedList_bisect_left instance = new function_sortedcontainers_SortedList_bisect_left();
        int position = instance.find_insert_position(10, null);
        assertEquals(4, position);
    }

    @Test
    public void test_existing_value() {
        function_sortedcontainers_SortedList_bisect_left instance = new function_sortedcontainers_SortedList_bisect_left();
        int position = instance.find_insert_position(3, null);
        assertEquals(1, position);
    }

    @Test
    public void test_insert_into_empty_list() {
        function_sortedcontainers_SortedList_bisect_left instance = new function_sortedcontainers_SortedList_bisect_left();
        int position = instance.find_insert_position(5, "5");
        assertEquals(0, position);
    }
}