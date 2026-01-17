package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_Schematics_model_to_native {

    @Test
    public void test_valid_model() {
        String data = "John Doe,30";
        String expected = "John Doe 30 Unknown";
        function_Schematics_model_to_native instance = new function_Schematics_model_to_native();
        assertEquals(expected, instance.get_native_representation(data));
    }

    @Test
    public void test_missing_name() {
        String data = "25";
        function_Schematics_model_to_native instance = new function_Schematics_model_to_native();
        String result = instance.get_native_representation(data);
        assertEquals("error: Invalid input format", result);
    }

    @Test
    public void test_negative_age() {
        String data = "John Doe,-5";
        function_Schematics_model_to_native instance = new function_Schematics_model_to_native();
        String result = instance.get_native_representation(data);
        assertTrue(result.startsWith("error"));
    }

    @Test
    public void test_default_city() {
        String data = "Jane Smith,22";
        function_Schematics_model_to_native instance = new function_Schematics_model_to_native();
        String result = instance.get_native_representation(data);
        assertTrue(result.contains("Unknown"));
    }

    @Test
    public void test_all_fields_provided() {
        String data = "Alice,40";
        String expected = "Alice 40 Unknown";
        function_Schematics_model_to_native instance = new function_Schematics_model_to_native();
        assertEquals(expected, instance.get_native_representation(data));
    }
}