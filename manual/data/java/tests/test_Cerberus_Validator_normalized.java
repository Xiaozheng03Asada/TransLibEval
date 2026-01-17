package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_Cerberus_Validator_normalized {
    private function_Cerberus_Validator_normalized normalizer;

    @BeforeEach
    void setUp() {
        normalizer = new function_Cerberus_Validator_normalized();
    }

    @Test
    void testDataWithMissingFields() {
        String dataStr = "{\"name\": \"Alice\"}";
        String normalizedData = normalizer.normalize_data(dataStr);
        assertEquals("{\"name\":\"Alice\",\"age\":18}", normalizedData);
    }

    @Test
    void testDataWithAllFields() {
        String dataStr = "{\"name\": \"Bob\", \"age\": 25}";
        String normalizedData = normalizer.normalize_data(dataStr);
        assertEquals("{\"name\":\"Bob\",\"age\":25}", normalizedData);
    }

    @Test
    void testDataWithMissingAge() {
        String dataStr = "{\"name\": \"Charlie\"}";
        String normalizedData = normalizer.normalize_data(dataStr);
        assertEquals("{\"name\":\"Charlie\",\"age\":18}", normalizedData);
    }

    @Test
    void testEmptyData() {
        String dataStr = "{}";
        String normalizedData = normalizer.normalize_data(dataStr);
        assertEquals("{\"name\":\"Unknown\",\"age\":18}", normalizedData);
    }

    @Test
    void testInvalidInput() {
        String normalizedData = normalizer.normalize_data(null);
        assertEquals("Error: Input must be a string", normalizedData);
    }
}