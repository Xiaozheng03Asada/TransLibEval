package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_Pydantic_constr {

    @Test
    public void test_valid_profile() {
        function_Pydantic_constr handler = new function_Pydantic_constr();
        String result = handler.create_user_profile("user_123", "user@example.com");
        assertEquals("username='user_123' email='user@example.com'", result);
    }

    @Test
    public void test_invalid_email() {
        function_Pydantic_constr handler = new function_Pydantic_constr();
        String result = handler.create_user_profile("user123", "user.com");
        assertTrue(result.contains("value is not a valid email address"));
    }

    @Test
    public void test_valid_profile_with_min_length_username() {
        function_Pydantic_constr handler = new function_Pydantic_constr();
        String result = handler.create_user_profile("abc", "test@example.com");
        assertEquals("username='abc' email='test@example.com'", result);
    }

    @Test
    public void test_valid_profile_with_max_length_username() {
        function_Pydantic_constr handler = new function_Pydantic_constr();
        String result = handler.create_user_profile("aaaaaaaaaaaaaaaaaaaa", "longusername@example.com");
        assertEquals("username='aaaaaaaaaaaaaaaaaaaa' email='longusername@example.com'", result);
    }

    @Test
    public void test_invalid_email_missing_domain() {
        function_Pydantic_constr handler = new function_Pydantic_constr();
        String result = handler.create_user_profile("user123", "user@");
        assertTrue(result.contains("value is not a valid email address"));
    }
}