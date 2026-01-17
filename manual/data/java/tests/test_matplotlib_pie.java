package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_matplotlib_pie {
    private function_matplotlib_pie chartGenerator;

    @BeforeEach
    void setUp() {
        chartGenerator = new function_matplotlib_pie();
    }

    @Test
    void test_valid_input() {
        String result = chartGenerator.create_pie_chart("A,B,C", "30,40,30", "Pie Chart");
        assertEquals("Pie chart saved as 'pie_chart.png'.", result);
    }

    @Test
    void test_empty_input() {
        String result = chartGenerator.create_pie_chart("", "", "Pie Chart");
        assertEquals("Error: labels and sizes cannot be empty.", result);
    }

    @Test
    void test_mismatched_lengths() {
        String result = chartGenerator.create_pie_chart("A,B", "30", "Pie Chart");
        assertEquals("Error: labels and sizes must have the same length.", result);
    }

    @Test
    void test_non_string_labels() {
        String result = chartGenerator.create_pie_chart("1,B,C", "30,40,30", "Pie Chart");
        assertEquals("Pie chart saved as 'pie_chart.png'.", result);
    }

    @Test
    void test_negative_size() {
        String result = chartGenerator.create_pie_chart("A,B,C", "30,-40,30", "Pie Chart");
        assertEquals("Error: All sizes must be non-negative numbers.", result);
    }
}