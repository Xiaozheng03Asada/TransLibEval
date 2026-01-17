package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_funcy_merge {
    private function_funcy_merge merger;

    @BeforeEach
    void setUp() {
        merger = new function_funcy_merge();
    }

    @Test
    void testCombineDicts() {
        String result = merger.combine_dicts("{'a': 1, 'b': 2}", "{'b': 3, 'c': 4}");
        assertEquals("{'a':1,'b':3,'c':4}", result);
    }

    @Test
    void testOverridingKeys() {
        String result = merger.combine_dicts("{'a': 1, 'b': 2}", "{'a': 3, 'b': 4, 'c': 5}");
        assertEquals("{'a':3,'b':4,'c':5}", result);
    }

    @Test
    void testEmptyDict() {
        String result = merger.combine_dicts("{}", "{'a': 1, 'b': 2}");
        assertEquals("{'a':1,'b':2}", result);
    }

    @Test
    void testMultipleOverriding() {
        String dict1 = "{'a': 1, 'b': 2}";
        String dict2 = "{'a': 3, 'b': 4}";
        String dict3 = "{'a': 5, 'd': 6}";
        String result = merger.combine_dicts(dict1, merger.combine_dicts(dict2, dict3));
        assertEquals("{'a':5,'b':4,'d':6}", result);
    }

    @Test
    void testInvalidInput() {
        String result = merger.combine_dicts("12345", "{'a': 1, 'b': 2}");
        assertEquals("Error: input is not a dictionary", result);
    }
}