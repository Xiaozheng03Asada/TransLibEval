package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HTMLParser {
    public String extract_first_h1_text(String html_content) {
        try {
            Document doc = Jsoup.parse(html_content);
            Element h1 = doc.selectFirst("h1");
            return h1 != null ? h1.text() : "None";
        } catch (Exception e) {
            return "None";
        }
    }
}