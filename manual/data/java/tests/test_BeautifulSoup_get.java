package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_BeautifulSoup_get {
    private function_BeautifulSoup_get parser;

    @BeforeEach
    void setUp() {
        parser = new function_BeautifulSoup_get();
    }

    @Test
    void test_case_1() {
        String html = "<a href=\"https://example.com\">Example</a>";
        assertEquals("https://example.com", parser.extract_first_link(html));
    }

    @Test
    void test_case_2() {
        String html =
                "<a href=\"https://example1.com\">Example1</a>\n" +
                        "<a href=\"https://example2.com\">Example2</a>";
        assertEquals("https://example1.com", parser.extract_first_link(html));
    }

    @Test
    void test_case_3() {
        String html = "<p>No links here</p>";
        assertEquals("", parser.extract_first_link(html));
    }

    @Test
    void test_case_4() {
        String html =
                "<a href=\"https://example.com\">Example</a>\n" +
                        "<div>Some content</div>\n" +
                        "<a href=\"https://example2.com\">Example2</a>";
        assertEquals("https://example.com", parser.extract_first_link(html));
    }

    @Test
    void test_case_5() {
        String html = "";
        assertEquals("", parser.extract_first_link(html));
    }
}