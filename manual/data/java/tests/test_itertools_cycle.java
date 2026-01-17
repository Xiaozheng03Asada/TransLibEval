package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class test_itertools_cycle {
    private function_itertools_cycle processor;

    @BeforeEach
    public void setUp() {
        processor = new function_itertools_cycle();
    }

    @Test
    public void test_cycle_example_empty_sequence() {
        String result = processor.test_cycle("", 5);
        assertEquals("", result);
    }

    @Test
    public void test_cycle_example_single_character() {
        String result = processor.test_cycle("A", 5);
        assertEquals("AAAAA", result);
    }

    @Test
    public void test_cycle_example_short_sequence() {
        String result = processor.test_cycle("AB", 5);
        assertEquals("ABABA", result);
    }

    @Test
    public void test_cycle_example_long_sequence() {
        String result = processor.test_cycle("ABCDEFG", 10);
        assertEquals("ABCDEFGABC", result);
    }

    @Test
    public void test_cycle_example_numeric_sequence() {
        String result = processor.test_cycle("123", 7);
        assertEquals("1231231", result);
    }
}