package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_Pydantic_conint {
    @Test
    public void test_valid_product() {
        function_Pydantic_conint validator = new function_Pydantic_conint();
        String result = validator.create_product(500, 19.99f);
        assertEquals("stock=500 price=19.99", result);
    }

    @Test
    public void test_zero_stock() {
        function_Pydantic_conint validator = new function_Pydantic_conint();
        String result = validator.create_product(0, 5.00f);
        assertEquals("stock=0 price=5.00", result);
    }

    @Test
    public void test_stock_at_upper_limit() {
        function_Pydantic_conint validator = new function_Pydantic_conint();
        String result = validator.create_product(1000, 15.00f);
        assertEquals("stock=1000 price=15.00", result);
    }

    @Test
    public void test_stock_at_lower_limit() {
        function_Pydantic_conint validator = new function_Pydantic_conint();
        String result = validator.create_product(0, 20.00f);
        assertEquals("stock=0 price=20.00", result);
    }

    @Test
    public void test_high_price() {
        function_Pydantic_conint validator = new function_Pydantic_conint();
        String result = validator.create_product(100, 9999.99f);
        assertEquals("stock=100 price=9999.99", result);
    }
}