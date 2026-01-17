package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_missingno_matrix {
    private function_missingno_matrix visualizer;

    @BeforeEach
    void setUp() {
        visualizer = new function_missingno_matrix();
    }

    @Test
    void test_no_missing_data() {
        String csv_data = "A,B,C\n1,2,3\n4,5,6\n7,8,9";
        String result = visualizer.visualize_missing_data(csv_data);
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }

    @Test
    void test_some_missing_data() {
        String csv_data = "A,B,C\n1,2,\n4,,6\n,8,9";
        String result = visualizer.visualize_missing_data(csv_data);
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }

    @Test
    void test_all_missing_column() {
        String csv_data = "A,B,C\n,,\n,,\n,,";
        String result = visualizer.visualize_missing_data(csv_data);
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }

    @Test
    void test_single_column() {
        String csv_data = "A\n1\n\n3\n4";
        String result = visualizer.visualize_missing_data(csv_data);
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }

    @Test
    void test_empty_csv() {
        String csv_data = "A,B,C\n";
        String result = visualizer.visualize_missing_data(csv_data);
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }
}