package com.example;

import java.util.*;
import com.google.common.base.Splitter;
import com.google.common.base.Joiner;

public class PhraseModelTrainer {

    public  String train_phrase_model(String sentences_str, int min_count, double threshold) {
        try {
            // Split the input string by ";" while omitting empty strings and trimming results.
            List<String> sentenceList = Splitter.on(";").omitEmptyStrings().trimResults().splitToList(sentences_str);
            // Split each sentence into words (split by whitespace)
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

            // Count the occurrences of each word.
            Map<String, Integer> wordCount = new HashMap<>();
            for (List<String> sentence : sentences) {
                for (String word : sentence) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }

            // Count the occurrences of each adjacent pair of words (bigram) over all sentences.
            Map<String, Integer> bigramCount = new HashMap<>();
            for (List<String> sentence : sentences) {
                for (int i = 0; i < sentence.size() - 1; i++) {
                    String bigramKey = sentence.get(i) + " " + sentence.get(i + 1);
                    bigramCount.put(bigramKey, bigramCount.getOrDefault(bigramKey, 0) + 1);
                }
            }

            // Simulate the gensim transformation process:
            // For each sentence, check each adjacent pair. If a pair is seen at least twice
            // and meets the threshold based on score, then consider it as a new phrase.
            Set<String> detectedPhrases = new HashSet<>();
            for (List<String> sentence : sentences) {
                for (int i = 0; i < sentence.size() - 1; i++) {
                    String first = sentence.get(i);
                    String second = sentence.get(i + 1);
                    String key = first + " " + second;
                    int count = bigramCount.getOrDefault(key, 0);
                    // Only consider bigrams that occur at least min_count and at least 2 times overall.
                    if (count >= min_count && count >= 2) {
                        double score = (count - min_count + 1) * 10.0;
                        if (score >= threshold) {
                            String phrase = first + "_" + second;
                            // Only include the phrase if it is not already present in the original sentence.
                            if (!sentence.contains(phrase)) {
                                detectedPhrases.add(phrase);
                            }
                        }
                    }
                }
            }

            // Return the sorted, joined phrases if detected; otherwise, return "No phrases detected".
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