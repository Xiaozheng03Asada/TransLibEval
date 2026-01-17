package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_networkx_connected_components {

    @Test
    public void test_success() {
        String result = new function_networkx_connected_components()
                .find_connected_components(5, 4, "A-B, B-C, D-E, E-F");
        assertTrue(result.startsWith("success"));
        assertTrue(result.contains("2 components"));
    }

    @Test
    public void test_single_component() {
        String result = new function_networkx_connected_components()
                .find_connected_components(3, 3, "A-B, B-C, A-C");
        assertTrue(result.startsWith("success"));
        assertTrue(result.contains("1 components"));
    }

    @Test
    public void test_no_edges() {
        String result = new function_networkx_connected_components()
                .find_connected_components(5, 0, "");
        assertTrue(result.startsWith("success"));
        assertTrue(result.contains("5 components"));
    }

    @Test
    public void test_single_node() {
        String result = new function_networkx_connected_components()
                .find_connected_components(1, 0, "");
        assertTrue(result.startsWith("success"));
        assertTrue(result.contains("1 components"));
    }

    @Test
    public void test_partial_connection() {
        String result = new function_networkx_connected_components()
                .find_connected_components(5, 3, "A-B, B-C, D-E");
        assertTrue(result.startsWith("success"));
        assertTrue(result.contains("2 components"));
    }
}