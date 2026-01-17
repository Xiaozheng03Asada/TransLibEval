package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_bokeh_line {

    @Test
    public void test_line_plot_creation() {
        function_bokeh_line plotter = new function_bokeh_line();
        String result = plotter.create_line_plot("[1, 2, 3]", "[4, 5, 6]", "Line Plot");
        assertEquals("Plot displayed.", result);
    }

    @Test
    public void test_empty_input() {
        function_bokeh_line plotter = new function_bokeh_line();
        String result = plotter.create_line_plot("[]", "[]", "Line Plot");
        assertEquals("Error: Invalid input format.", result);
    }

    @Test
    public void test_invalid_length() {
        function_bokeh_line plotter = new function_bokeh_line();
        String result = plotter.create_line_plot("[1, 2]", "[3]", "Line Plot");
        assertEquals("Error: x_values and y_values must have the same length.", result);
    }

    @Test
    public void test_custom_title() {
        function_bokeh_line plotter = new function_bokeh_line();
        String result = plotter.create_line_plot("[1, 2, 3]", "[4, 5, 6]", "Custom Line Plot Title");
        assertEquals("Plot displayed.", result);
    }

    @Test
    public void test_negative_values() {
        function_bokeh_line plotter = new function_bokeh_line();
        String result = plotter.create_line_plot("[-1, -2, -3]", "[-4, -5, -6]", "Line Plot");
        assertEquals("Plot displayed.", result);
    }
}