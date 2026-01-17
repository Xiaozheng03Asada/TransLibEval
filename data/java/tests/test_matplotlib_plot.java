package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_matplotlib_plot {

    @Test
    void test_valid_input() {
        function_matplotlib_plot plotGenerator = new function_matplotlib_plot();
        String result = plotGenerator.create_bar_plot("A,B,C", "10,20,30");
        assertEquals("Plot saved as 'bar_plot.png'.", result);
    }

    @Test
    void test_empty_input() {
        function_matplotlib_plot plotGenerator = new function_matplotlib_plot();
        String result = plotGenerator.create_bar_plot("", "");
        assertEquals("Error: Categories and values cannot be empty.", result);
    }

    @Test
    void test_mismatched_lengths() {
        function_matplotlib_plot plotGenerator = new function_matplotlib_plot();
        String result = plotGenerator.create_bar_plot("A,B", "10");
        assertEquals("Error: Categories and values must have the same length.", result);
    }

    @Test
    void test_non_string_categories() {
        function_matplotlib_plot plotGenerator = new function_matplotlib_plot();
        String result = plotGenerator.create_bar_plot("1,2,3", "10,20,30");
        assertEquals("Plot saved as 'bar_plot.png'.", result);
    }

    @Test
    void test_non_numeric_values() {
        function_matplotlib_plot plotGenerator = new function_matplotlib_plot();
        String result = plotGenerator.create_bar_plot("A,B,C", "10,'20',30");
        assertEquals("Error: All values must be numbers.", result);
    }
}