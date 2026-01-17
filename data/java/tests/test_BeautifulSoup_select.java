package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_BeautifulSoup_select {
    private function_BeautifulSoup_select parser;

    @BeforeEach
    void setUp() {
        parser = new function_BeautifulSoup_select();
    }

    @Test
    void test_case_1() {
        String html = "<p>First paragraph.</p><p>Second paragraph.</p>";
        assertEquals("First paragraph.|Second paragraph.", parser.extract_paragraphs(html));
    }

    @Test
    void test_case_2() {
        String html = "<p>Only one paragraph.</p>";
        assertEquals("Only one paragraph.", parser.extract_paragraphs(html));
    }

    @Test
    void test_case_3() {
        String html = "<div>No paragraphs here</div>";
        assertEquals("None", parser.extract_paragraphs(html));
    }

    @Test
    void test_case_4() {
        String html = "\n        <p>Paragraph 1</p>\n        <div>Some content</div>\n        <p>Paragraph 2</p>\n        ";
        assertEquals("Paragraph 1|Paragraph 2", parser.extract_paragraphs(html));
    }

    @Test
    void test_case_5() {
        String html = "";
        assertEquals("None", parser.extract_paragraphs(html));
    }
}