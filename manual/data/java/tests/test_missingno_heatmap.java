package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class test_missingno_heatmap {

    @Test
    void test_valid_csv() {
        String csv_data = "A,B,C\n1,2,3\n4,5,6\n7,8,9";
        function_missingno_heatmap obj = new function_missingno_heatmap();
        String result = obj.generate_heatmap(csv_data);
        assertEquals("Heatmap generated successfully", result);
    }

    @Test
    void test_empty_csv() {
        String csv_data = "";
        function_missingno_heatmap obj = new function_missingno_heatmap();
        String result = obj.generate_heatmap(csv_data);
        assertEquals("No data in the file", result);
    }

    @Test
    void test_missing_values() {
        String csv_data = "A,B,C\n1,2,\n4,,6\n,8,9";
        function_missingno_heatmap obj = new function_missingno_heatmap();
        String result = obj.generate_heatmap(csv_data);
        assertEquals("Heatmap generated successfully", result);
    }

    @Test
    void test_other_error() {
        String csv_data = null;
        function_missingno_heatmap obj = new function_missingno_heatmap();
        String result = obj.generate_heatmap(csv_data);
        assertTrue(result.contains("An error occurred"));
    }

    @Test
    void test_single_column_csv() {
        String csv_data = "A\n1\n2\n3\n4\n5";
        function_missingno_heatmap obj = new function_missingno_heatmap();
        String result = obj.generate_heatmap(csv_data);
        assertEquals("Heatmap generated successfully", result);
    }
}