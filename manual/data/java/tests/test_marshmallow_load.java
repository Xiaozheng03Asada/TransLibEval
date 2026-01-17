package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_marshmallow_load {

    @Test
    void testValidData() {
        String productData = "{\"name\": \"Laptop\", \"price\": 999.99, \"in_stock\": true}";
        function_marshmallow_load loader = new function_marshmallow_load();
        String result = loader.deserialize_product_data(productData);
        assertEquals("{\"name\":\"Laptop\",\"price\":999.99,\"in_stock\":true}", result);
    }

    @Test
    void testMissingRequiredFields() {
        String productData = "{\"price\": 499.99}";
        function_marshmallow_load loader = new function_marshmallow_load();
        String result = loader.deserialize_product_data(productData);
        assertEquals("Error: invalid input", result);
    }

    @Test
    void testDefaultFieldValue() {
        String productData = "{\"name\": \"Tablet\", \"price\": 199.99}";
        function_marshmallow_load loader = new function_marshmallow_load();
        String result = loader.deserialize_product_data(productData);
        assertEquals("{\"name\":\"Tablet\",\"price\":199.99,\"in_stock\":true}", result);
    }

    @Test
    void testInvalidDataType() {
        String productData = "{\"name\": \"Chair\", \"price\": \"forty\"}";
        function_marshmallow_load loader = new function_marshmallow_load();
        String result = loader.deserialize_product_data(productData);
        assertEquals("Error: invalid input", result);
    }

    @Test
    void testProductWithOnlyNameAndPrice() {
        String productData = "{\"name\": \"Phone\", \"price\": 799.99}";
        function_marshmallow_load loader = new function_marshmallow_load();
        String result = loader.deserialize_product_data(productData);
        assertEquals("{\"name\":\"Phone\",\"price\":799.99,\"in_stock\":true}", result);
    }
}