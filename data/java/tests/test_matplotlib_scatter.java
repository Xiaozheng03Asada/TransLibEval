package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class test_matplotlib_scatter {
    @Test
    public void test_valid_input() {
        function_matplotlib_scatter generator = new function_matplotlib_scatter();
        String result = generator.create_scatter_plot("1,2,3", "4,5,6", "");
        assertEquals("Plot saved as 'scatter_plot.png'.", result);
    }

    @Test
    public void test_empty_input() {
        function_matplotlib_scatter generator = new function_matplotlib_scatter();
        String result = generator.create_scatter_plot("", "", "");
        assertEquals("Error: x_values and y_values cannot be empty.", result);
    }

    @Test
    public void test_mismatched_lengths() {
        function_matplotlib_scatter generator = new function_matplotlib_scatter();
        String result = generator.create_scatter_plot("1,2", "3", "");
        assertEquals("Error: x_values and y_values must have the same length.", result);
    }

    @Test
    public void test_non_numeric_values() {
        function_matplotlib_scatter generator = new function_matplotlib_scatter();
        String result = generator.create_scatter_plot("1,'2',3", "4,5,6", "");
        assertEquals("Error: All x and y values must be numbers.", result);
    }

    @Test
    public void test_large_input() {
        function_matplotlib_scatter generator = new function_matplotlib_scatter();
        String x_values = IntStream.range(0, 1000)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
        String y_values = IntStream.range(0, 1000)
                .map(i -> i * 2)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
        String result = generator.create_scatter_plot(x_values, y_values, "");
        assertEquals("Plot saved as 'scatter_plot.png'.", result);
    }
}