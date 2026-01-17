package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_Schema_Tuple {

    @Test
    public void test_valid_tuple() {
        function_Schema_Tuple validator = new function_Schema_Tuple();
        String result = validator.validate("42, hello");
        assertEquals("Valid tuple: (42, hello)", result);
    }

    @Test
    public void test_invalid_second_element() {
        function_Schema_Tuple validator = new function_Schema_Tuple();
        String result = validator.validate("42, 100");
        assertEquals("Valid tuple: (42, 100)", result);
    }

    @Test
    public void test_valid_other_tuple() {
        function_Schema_Tuple validator = new function_Schema_Tuple();
        String result = validator.validate("100, world");
        assertEquals("Valid tuple: (100, world)", result);
    }

    @Test
    public void test_invalid_first_element() {
        function_Schema_Tuple validator = new function_Schema_Tuple();
        String result = validator.validate("hello, 42");
        assertEquals("Invalid input: First value must be an integer", result);
    }

    @Test
    public void test_not_a_pair() {
        function_Schema_Tuple validator = new function_Schema_Tuple();
        String result = validator.validate("42");
        assertEquals("Invalid input: Must be a pair of values", result);
    }
}