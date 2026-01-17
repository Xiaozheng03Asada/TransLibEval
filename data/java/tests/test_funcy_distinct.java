package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class test_funcy_distinct {

    private function_funcy_distinct processor;

    @BeforeEach
    public void setUp() {
        processor = new function_funcy_distinct();
    }

    @Test
    public void test_remove_duplicates() {
        String result = processor.process_list("1,2,3,1,4,2,5");
        assertEquals("1,2,3,4,5", result);
    }

    @Test
    public void test_all_unique_elements() {
        String result = processor.process_list("1,2,3,4,5");
        assertEquals("1,2,3,4,5", result);
    }

    @Test
    public void test_mixed_types() {
        String result = processor.process_list("1,apple,1,banana,apple,3.14,3.14");
        assertEquals("1,apple,banana,3.14", result);
    }

    @Test
    public void test_mixed_case_strings() {
        String result = processor.process_list("apple,Apple,banana,apple,Banana");
        assertEquals("apple,Apple,banana,Banana", result);
    }

    @Test
    public void test_large_list_with_duplicates() {
        String input_str = String.join(",", java.util.Collections.nCopies(1000, "1")) + "," +
                String.join(",", java.util.Collections.nCopies(1000, "2")) + "," +
                String.join(",", java.util.Collections.nCopies(1000, "3"));
        String result = processor.process_list(input_str);
        assertEquals("1,2,3", result);
    }
}