package com.example;

import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.collections4.bag.HashBag;

public class WordCounter {
    public String count_words(String word_string) {
        if (word_string == null) {
            throw new IllegalArgumentException("Input should be a string of words");
        }

        if (word_string.trim().isEmpty()) {
            return "";
        }

        String[] words = word_string.split(",");
        
        Map<String, Integer> wordCount = new LinkedHashMap<>();

        
        for (String word : words) {
            String processedWord = word.trim().toLowerCase();
            if (!wordCount.containsKey(processedWord)) {
                wordCount.put(processedWord, 0);
            }
        }

        
        for (String word : words) {
            String processedWord = word.trim().toLowerCase();
            wordCount.put(processedWord, wordCount.get(processedWord) + 1);
        }

        return wordCount.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .collect(Collectors.joining(";"));
    }
}