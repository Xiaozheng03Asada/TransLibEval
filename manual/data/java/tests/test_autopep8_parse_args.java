package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class test_autopep8_parse_args {

    @Test
    public void test_parse_args_basic() {
        String input_str = "-n Alice -a 30";
        function_autopep8_parse_args parser = new function_autopep8_parse_args();
        String result = parser.parse_arguments(input_str);
        assertEquals("Name: Alice, Age: 30, City: Not provided", result);
    }

    @Test
    public void test_parse_args_with_city() {
        String input_str = "-n Bob -a 25 -c New York";
        function_autopep8_parse_args parser = new function_autopep8_parse_args();
        String result = parser.parse_arguments(input_str);
        // 因为 "New York" 被拆分成 "New" 和 "York"，因此返回错误信息 "Error: 2"
        assertEquals("Error: 2", result);
    }

    @Test
    public void test_parse_args_without_city() {
        String input_str = "-n Charlie -a 40";
        function_autopep8_parse_args parser = new function_autopep8_parse_args();
        String result = parser.parse_arguments(input_str);
        assertEquals("Name: Charlie, Age: 40, City: Not provided", result);
    }

    @Test
    public void test_parse_args_missing_age() {
        String input_str = "-n Dave";
        function_autopep8_parse_args parser = new function_autopep8_parse_args();
        String result = parser.parse_arguments(input_str);
        assertTrue(result.contains("Error:"));
    }

    @Test
    public void test_parse_args_missing_name() {
        String input_str = "-a 50";
        function_autopep8_parse_args parser = new function_autopep8_parse_args();
        String result = parser.parse_arguments(input_str);
        assertTrue(result.contains("Error:"));
    }
}