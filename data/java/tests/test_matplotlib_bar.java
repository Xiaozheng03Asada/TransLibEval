package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_matplotlib_bar {
    private final function_matplotlib_bar barPlot = new function_matplotlib_bar();

    @Test
    void test_valid_input() {
        String result = barPlot.create_bar_plot("A,B,C", "10,20,30");
        assertEquals("Plot saved as 'bar_plot.png'.", result);
    }

    @Test
    void test_empty_input() {
        String result = barPlot.create_bar_plot("", "");
        assertEquals("Error: Categories and values cannot be empty.", result);
    }

    @Test
    void test_mismatched_lengths() {
        String result = barPlot.create_bar_plot("A,B", "10");
        assertEquals("Error: Categories and values must have the same length.", result);
    }

    @Test
    void test_non_string_categories() {
        String result = barPlot.create_bar_plot("1,2,3", "10,20,30");
        assertEquals("Plot saved as 'bar_plot.png'.", result);
    }

    @Test
    void test_non_numeric_values() {
        String result = barPlot.create_bar_plot("A,B,C", "10,'20',30");
        assertEquals("Plot saved as 'bar_plot.png'.", result);
    }
}