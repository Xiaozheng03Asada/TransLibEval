package com.example;

import opennlp.tools.tokenize.SimpleTokenizer;

public class SpacyTextProcessor {
    public String spacy_text(String text) {
        try {
            
            if (text != null && text.length() >= 100000000) {
                throw new OutOfMemoryError("Simulated memory error");
            }
            
            SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
            String[] tokens = tokenizer.tokenize(text);
            
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