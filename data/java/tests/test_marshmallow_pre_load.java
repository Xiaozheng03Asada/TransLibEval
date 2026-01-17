package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class test_marshmallow_pre_load {

    private function_marshmallow_pre_load processor;

    @BeforeEach
    public void setUp() {
        processor = new function_marshmallow_pre_load();
    }

    @Test
    public void test_pre_load_conversion() {
        String product_data = "{\"name\": \"tablet\", \"price\": 199.99}";
        String result = processor.process_product_data(product_data);
        // 检查返回结果中名字是否转换为大写，并且价格正确
        assertTrue(result.contains("\"name\":\"TABLET\"") || result.contains("\"name\": \"TABLET\""));
        assertTrue(result.contains("\"price\":199.99") || result.contains("\"price\": 199.99"));
    }

    @Test
    public void test_invalid_data_type() {
        String product_data = "{\"name\": 12345, \"price\": 199.99}";
        String result = processor.process_product_data(product_data);
        assertEquals("Error: invalid input", result);
    }

    @Test
    public void test_invalid_price() {
        String product_data = "{\"name\": \"tablet\", \"price\": \"not_a_number\"}";
        String result = processor.process_product_data(product_data);
        assertEquals("Error: invalid input", result);
    }

    @Test
    public void test_missing_name_field() {
        String product_data = "{\"price\": 199.99, \"in_stock\": true}";
        String result = processor.process_product_data(product_data);
        assertEquals("Error: invalid input", result);
    }

    @Test
    public void test_missing_price_field() {
        String product_data = "{\"name\": \"tablet\", \"in_stock\": true}";
        String result = processor.process_product_data(product_data);
        assertEquals("Error: invalid input", result);
    }
}