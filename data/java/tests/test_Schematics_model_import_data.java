package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_Schematics_model_import_data {

    @Test
    public void test_valid_data() {
        function_Schematics_model_import_data product = new function_Schematics_model_import_data();
        String data = "{\"name\": \"Laptop\", \"price\": 1000}";
        String expected = "{'name': 'Laptop', 'price': 1000, 'category': 'General'}";
        assertEquals(expected, product.import_and_validate(data));
    }

    @Test
    public void test_missing_required_field() {
        function_Schematics_model_import_data product = new function_Schematics_model_import_data();
        String data = "{\"price\": 1000}";
        String result = product.import_and_validate(data);
        // 检查错误消息中包含 "name"
        assertTrue(result.contains("name"));
    }

    @Test
    public void test_default_category() {
        function_Schematics_model_import_data product = new function_Schematics_model_import_data();
        String data = "{\"name\": \"Tablet\", \"price\": 500}";
        String nativeResult = product.import_and_validate(data);
        // 检查输出字符串中包含默认 category 值
        assertTrue(nativeResult.contains("'category': 'General'"));
    }

    @Test
    public void test_all_fields_provided() {
        function_Schematics_model_import_data product = new function_Schematics_model_import_data();
        String data = "{\"name\": \"Headphones\", \"price\": 200, \"category\": \"Electronics\"}";
        String expected = "{'name': 'Headphones', 'price': 200, 'category': 'Electronics'}";
        assertEquals(expected, product.import_and_validate(data));
    }

    @Test
    public void test_partial_data_with_partial_true() {
        function_Schematics_model_import_data product = new function_Schematics_model_import_data();
        String data = "{\"name\": \"Camera\"}";
        String result = product.import_and_validate(data);
        // 检查错误消息中包含 "price"
        assertTrue(result.contains("price"));
    }
}