package com.example;

import java.util.*;
import com.google.common.base.Splitter;
import com.google.common.base.Joiner;

public class PhraseModelTrainer {

    public  String train_phrase_model(String sentences_str, int min_count, double threshold) {
        try {
            
            List<String> sentenceList = Splitter.on(";").omitEmptyStrings().trimResults().splitToList(sentences_str);
            
            List<List<String>> sentences = new ArrayList<>();
            for (String sentence : sentenceList) {
                String[] tokens = sentence.split("\\s+");
                if (tokens.length > 0) {
                    sentences.add(Arrays.asList(tokens));
                }
            }

            if (sentences.isEmpty()) {
                return "No phrases detected";
            }

            
            Map<String, Integer> wordCount = new HashMap<>();
            for (List<String> sentence : sentences) {
                for (String word : sentence) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }

            
            Map<String, Integer> bigramCount = new HashMap<>();
            for (List<String> sentence : sentences) {
                for (int i = 0; i < sentence.size() - 1; i++) {
                    String bigramKey = sentence.get(i) + " " + sentence.get(i + 1);
                    bigramCount.put(bigramKey, bigramCount.getOrDefault(bigramKey, 0) + 1);
                }
            }

            
            
            
            Set<String> detectedPhrases = new HashSet<>();
            for (List<String> sentence : sentences) {
                for (int i = 0; i < sentence.size() - 1; i++) {
                    String first = sentence.get(i);
                    String second = sentence.get(i + 1);
                    String key = first + " " + second;
                    int count = bigramCount.getOrDefault(key, 0);
                    
                    if (count >= min_count && count >= 2) {
                        double score = (count - min_count + 1) * 10.0;
                        if (score >= threshold) {
                            String phrase = first + "_" + second;
                            
                            if (!sentence.contains(phrase)) {
                                detectedPhrases.add(phrase);
                            }
                        }
                    }
                }
            }

            
            if (!detectedPhrases.isEmpty()) {
                List<String> sortedPhrases = new ArrayList<>(detectedPhrases);
                Collections.sort(sortedPhrases);
                return Joiner.on(" | ").join(sortedPhrases);
            } else {
                return "No phrases detected";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}