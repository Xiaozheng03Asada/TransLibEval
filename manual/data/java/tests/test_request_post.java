package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_request_post {
    @Test
    void test_success_post() {
        function_request_post handler = new function_request_post();
        assertEquals("success",
                handler.handle_post_request("https://jsonplaceholder.typicode.com/posts", "foo", "bar", 1));
    }

    @Test
    void test_error_post() {
        function_request_post handler = new function_request_post();
        assertEquals("error 404",
                handler.handle_post_request("https://jsonplaceholder.typicode.com/invalid", "foo", "bar", 1));
    }

    @Test
    void test_timeout_post() {
        function_request_post handler = new function_request_post();
        assertEquals("timeout",
                handler.handle_post_request("https://10.255.255.1", "foo", "bar", 1));
    }

    @Test
    void test_large_data_post() {
        function_request_post handler = new function_request_post();
        String largeDataTitle = "foo".repeat(10000);
        String largeDataBody = "bar".repeat(10000);
        assertEquals("success",
                handler.handle_post_request("https://jsonplaceholder.typicode.com/posts", largeDataTitle, largeDataBody, 1));
    }

    @Test
    void test_post_with_empty_body() {
        function_request_post handler = new function_request_post();
        assertEquals("success",
                handler.handle_post_request("https://jsonplaceholder.typicode.com/posts", "", "", 1));
    }
}