package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_sortedcontainers_SortedDict_popitem {
    private function_sortedcontainers_SortedDict_popitem handler;

    @BeforeEach
    public void setUp() {
        handler = new function_sortedcontainers_SortedDict_popitem();
    }

    @Test
    public void test_pop_last_item() {
        String result = handler.modify_sorted_dict(-1);
        assertEquals("{1: 'one', 3: 'three'} (5, 'five')", result);
    }

    @Test
    public void test_pop_first_item() {
        String result = handler.modify_sorted_dict(0);
        assertEquals("{3: 'three', 5: 'five'} (1, 'one')", result);
    }

    @Test
    public void test_pop_invalid_index() {
        String result = handler.modify_sorted_dict(5);
        assertEquals("error: Invalid index", result);
    }

    @Test
    public void test_pop_middle_item() {
        String result = handler.modify_sorted_dict(1);
        assertEquals("{1: 'one', 5: 'five'} (3, 'three')", result);
    }

    @Test
    public void test_pop_all_items() {
        String result = handler.modify_sorted_dict(0);
        assertEquals("{3: 'three', 5: 'five'} (1, 'one')", result);
    }
}