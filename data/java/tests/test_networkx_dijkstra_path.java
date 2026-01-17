package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_networkx_dijkstra_path {
    @Test
    void test_shortest_path() {
        function_networkx_dijkstra_path calc = new function_networkx_dijkstra_path();
        String result = calc.compute_shortest_path("A", "C", "A", "B", 1f, "B", "C", 1f);
        assertEquals("A -> B -> C", result);
    }

    @Test
    void test_no_path() {
        function_networkx_dijkstra_path calc = new function_networkx_dijkstra_path();
        String result = calc.compute_shortest_path("A", "D", "A", "B", 1f, "B", "C", 2f);
        assertEquals("no path", result);
    }

    @Test
    void test_single_edge() {
        function_networkx_dijkstra_path calc = new function_networkx_dijkstra_path();
        String result = calc.compute_shortest_path("A", "B", "A", "B", 5f, null, null, 0f);
        assertEquals("A -> B", result);
    }

    @Test
    void test_multiple_paths() {
        function_networkx_dijkstra_path calc = new function_networkx_dijkstra_path();
        String result = calc.compute_shortest_path("A", "C", "A", "B", 1f, "B", "C", 2f);
        assertEquals("A -> B -> C", result);
    }

    @Test
    void test_multiple_edges_with_different_weights() {
        function_networkx_dijkstra_path calc = new function_networkx_dijkstra_path();
        String result = calc.compute_shortest_path("A", "C", "A", "B", 5f, "B", "C", 1f);
        assertEquals("A -> B -> C", result);
    }
}