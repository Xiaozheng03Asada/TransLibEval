package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_request_get {
    private function_request_get handler;

    @BeforeEach
    void setUp() {
        handler = new function_request_get();
    }

    @Test
    void test_success() {
        String result = handler.handle_request("https://jsonplaceholder.typicode.com/posts/1");
        assertEquals("success", result);
    }

    @Test
    void test_redirect() {
        String result = handler.handle_request("https://httpstat.us/301");
        assertEquals("redirect 301", result);
    }

    @Test
    void test_error_403() {
        String result = handler.handle_request("https://httpstat.us/403");
        assertEquals("error 403", result);
    }

    @Test
    void test_error_500() {
        String result = handler.handle_request("https://httpstat.us/500");
        assertEquals("error 500", result);
    }

    @Test
    void test_timeout() {
        String result = handler.handle_request("https://10.255.255.1");
        assertEquals("timeout", result);
    }
}