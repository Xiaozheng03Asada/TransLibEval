package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_boltons_cacheutils_LRU {
    private function_boltons_cacheutils_LRU function;

    @BeforeEach
    void setUp() {
        function = new function_boltons_cacheutils_LRU();
    }

    @Test
    void test_basic_set_and_get() {
        String operations = "set,a,1,set,b,2,get,a,get,b";
        String result = function.manage_cache_operations(3, operations);
        assertEquals("1,2", result);
    }

    @Test
    void test_lru_eviction() {
        String operations = "set,a,1,set,b,2,set,c,3,set,d,4,get,a,get,b";
        String result = function.manage_cache_operations(3, operations);
        assertEquals("None,2", result);
    }

    @Test
    void test_update_existing_key() {
        String operations = "set,a,1,set,a,10,get,a";
        String result = function.manage_cache_operations(2, operations);
        assertEquals("10", result);
    }

    @Test
    void test_cache_size_limit() {
        String operations = "set,a,1,set,b,2,set,c,3,set,d,4,get,a,get,b,get,c,get,d";
        String result = function.manage_cache_operations(2, operations);
        assertEquals("None,None,3,4", result);
    }

    @Test
    void test_empty_cache_operations() {
        String result = function.manage_cache_operations(2, "");
        assertEquals("", result);
    }
}