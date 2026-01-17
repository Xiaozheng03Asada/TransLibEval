package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_funcy_flatten {
    function_funcy_flatten processor;

    @BeforeEach
    void setUp() {
        processor = new function_funcy_flatten();
    }

    @Test
    void testFlattenSimpleList() {
        String result = processor.process_list("[1, [2, 3], [4, 5], 6]");
        assertEquals("[1, 2, 3, 4, 5, 6]", result);
    }

    @Test
    void testFlattenEmptyElements() {
        String result = processor.process_list("[[], [], [1, 2], [], [3, 4]]");
        assertEquals("[1, 2, 3, 4]", result);
    }

    @Test
    void testComplexNestedStructure() {
        String result = processor.process_list("[1, [2, 3, [4, 5]], [6, [7, 8]], 9]");
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9]", result);
    }

    @Test
    void testFlattenWithMixedDataTypes() {
        String result = processor.process_list("[1, \"apple\", [3.14, \"banana\"], [2, \"orange\"], [[true, false]]]");
        assertEquals("[1, \"apple\", 3.14, \"banana\", 2, \"orange\", true, false]", result);
    }

    @Test
    void testFlattenNonIterable() {
        String result = processor.process_list("12345");
        assertEquals("Error: input is not iterable", result);
    }
}