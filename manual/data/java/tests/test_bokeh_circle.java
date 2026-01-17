package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_bokeh_circle {
    @Test
    public void test_circle_plot_creation() {
        function_bokeh_circle plotter = new function_bokeh_circle();
        String result = plotter.create_circle_plot("[1, 2, 3]", "[4, 5, 6]", 10, "Circle Plot");
        assertEquals("Plot displayed.", result);
    }

    @Test
    public void test_empty_input() {
        function_bokeh_circle plotter = new function_bokeh_circle();
        String result = plotter.create_circle_plot("[]", "[]", 10, "Circle Plot");
        assertEquals("Error: Invalid input format.", result);
    }

    @Test
    public void test_invalid_length() {
        function_bokeh_circle plotter = new function_bokeh_circle();
        String result = plotter.create_circle_plot("[1, 2]", "[3]", 10, "Circle Plot");
        assertEquals("Error: x_values and y_values must have the same length.", result);
    }

    @Test
    public void test_custom_title() {
        function_bokeh_circle plotter = new function_bokeh_circle();
        String result = plotter.create_circle_plot("[1, 2, 3]", "[4, 5, 6]", 10, "Custom Circle Plot Title");
        assertEquals("Plot displayed.", result);
    }

    @Test
    public void test_custom_radius() {
        function_bokeh_circle plotter = new function_bokeh_circle();
        String result = plotter.create_circle_plot("[1, 2, 3]", "[4, 5, 6]", 15, "Circle Plot");
        assertEquals("Plot displayed.", result);
    }
}