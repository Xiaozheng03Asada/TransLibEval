package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HTMLParser {
    public String extract_first_link(String html_content) {
        class HTMLParser {
            public String extract_first_link(String html_content) {
                try {
                    Document doc = Jsoup.parse(html_content);
                    Element firstLink = doc.select("a").first();
                    return firstLink != null ? firstLink.attr("href") : "";
                } catch (Exception e) {
                    return "";
                }
            }
        }
        return new HTMLParser().extract_first_link(html_content);
    }
}