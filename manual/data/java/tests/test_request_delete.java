package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_request_delete {
    @Test
    void test_success_delete() {
        String result = new function_request_delete()
                .handle_delete_request("https://jsonplaceholder.typicode.com/posts/1");
        assertEquals("error", result);
    }

    @Test
    void test_error_delete() {
        String result = new function_request_delete()
                .handle_delete_request("https://api.github.com/repos/nonexistentrepo");
        assertEquals("error 404", result);
    }

    @Test
    void test_timeout_delete() {
        String result = new function_request_delete()
                .handle_delete_request("https://10.255.255.1");
        assertEquals("timeout", result);
    }

    @Test
    void test_delete_nonexistent_user() {
        String result = new function_request_delete()
                .handle_delete_request("https://api.github.com/users/nonexistentuser");
        assertEquals("error 404", result);
    }

    @Test
    void test_delete_invalid_url() {
        String result = new function_request_delete()
                .handle_delete_request("https://invalid.url");
        assertEquals("ssl_error", result);
    }
}