package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class test_pyphonetics_distance {
    private function_pyphonetics_distance processor;

    @BeforeEach
    public void setUp() {
        processor = new function_pyphonetics_distance();
    }

    @Test
    public void test_refined_soundex_distance_equal() {
        int result = processor.compute_distance("Rupert", "Robert", "refined");
        assertEquals(0, result);
    }

    @Test
    public void test_refined_soundex_distance_not_equal() {
        int result = processor.compute_distance("assign", "assist", "refined");
        assertEquals(2, result);
    }

    @Test
    public void test_hamming_distance_equal() {
        int result = processor.compute_distance("hello", "hxllo", "hamming");
        assertEquals(1, result);
    }

    @Test
    public void test_hamming_distance_not_equal() {
        int result = processor.compute_distance("katiang", "sitting", "hamming");
        assertEquals(1, result);
    }

    @Test
    public void test_invalid_metric() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            processor.compute_distance("hello", "hxllo", "invalid");
        });
        assertEquals("Invalid metric. Only 'refined' and 'hamming' are supported.", exception.getMessage());
    }
}