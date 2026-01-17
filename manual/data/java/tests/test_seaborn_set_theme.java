package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_seaborn_set_theme {

    @Test
    public void test_default_theme() {
        function_seaborn_set_theme themeSetter = new function_seaborn_set_theme();
        String result = themeSetter.set_theme("darkgrid", "notebook", "deep");
        assertEquals("{'style': 'darkgrid', 'context': 'notebook', 'palette': 'deep'}", result);
    }

    @Test
    public void test_custom_theme() {
        function_seaborn_set_theme themeSetter = new function_seaborn_set_theme();
        String result = themeSetter.set_theme("white", "talk", "muted");
        assertEquals("{'style': 'white', 'context': 'talk', 'palette': 'muted'}", result);
    }

    @Test
    public void test_empty_style() {
        function_seaborn_set_theme themeSetter = new function_seaborn_set_theme();
        String result = themeSetter.set_theme("", "notebook", "deep");
        assertEquals("{'style': 'darkgrid', 'context': 'notebook', 'palette': 'deep'}", result);
    }

    @Test
    public void test_empty_context() {
        function_seaborn_set_theme themeSetter = new function_seaborn_set_theme();
        String result = themeSetter.set_theme("darkgrid", "", "deep");
        assertEquals("{'style': 'darkgrid', 'context': 'notebook', 'palette': 'deep'}", result);
    }

    @Test
    public void test_empty_palette() {
        function_seaborn_set_theme themeSetter = new function_seaborn_set_theme();
        String result = themeSetter.set_theme("darkgrid", "notebook", "");
        assertEquals("{'style': 'darkgrid', 'context': 'notebook', 'palette': 'deep'}", result);
    }
}