package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_bitarray_tobytes {
    private function_bitarray_tobytes converter;

    @BeforeEach
    void setUp() {
        converter = new function_bitarray_tobytes();
    }

    @Test
    void test_convert_standard_bitarray() {
        String ba = "110101";
        String result = converter.convert_to_bytes(ba);
        assertEquals("b'\\xd4'", result);
    }

    @Test
    void test_convert_empty_bitarray() {
        String ba = "";
        String result = converter.convert_to_bytes(ba);
        assertEquals("b''", result);
    }

    @Test
    void test_convert_padded_bitarray() {
        String ba = "1101";
        String result = converter.convert_to_bytes(ba);
        assertEquals("b'\\xd0'", result);
    }

    @Test
    void test_convert_bitarray_with_trailing_zeros() {
        String ba = "1101000";
        String result = converter.convert_to_bytes(ba);
        assertEquals("b'\\xd0'", result);
    }

    @Test
    void test_non_bitarray_input() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert_to_bytes("10102");
        });
    }
}