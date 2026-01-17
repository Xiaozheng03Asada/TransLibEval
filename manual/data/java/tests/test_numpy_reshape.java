package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_numpy_reshape {

    @Test
    void testValidInput() {
        function_numpy_reshape calculator = new function_numpy_reshape();
        String result = calculator.reshape(10.0, 5.0);
        assertTrue(result.contains("Price: 10.0"));
        assertTrue(result.contains("Quantity: 5.0"));
        assertTrue(result.contains("Total Amount: 50.0"));
    }

    @Test
    void testZeroValue() {
        function_numpy_reshape calculator = new function_numpy_reshape();
        String result = calculator.reshape(0.0, 5.0);
        assertTrue(result.contains("Price: 0.0"));
        assertTrue(result.contains("Quantity: 5.0"));
        assertTrue(result.contains("Total Amount: 0.0"));
    }

    @Test
    void testNegativeValue() {
        function_numpy_reshape calculator = new function_numpy_reshape();
        String result = calculator.reshape(-10.0, 5.0);
        assertTrue(result.contains("Price: -10.0"));
        assertTrue(result.contains("Quantity: 5.0"));
        assertTrue(result.contains("Total Amount: -50.0"));
    }

    @Test
    void testFloatValue() {
        function_numpy_reshape calculator = new function_numpy_reshape();
        String result = calculator.reshape(10.5, 3.2);
        assertTrue(result.contains("Price: 10.5"));
        assertTrue(result.contains("Quantity: 3.2"));
        assertTrue(result.contains("Total Amount: 33.6"));
    }

    @Test
    void testDefaultValues() {
        function_numpy_reshape calculator = new function_numpy_reshape();
        String result = calculator.reshape(null, null);
        assertTrue(result.contains("Price: 10.0"));
        assertTrue(result.contains("Quantity: 5.0"));
        assertTrue(result.contains("Total Amount: 50.0"));
    }
}