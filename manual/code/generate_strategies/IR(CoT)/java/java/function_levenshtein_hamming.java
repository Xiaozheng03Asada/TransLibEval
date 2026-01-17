package com.example;

import org.apache.commons.text.similarity.HammingDistance;

public class StringProcessor {
    public String calculate_hamming_distance(String str1, String str2) {
        
        class StringProcessor {
            public String calculate_hamming_distance(String str1, String str2) {
                
                if (str1 == null || str2 == null) {
                    return "Error: Both inputs must be strings";
                }

                
                if (str1.length() != str2.length()) {
                    return "Strings must be of the same length for Hamming distance.";
                }

                
                HammingDistance hammingDistance = new HammingDistance();
                return String.valueOf(hammingDistance.apply(str1, str2));
            }
        }

        
        StringProcessor processor = new StringProcessor();
        return processor.calculate_hamming_distance(str1, str2);
    }
}