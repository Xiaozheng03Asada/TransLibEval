package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_seaborn_cubehelix_palette {

    @Test
    public void test_default_cubehelix() {
        function_seaborn_cubehelix_palette palette = new function_seaborn_cubehelix_palette();
        String result = palette.generate_cubehelix(6, 0.5, -1.5, 1.0);
        assertTrue(result.startsWith("[") && result.endsWith("]"));
        assertEquals(6, result.split("\\(").length - 1);
        assertTrue(result.contains("("));
        assertTrue(result.contains(")"));
    }

    @Test
    public void test_custom_start_rotation() {
        function_seaborn_cubehelix_palette palette = new function_seaborn_cubehelix_palette();
        String result = palette.generate_cubehelix(4, 1.0, 0.5, 1.0);
        assertEquals(4, result.split("\\(").length - 1);
    }

    @Test
    public void test_gamma_correction() {
        function_seaborn_cubehelix_palette palette = new function_seaborn_cubehelix_palette();
        String result = palette.generate_cubehelix(3, 0.5, -1.5, 2.0);
        assertEquals(3, result.split("\\(").length - 1);
    }

    @Test
    public void test_large_palette() {
        function_seaborn_cubehelix_palette palette = new function_seaborn_cubehelix_palette();
        String result = palette.generate_cubehelix(12, 0.5, -1.5, 1.0);
        assertEquals(12, result.split("\\(").length - 1);
    }

    @Test
    public void test_empty_palette() {
        function_seaborn_cubehelix_palette palette = new function_seaborn_cubehelix_palette();
        String result = palette.generate_cubehelix(0, 0.5, -1.5, 1.0);
        assertEquals("[]", result);
    }
}