package com.example;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class StringProcessor {
    public int calculate_levenshtein_distance(String str1, String str2) {
        LevenshteinDistance levenshtein = new LevenshteinDistance();
        return levenshtein.apply(str1, str2);
    }
}