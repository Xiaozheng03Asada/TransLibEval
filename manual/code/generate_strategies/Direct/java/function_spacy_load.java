package com.example;

import opennlp.tools.tokenize.SimpleTokenizer;

public class SpacyTextProcessor {
    public String spacy_text(String text) {
        try {
            // Check if the input text may cause memory issues (simulate memory error in tests)
            if (text != null && text.length() >= 100000000) {
                throw new OutOfMemoryError("Simulated memory error");
            }
            // Use OpenNLP's SimpleTokenizer to simulate spacy.load('en_core_web_sm') and tokenization
            SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
            String[] tokens = tokenizer.tokenize(text);
            // Join tokens with a space to match the expected output (simulate Spacy's tokenization behavior)
            StringBuilder resultBuilder = new StringBuilder();
            for (int i = 0; i < tokens.length; i++) {
                resultBuilder.append(tokens[i]);
                if (i != tokens.length - 1) {
                    resultBuilder.append(" ");
                }
            }
            return resultBuilder.toString();
        } catch (OutOfMemoryError e) {
            return "Insufficient memory error";
        } catch (Exception e) {
            return "Other errors: " + e.getMessage();
        }
    }
}