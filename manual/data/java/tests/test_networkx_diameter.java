package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_networkx_diameter {
    @Test
    public void test_success() {
        String result = new function_networkx_diameter()
                .calculate_diameter("A-B,B-C,C-D,D-E");
        assertTrue(result.startsWith("success"));
    }

    @Test
    public void test_diameter_value() {
        String result = new function_networkx_diameter()
                .calculate_diameter("A-B,B-C,C-D,D-E");
        if (result.startsWith("success")) {
            int diameter = Integer.parseInt(result.split(":")[1].trim());
            assertEquals(4, diameter);
        }
    }

    @Test
    public void test_non_numeric() {
        String result = new function_networkx_diameter()
                .calculate_diameter("A-B,B-C,C-D,D-E");
        assertInstanceOf(String.class, result);
    }

    @Test
    public void test_disconnected_graph() {
        String result = new function_networkx_diameter()
                .calculate_diameter("A-B,C-D");
        assertEquals("failed", result);
    }

    @Test
    public void test_empty_graph() {
        String result = new function_networkx_diameter()
                .calculate_diameter("");
        assertEquals("failed", result);
    }
}