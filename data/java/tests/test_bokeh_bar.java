package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_bokeh_bar {
    @Test
    public void test_bar_chart_creation() {
        function_bokeh_bar plotter = new function_bokeh_bar();
        String result = plotter.create_bar_chart("[\"A\", \"B\", \"C\"]", "[4, 5, 6]", null);
        assertEquals("Plot displayed.", result);
    }

    @Test
    public void test_empty_input() {
        function_bokeh_bar plotter = new function_bokeh_bar();
        String result = plotter.create_bar_chart("[]", "[]", null);
        assertEquals("Error: Invalid input format.", result);
    }

    @Test
    public void test_invalid_length() {
        function_bokeh_bar plotter = new function_bokeh_bar();
        String result = plotter.create_bar_chart("[1, 2]", "[3]", null);
        assertEquals("Error: x_values and y_values must have the same length.", result);
    }

    @Test
    public void test_custom_title() {
        function_bokeh_bar plotter = new function_bokeh_bar();
        String result = plotter.create_bar_chart("[\"A\", \"B\", \"C\"]", "[4, 5, 6]", "Custom Bar Chart Title");
        assertEquals("Plot displayed.", result);
    }

    @Test
    public void test_negative_values() {
        function_bokeh_bar plotter = new function_bokeh_bar();
        String result = plotter.create_bar_chart("[\"A\", \"B\", \"C\"]", "[-4, -5, -6]", null);
        assertEquals("Plot displayed.", result);
    }
}