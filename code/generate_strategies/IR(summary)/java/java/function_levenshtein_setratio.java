package com.example;

import org.apache.commons.text.similarity.LevenshteinDistance;
import java.util.HashSet;
import java.util.Set;

public class StringProcessor {
    public double calculate_setratio(String str1, String str2) {
        
        Set<Character> set1 = new HashSet<>();
        Set<Character> set2 = new HashSet<>();

        
        for (char c : str1.toCharArray()) {
            set1.add(c);
        }
        for (char c : str2.toCharArray()) {
            set2.add(c);
        }

        
        if (set1.isEmpty() && set2.isEmpty()) {
            return 1.0;
        }

        
        Set<Character> union = new HashSet<>(set1);
        union.addAll(set2);

        Set<Character> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        
        return union.isEmpty() ? 1.0 : (2.0 * intersection.size()) / (set1.size() + set2.size());
    }
}