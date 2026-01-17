package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class test_sortedcontainers_SortedList_remove {

    // Test 1: Remove an existing element.
    // For the default list "5,3,8,1", removing 3 should return "1,5,8".
    @Test
    public void testRemoveElement() {
        function_sortedcontainers_SortedList_remove hanSeok = new function_sortedcontainers_SortedList_remove();
        String result = hanSeok.remove_element_from_list(3, null);
        assertEquals("1,5,8", result);
    }

    // Test 2: Try to remove an element that doesn't exist; should return "Value not found".
    @Test
    public void testRemoveElementNotFound() {
        function_sortedcontainers_SortedList_remove hanSeok = new function_sortedcontainers_SortedList_remove();
        String result = hanSeok.remove_element_from_list(10, null);
        assertEquals("Value not found", result);
    }

    // Test 3: With duplicate entries in the list: removing 3 from "1,3,3,5" should only remove one occurrence, resulting in "1,3,5".
    @Test
    public void testRemoveDuplicateElement() {
        function_sortedcontainers_SortedList_remove hanSeok = new function_sortedcontainers_SortedList_remove();
        String result = hanSeok.remove_element_from_list(3, "1,3,3,5");
        assertEquals("1,3,5", result);
    }

    // Test 4: Sequentially remove each element from "1,3,5,8" until all elements have been removed.
    @Test
    public void testRemoveAllElements() {
        function_sortedcontainers_SortedList_remove hanSeok = new function_sortedcontainers_SortedList_remove();
        String currentList = "1,3,5,8";
        for (String valStr : currentList.split(",")) {
            int val = Integer.parseInt(valStr.trim());
            currentList = hanSeok.remove_element_from_list(val, currentList);
        }
        assertEquals("", currentList);
    }

    // Test 5: When the list contains a single element, removing that element should result in an empty string.
    @Test
    public void testEmptyList() {
        function_sortedcontainers_SortedList_remove hanSeok = new function_sortedcontainers_SortedList_remove();
        String result = hanSeok.remove_element_from_list(1, "1");
        assertEquals("", result);
    }
}