package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.stream.Collectors;

public class HTMLParser {
    public String extract_links(String html_content) {
        Document doc = Jsoup.parse(html_content);
        Elements links = doc.select("a[href]");

        if (links.isEmpty()) {
            return "None";
        }

        return links.stream()
                .map(link -> link.attr("href"))
                .collect(Collectors.joining(","));
    }
}