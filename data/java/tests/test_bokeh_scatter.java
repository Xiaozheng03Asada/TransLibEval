package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_bokeh_scatter {

    @Test
    void testScatterPlotCreation() {
        function_bokeh_scatter plotter = new function_bokeh_scatter();
        String result = plotter.create_scatter_plot("[1, 2, 3]", "[4, 5, 6]", null);
        assertEquals("Plot displayed.", result);
    }

    @Test
    void testEmptyInput() {
        function_bokeh_scatter plotter = new function_bokeh_scatter();
        String result = plotter.create_scatter_plot("[]", "[]", null);
        assertEquals("Error: Invalid input format.", result);
    }

    @Test
    void testInvalidLength() {
        function_bokeh_scatter plotter = new function_bokeh_scatter();
        String result = plotter.create_scatter_plot("[1, 2]", "[3]", null);
        assertEquals("Error: x_values and y_values must have the same length.", result);
    }

    @Test
    void testCustomTitle() {
        function_bokeh_scatter plotter = new function_bokeh_scatter();
        String result = plotter.create_scatter_plot("[1, 2, 3]", "[4, 5, 6]", "Custom Scatter Plot Title");
        assertEquals("Plot displayed.", result);
    }

    @Test
    void testNegativeValues() {
        function_bokeh_scatter plotter = new function_bokeh_scatter();
        String result = plotter.create_scatter_plot("[-1, -2, -3]", "[-4, -5, -6]", null);
        assertEquals("Plot displayed.", result);
    }
}