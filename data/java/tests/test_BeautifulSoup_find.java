package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_BeautifulSoup_find {
    private function_BeautifulSoup_find parser;

    @BeforeEach
    void setUp() {
        parser = new function_BeautifulSoup_find();
    }

    @Test
    void test_case_1() {
        String html = "<h1>Welcome to the site</h1>";
        assertEquals("Welcome to the site", parser.extract_first_h1_text(html));
    }

    @Test
    void test_case_2() {
        String html =
                "<h1>First Heading</h1>\n" +
                        "<h1>Second Heading</h1>";
        assertEquals("First Heading", parser.extract_first_h1_text(html));
    }

    @Test
    void test_case_3() {
        String html = "<p>No h1 tag here</p>";
        assertEquals("None", parser.extract_first_h1_text(html));
    }

    @Test
    void test_case_4() {
        String html = "<h1>Single Heading</h1><p>Paragraph</p>";
        assertEquals("Single Heading", parser.extract_first_h1_text(html));
    }

    @Test
    void test_case_5() {
        String html = "";
        assertEquals("None", parser.extract_first_h1_text(html));
    }
}