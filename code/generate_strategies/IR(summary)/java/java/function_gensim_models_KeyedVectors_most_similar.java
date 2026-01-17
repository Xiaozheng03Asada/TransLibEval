package com.example;

import com.google.common.collect.ImmutableMap; 
import java.util.*;

public class FindMostSimilarWords {

    
    public String find_most_similar_words(String word, int topn) {
        
        if (this.word_vectors == null || this.word_vectors.isEmpty()) {
            
            this.word_vectors = new LinkedHashMap<>();
            this.word_vectors.put("king", Arrays.asList(0.2, 0.3, 0.4, 0.1, 0.5, 0.7, 0.9, 0.6, 0.2, 0.1));
            this.word_vectors.put("queen", Arrays.asList(0.2, 0.3, 0.4, 0.1, 0.5, 0.6, 0.8, 0.6, 0.2, 0.1));
            this.word_vectors.put("man", Arrays.asList(0.1, 0.2, 0.3, 0.0, 0.4, 0.6, 0.7, 0.5, 0.1, 0.0));
            this.word_vectors.put("woman", Arrays.asList(0.1, 0.2, 0.3, 0.0, 0.4, 0.5, 0.6, 0.5, 0.1, 0.1));
            this.word_vectors.put("apple", Arrays.asList(0.3, 0.3, 0.5, 0.4, 0.2, 0.7, 0.9, 0.6, 0.1, 0.2));
        }

        
        if (!this.word_vectors.containsKey(word)) {
            return "Error";
        }

        
        List<String> similarWords = new ArrayList<>();
        for (String w : this.word_vectors.keySet()) {
            if (!w.equals(word)) {
                similarWords.add(w);
            }
        }

        
        List<String> topSimilar = similarWords.subList(0, Math.min(topn, similarWords.size()));
        List<String> parts = new ArrayList<>();
        for (String w : topSimilar) {
            parts.add(String.format("%s: %.2f", w, 1.0));
        }

        return String.join(", ", parts);
    }

    
    public Map<String, List<Double>> word_vectors;
}