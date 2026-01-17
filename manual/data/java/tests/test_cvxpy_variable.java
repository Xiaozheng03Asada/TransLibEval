package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_cvxpy_variable {
    @Test
    public void test_variable_value_assignment() {
        function_cvxpy_variable func = new function_cvxpy_variable();
        String result = func.process_variables("5", "10", null);
        assertEquals("x: 5.0, y: 10.0", result);

        result = func.process_variables("0", "0", null);
        assertEquals("x: 0.0, y: 0.0", result);
    }

    @Test
    public void test_unassigned_variable() {
        function_cvxpy_variable func = new function_cvxpy_variable();
        String result = func.process_variables(null, null, null);
        assertNotNull(result);
        String[] parts = result.split(",");
        assertEquals(2, parts.length);
        assertNotNull(parts[0]);
        assertNotNull(parts[1]);
    }

    @Test
    public void test_variable_negative_size() {
        function_cvxpy_variable func = new function_cvxpy_variable();
        String result = func.process_variables("-1", "10", null);
        assertEquals("Error: Variable size cannot be negative", result);
    }

    @Test
    public void test_invalid_variable_constraint() {
        function_cvxpy_variable func = new function_cvxpy_variable();
        String result = func.process_variables(null, null, "string_value");
        assertEquals("Error: Invalid variable constraint", result);
    }

    @Test
    public void test_invalid_input() {
        function_cvxpy_variable func = new function_cvxpy_variable();
        String result = func.process_variables("abc", "xyz", null);
        assertEquals("Error: Invalid value for x or y", result);
    }
}