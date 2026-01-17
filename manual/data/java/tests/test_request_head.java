package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_request_head {
    @Test
    public void test_success() {
        String result = new function_request_head().handle_request("https://jsonplaceholder.typicode.com/posts/1");
        assertEquals("success", result);
    }

    @Test
    public void test_redirect() {
        String result = new function_request_head().handle_request("https://httpstat.us/301");
        assertEquals("redirect 301", result);
    }

    @Test
    public void test_error_403() {
        String result = new function_request_head().handle_request("https://httpstat.us/403");
        assertEquals("error 403", result);
    }

    @Test
    public void test_error_500() {
        String result = new function_request_head().handle_request("https://httpstat.us/500");
        assertEquals("error 500", result);
    }

    @Test
    public void test_timeout() {
        String result = new function_request_head().handle_request("https://10.255.255.1");
        assertEquals("timeout", result);
    }
}