package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class test_networkx_minimum_spanning_tree {

    @Test
    public void test_basic_mst() {
        function_networkx_minimum_spanning_tree calc = new function_networkx_minimum_spanning_tree();
        float result = calc.compute_minimum_spanning_tree("A", "B", 4.0f, "B", "C", 3.0f);
        assertEquals(7.0f, result, 0.0001);
    }

    @Test
    public void test_mst_multiple_edges() {
        function_networkx_minimum_spanning_tree calc = new function_networkx_minimum_spanning_tree();
        float result = calc.compute_minimum_spanning_tree("A", "B", 1.0f, "B", "C", 2.0f);
        assertEquals(3.0f, result, 0.0001);
    }

    @Test
    public void test_mst_no_edges() {
        function_networkx_minimum_spanning_tree calc = new function_networkx_minimum_spanning_tree();
        float result = calc.compute_minimum_spanning_tree("A", "B", 0.0f, "B", "C", 0.0f);
        assertEquals(0.0f, result, 0.0001);
    }

    @Test
    public void test_mst_single_edge() {
        function_networkx_minimum_spanning_tree calc = new function_networkx_minimum_spanning_tree();
        float result = calc.compute_minimum_spanning_tree("A", "B", 10.0f, null, null, null);
        assertEquals(10.0f, result, 0.0001);
    }

    @Test
    public void test_mst_disconnected_graph() {
        function_networkx_minimum_spanning_tree calc = new function_networkx_minimum_spanning_tree();
        float result = calc.compute_minimum_spanning_tree("A", "B", 5.0f, "C", "D", 7.0f);
        assertEquals(12.0f, result, 0.0001);
    }
}