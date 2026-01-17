package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_Pydantic_root_validator {

    @Test
    void test_valid_order() {
        function_Pydantic_root_validator handler = new function_Pydantic_root_validator();
        String result = handler.check_discount_for_large_order(5, 100.0f, 0.0f);
        assertEquals("quantity=5 price=100.0 discount=0.0", result);
    }

    @Test
    void test_order_with_large_quantity_without_discount() {
        function_Pydantic_root_validator handler = new function_Pydantic_root_validator();
        String result = handler.check_discount_for_large_order(15, 100.0f, 0.0f);
        assertTrue(result.contains("Discount must be greater than 0 for orders with quantity greater than 10"));
    }

    @Test
    void test_order_with_discount_but_negative_price() {
        function_Pydantic_root_validator handler = new function_Pydantic_root_validator();
        String result = handler.check_discount_for_large_order(5, -50.0f, 10.0f);
        assertTrue(result.contains("Price must be positive when discount is applied"));
    }

    @Test
    void test_order_with_large_quantity_and_discount() {
        function_Pydantic_root_validator handler = new function_Pydantic_root_validator();
        String result = handler.check_discount_for_large_order(15, 100.0f, 10.0f);
        assertEquals("quantity=15 price=100.0 discount=10.0", result);
    }

    @Test
    void test_valid_order_without_discount() {
        function_Pydantic_root_validator handler = new function_Pydantic_root_validator();
        String result = handler.check_discount_for_large_order(5, 50.0f, 0.0f);
        assertEquals("quantity=5 price=50.0 discount=0.0", result);
    }
}