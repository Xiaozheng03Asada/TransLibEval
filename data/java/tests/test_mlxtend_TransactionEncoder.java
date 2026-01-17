package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_mlxtend_TransactionEncoder {

    @Test
    void test_single_transaction() {
        function_mlxtend_TransactionEncoder encoder = new function_mlxtend_TransactionEncoder();
        String transactions = "apple,banana";
        String expected_output = "1,1";
        assertEquals(expected_output, encoder.encode_transactions(transactions));
    }

    @Test
    void test_multiple_transactions() {
        function_mlxtend_TransactionEncoder encoder = new function_mlxtend_TransactionEncoder();
        String transactions = "apple,banana;banana,cherry;apple,cherry";
        String expected_output = "1,1,0;0,1,1;1,0,1";
        assertEquals(expected_output, encoder.encode_transactions(transactions));
    }

    @Test
    void test_empty_transaction() {
        function_mlxtend_TransactionEncoder encoder = new function_mlxtend_TransactionEncoder();
        String transactions = "";
        String expected_output = "[]";
        assertEquals(expected_output, encoder.encode_transactions(transactions));
    }

    @Test
    void test_repeated_items() {
        function_mlxtend_TransactionEncoder encoder = new function_mlxtend_TransactionEncoder();
        String transactions = "apple,banana,apple";
        String expected_output = "1,1";
        assertEquals(expected_output, encoder.encode_transactions(transactions));
    }

    @Test
    void test_no_transactions() {
        function_mlxtend_TransactionEncoder encoder = new function_mlxtend_TransactionEncoder();
        String transactions = "";
        String expected_output = "[]";
        assertEquals(expected_output, encoder.encode_transactions(transactions));
    }
}