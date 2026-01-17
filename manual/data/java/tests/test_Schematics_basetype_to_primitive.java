package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_Schematics_basetype_to_primitive {

    @Test
    public void test_valid_integer_list() {
        String input_value = "[1, 2, 3]";
        String expected = "[1, 2, 3]";
        function_Schematics_basetype_to_primitive field = new function_Schematics_basetype_to_primitive();
        assertEquals(expected, field.to_primitive(input_value));
    }

    @Test
    public void test_invalid_non_list_input() {
        function_Schematics_basetype_to_primitive field = new function_Schematics_basetype_to_primitive();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            field.to_primitive("not_a_list");
        });
        String expectedMessage = "Value must be a string representing a list.";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void test_empty_list() {
        String input_value = "[]";
        String expected = "[]";
        function_Schematics_basetype_to_primitive field = new function_Schematics_basetype_to_primitive();
        assertEquals(expected, field.to_primitive(input_value));
    }

    @Test
    public void test_single_integer() {
        String input_value = "[5]";
        String expected = "[5]";
        function_Schematics_basetype_to_primitive field = new function_Schematics_basetype_to_primitive();
        assertEquals(expected, field.to_primitive(input_value));
    }

    @Test
    public void test_empty_list_string() {
        String input_value = "[]";
        String expected = "[]";
        function_Schematics_basetype_to_primitive field = new function_Schematics_basetype_to_primitive();
        assertEquals(expected, field.to_primitive(input_value));
    }
}