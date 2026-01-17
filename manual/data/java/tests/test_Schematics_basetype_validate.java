package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class test_Schematics_basetype_validate {

    @Test
    public void test_valid_positive_integer() {
        function_Schematics_basetype_validate instance = new function_Schematics_basetype_validate();
        String input_value = "5";
        String result = instance.validate(input_value);
        assertEquals("Validation successful.", result);
    }

    @Test
    public void test_invalid_non_integer() {
        function_Schematics_basetype_validate instance = new function_Schematics_basetype_validate();
        String result = instance.validate("not_an_integer");
        assertEquals("Value must be an integer.", result);
    }

    @Test
    public void test_invalid_negative_integer() {
        function_Schematics_basetype_validate instance = new function_Schematics_basetype_validate();
        String result = instance.validate("-10");
        assertEquals("Value must be a positive integer.", result);
    }

    @Test
    public void test_invalid_float() {
        function_Schematics_basetype_validate instance = new function_Schematics_basetype_validate();
        String result = instance.validate("10.5");
        assertEquals("Value must be an integer.", result);
    }

    @Test
    public void test_valid_integer_type_conversion() {
        function_Schematics_basetype_validate instance = new function_Schematics_basetype_validate();
        String input_value = "10";  // 输入为字符串
        String result = instance.validate(input_value);
        assertEquals("Validation successful.", result);
    }
}