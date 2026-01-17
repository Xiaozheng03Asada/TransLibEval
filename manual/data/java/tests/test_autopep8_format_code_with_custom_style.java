package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class test_autopep8_format_code_with_custom_style {

    @Test
    public void test_format_code_basic() {
        function_autopep8_format_code_with_custom_style formatter = new function_autopep8_format_code_with_custom_style();
        String code = "def bad_function(x,y):print(x+y)";
        String expected_code = "def bad_function(x,y):print(x+y)";
        String formatted_code = formatter.format_code_with_custom_style(code);
        assertEquals(expected_code, formatted_code);
    }

    @Test
    public void test_format_code_multiple_arguments() {
        function_autopep8_format_code_with_custom_style formatter = new function_autopep8_format_code_with_custom_style();
        String code = "def multi_param_function(a,b,c,d):return a+b+c+d";
        String expected_code = "def multi_param_function(a,b,c,d):return a+b+c+d";
        String formatted_code = formatter.format_code_with_custom_style(code);
        assertEquals(expected_code, formatted_code);
    }

    @Test
    public void test_format_code_multiline() {
        function_autopep8_format_code_with_custom_style formatter = new function_autopep8_format_code_with_custom_style();
        String code = "def long_function():\n    a=1\n    b=2\n    return a+b";
        String expected_code = "def long_function():\n    a=1\n    b=2\n    return a+b";
        String formatted_code = formatter.format_code_with_custom_style(code);
        assertEquals(expected_code, formatted_code);
    }

    @Test
    public void test_format_code_already_correct() {
        function_autopep8_format_code_with_custom_style formatter = new function_autopep8_format_code_with_custom_style();
        String code = "def already_correct_function(a, b): return a + b";
        String expected_code = "def already_correct_function(a, b): return a + b";
        String formatted_code = formatter.format_code_with_custom_style(code);
        assertEquals(expected_code, formatted_code);
    }

    @Test
    public void test_format_code_indentation() {
        function_autopep8_format_code_with_custom_style formatter = new function_autopep8_format_code_with_custom_style();
        String code = "def function():\na=1\n    b=2\n    return a+b";
        String expected_code = "def function():\na=1\n    b=2\n    return a+b";
        String formatted_code = formatter.format_code_with_custom_style(code);
        assertEquals(expected_code, formatted_code);
    }
}