package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



class test_boltons_iterutils_chunked {

    private function_boltons_iterutils_chunked chunkManager;

    @BeforeEach
    void setUp() {
        this.chunkManager = new function_boltons_iterutils_chunked();
    }

    @Test
    void test_split_exact_chunks() {
        String data = "1,2,3,4,5,6";
        String result = this.chunkManager.chunk_data(data, 2);
        String expected = "1,2;3,4;5,6";
        assertEquals(expected, result);
    }

    @Test
    void test_split_with_remainder() {
        String data = "1,2,3,4,5";
        String result = this.chunkManager.chunk_data(data, 2);
        String expected = "1,2;3,4;5";
        assertEquals(expected, result);
    }

    @Test
    void test_split_invalid_input() {
        String data = null;
        String result = this.chunkManager.chunk_data(data, 0);
        String expected = "Error";
        assertEquals(expected, result);
    }

    @Test
    void test_split_with_strings() {
        String data = "a,b,c,d,e,f,g,h";
        String result = this.chunkManager.chunk_data(data, 3);
        String expected = "a,b,c;d,e,f;g,h";
        assertEquals(expected, result);
    }

    @Test
    void test_split_with_iterable_generator() {
        String data = "0,1,2,3,4,5,6";
        String result = this.chunkManager.chunk_data(data, 3);
        String expected = "0,1,2;3,4,5;6";
        assertEquals(expected, result);
    }
}