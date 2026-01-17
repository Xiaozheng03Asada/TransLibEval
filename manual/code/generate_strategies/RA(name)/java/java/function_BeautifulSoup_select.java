package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.stream.Collectors;

public class HTMLParser {
    public String extract_paragraphs(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        Elements paragraphs = doc.select("p");

        if (paragraphs.isEmpty()) {
            return "None";
        }

        return paragraphs.stream()
                .map(element -> element.text())
                .collect(Collectors.joining("|"));
    }
}