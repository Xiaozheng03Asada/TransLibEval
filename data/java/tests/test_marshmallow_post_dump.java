package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class test_marshmallow_post_dump {

    private function_marshmallow_post_dump schema;

    @BeforeEach
    void setUp() {
        schema = new function_marshmallow_post_dump();
    }

    @Test
    void test_valid_user_data() {
        String userData = "{\"age\": 30, \"score\": 85.0}";
        String result = schema.serialize_user_data(userData);
        String expected = "{\"result\":{\"age\":30,\"score\":85.0}}";
        assertEquals(expected, result, "Valid user data should be correctly serialized");
    }

    @Test
    void test_output_structure() {
        String userData = "{\"age\": 50, \"score\": 95.5}";
        String result = schema.serialize_user_data(userData);
        String expected = "{\"result\":{\"age\":50,\"score\":95.5}}";
        assertEquals(expected, result, "Output should have correct structure with result wrapper");
    }

    @Test
    void test_extra_fields_in_input() {
        String userData = "{\"age\": 35, \"score\": 88.5, \"extra\": \"field\"}";
        String expected = "{\"result\":{\"age\":35,\"score\":88.5}}";
        String result = schema.serialize_user_data(userData);
        assertEquals(expected, result, "Extra fields should be excluded from output");
    }

    @Test
    void test_zero_values() {
        String userData = "{\"age\": 0, \"score\": 0.0}";
        String expected = "{\"result\":{\"age\":0,\"score\":0.0}}";
        String result = schema.serialize_user_data(userData);
        assertEquals(expected, result, "Zero values should be handled correctly");
    }

    @Test
    void test_invalid_input() {
        String userData = "{\"age\": \"abc\", \"score\": \"def\"}";
        String result = schema.serialize_user_data(userData);
        assertEquals("Error: invalid input", result, "Invalid input should return error message");
    }
}