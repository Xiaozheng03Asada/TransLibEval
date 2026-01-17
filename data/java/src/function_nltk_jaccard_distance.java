package com.example;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.HashSet;

public class JaccardExample {

    // The unique method that implements all functionality.
    public double compute_jaccard_distance(String string1, String string2) {
        // Declare local inner class TypeError inside the method
        class TypeError extends RuntimeException {
            TypeError(String msg) {
                super(msg);
            }
        }

        // Check if inputs are non-null. (Java’s static typing already ensures a String,
        // so we treat a null value as an invalid input to mimic non-string cases.)
        if (string1 == null || string2 == null) {
            throw new TypeError("Both inputs must be non-null strings");
        }

        // Generate bigrams (2-grams) from string1
        Set<String> set1 = new HashSet<>();
        for (int i = 0; i < string1.length() - 1; i++) {
            set1.add(string1.substring(i, i + 2));
        }

        // Generate bigrams from string2
        Set<String> set2 = new HashSet<>();
        for (int i = 0; i < string2.length() - 1; i++) {
            set2.add(string2.substring(i, i + 2));
        }

        // If both sets are empty, return 0.0
        if (set1.isEmpty() && set2.isEmpty()) {
            return 0.0;
        }

        // Compute the Jaccard distance: 1.0 - (intersection size / union size)
        Set<String> intersection = Sets.intersection(set1, set2);
        Set<String> union = Sets.union(set1, set2);
        double similarity = (double) intersection.size() / union.size();
        double distance = 1.0 - similarity;

        return distance;
    }
}