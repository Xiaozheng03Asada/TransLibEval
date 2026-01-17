package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_funcy_take {
    private function_funcy_take processor;

    @BeforeEach
    void setUp() {
        processor = new function_funcy_take();
    }

    @Test
    void test_get_first_n_elements() {
        String result = processor.get_first_n_elements("[1, 2, 3, 4, 5]", 3);
        assertEquals("[1, 2, 3]", result);
    }

    @Test
    void test_more_elements_than_list() {
        String result = processor.get_first_n_elements("[1, 2]", 5);
        assertEquals("[1, 2]", result);
    }

    @Test
    void test_take_large_n() {
        String result = processor.get_first_n_elements("[1, 2, 3]", 100);
        assertEquals("[1, 2, 3]", result);
    }

    @Test
    void test_take_with_strings() {
        String result = processor.get_first_n_elements("[\"apple\", \"banana\", \"cherry\", \"date\"]", 2);
        assertEquals("[apple, banana]", result);
    }

    @Test
    void test_invalid_input() {
        String result = processor.get_first_n_elements("12345", 3);
        assertEquals("Error: invalid input", result);
    }
}