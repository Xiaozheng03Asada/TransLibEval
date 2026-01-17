package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_BeautifulSoup_find_all {
    private function_BeautifulSoup_find_all parser;

    @BeforeEach
    void setUp() {
        parser = new function_BeautifulSoup_find_all();
    }

    @Test
    void test_case_1() {
        String html = "<a href=\"https://example.com\">Example</a>";
        assertEquals("https://example.com", parser.extract_links(html));
    }

    @Test
    void test_case_2() {
        String html = "<a href=\"https://example1.com\">Example1</a><a href=\"https://example2.com\">Example2</a>";
        assertEquals("https://example1.com,https://example2.com", parser.extract_links(html));
    }

    @Test
    void test_case_3() {
        String html = "<p>No links here</p>";
        assertEquals("None", parser.extract_links(html));
    }

    @Test
    void test_case_4() {
        String html = "<a href=\"https://example.com\">Valid</a><a>Missing href</a>";
        assertEquals("https://example.com", parser.extract_links(html));
    }

    @Test
    void test_case_5() {
        String html = "";
        assertEquals("None", parser.extract_links(html));
    }
}