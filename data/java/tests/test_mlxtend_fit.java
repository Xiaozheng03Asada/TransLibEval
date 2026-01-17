package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_mlxtend_fit {

    @Test
    void test_single_transaction() {
        function_mlxtend_fit onehot_instance = new function_mlxtend_fit();
        assertEquals("bread, butter, milk", onehot_instance.onehot_encode("milk bread butter"));
    }

    @Test
    void test_multiple_transactions() {
        function_mlxtend_fit onehot_instance = new function_mlxtend_fit();
        assertEquals("bread, butter, milk", onehot_instance.onehot_encode("milk bread, bread butter"));
    }

    @Test
    void test_empty_transaction() {
        function_mlxtend_fit onehot_instance = new function_mlxtend_fit();
        assertEquals("", onehot_instance.onehot_encode(""));
    }

    @Test
    void test_repeated_items() {
        function_mlxtend_fit onehot_instance = new function_mlxtend_fit();
        assertEquals("bread, milk", onehot_instance.onehot_encode("milk bread milk"));
    }

    @Test
    void test_non_empty_unique_items() {
        function_mlxtend_fit onehot_instance = new function_mlxtend_fit();
        assertEquals("butter, cheese, milk", onehot_instance.onehot_encode("milk butter cheese"));
    }
}