package com.example;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.HashSet;

public class JaccardExample {

    
    public double compute_jaccard_distance(String string1, String string2) {
        
        class TypeError extends RuntimeException {
            TypeError(String msg) {
                super(msg);
            }
        }

        
        
        if (string1 == null || string2 == null) {
            throw new TypeError("Both inputs must be non-null strings");
        }

        
        Set<String> set1 = new HashSet<>();
        for (int i = 0; i < string1.length() - 1; i++) {
            set1.add(string1.substring(i, i + 2));
        }

        
        Set<String> set2 = new HashSet<>();
        for (int i = 0; i < string2.length() - 1; i++) {
            set2.add(string2.substring(i, i + 2));
        }

        
        if (set1.isEmpty() && set2.isEmpty()) {
            return 0.0;
        }

        
        Set<String> intersection = Sets.intersection(set1, set2);
        Set<String> union = Sets.union(set1, set2);
        double similarity = (double) intersection.size() / union.size();
        double distance = 1.0 - similarity;

        return distance;
    }
}