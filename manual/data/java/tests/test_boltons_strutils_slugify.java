package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class test_boltons_strutils_slugify {

    private function_boltons_strutils_slugify slugifier;

    @BeforeEach
    public void setUp() {
        slugifier = new function_boltons_strutils_slugify();
    }

    // Modified to pass null as the delimiter to trigger the default behavior.
    @Test
    public void test_slugify_basic() {
        String text = "Hello World";
        String result = slugifier.slugify_example(text, null);
        assertEquals("hello-world", result);
    }

    @Test
    public void test_slugify_with_custom_delimiter() {
        String text = "Python is awesome!";
        String result = slugifier.slugify_example(text, "_");
        assertEquals("python_is_awesome", result);
    }

    @Test
    public void test_slugify_special_characters() {
        String text = "Clean @!this** up$!";
        String result = slugifier.slugify_example(text, null);
        assertEquals("clean-this-up", result);
    }

    @Test
    public void test_slugify_empty_string() {
        String text = "";
        String result = slugifier.slugify_example(text, null);
        assertEquals("", result);
    }

    @Test
    public void test_slugify_non_string_input() {
        // Since Java is statically typed, non-string inputs are not allowed.
        // We simulate invalid input by passing null as the text.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            slugifier.slugify_example(null, null);
        });
        String expectedMessage = "Input text must be a string";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}