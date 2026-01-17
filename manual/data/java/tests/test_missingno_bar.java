package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class test_missingno_bar {

    private function_missingno_bar missingnoBar;

    @BeforeEach
    public void setUp() {
        missingnoBar = new function_missingno_bar();
    }

    @Test
    public void test_complete_data() {
        String csv_data = "A,B,C\n1,2,3\n4,5,6\n7,8,9";
        String result = missingnoBar.visualize_missing_data(csv_data);
        assertEquals("Missing data visualization generated.", result);
    }

    @Test
    public void test_missing_values() {
        String csv_data = "A,B,C\n1,2,\n,5,6\n7,,9";
        String result = missingnoBar.visualize_missing_data(csv_data);
        assertEquals("Missing data visualization generated.", result);
    }

    @Test
    public void test_all_missing() {
        String csv_data = "A,B,C\n,,\n,,\n,,";
        String result = missingnoBar.visualize_missing_data(csv_data);
        assertEquals("Missing data visualization generated.", result);
    }

    @Test
    public void test_empty_csv() {
        String csv_data = "";
        assertThrows(IllegalArgumentException.class, () -> {
            missingnoBar.visualize_missing_data(csv_data);
        });
    }

    @Test
    public void test_incorrect_column_count() {
        String csv_data = "A,B,C\n1,2\n4,5,6,7\n8,9";
        assertThrows(IllegalArgumentException.class, () -> {
            missingnoBar.visualize_missing_data(csv_data);
        });
    }
}