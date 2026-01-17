package com.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpacyVectorProcessor {
    public String vector_lengths(String text) {
        class SpacyVectorProcessor {
            private final Analyzer analyzer;

            public SpacyVectorProcessor() {
                
                analyzer = new WhitespaceAnalyzer();
            }

            private List<String> tokenize(String text) throws IOException {
                List<String> tokens = new ArrayList<>();
                TokenStream tokenStream = analyzer.tokenStream(null, text);
                CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

                tokenStream.reset();
                while (tokenStream.incrementToken()) {
                    tokens.add(charTermAttribute.toString());
                }
                tokenStream.end();
                tokenStream.close();

                return tokens;
            }

            private RealVector createWordVector(String word) {
                
                double[] vector = new double[300];

                
                for (int i = 0; i < word.length() && i < 100; i++) {
                    int charValue = word.charAt(i);
                    
                    for (int j = 0; j < 3; j++) {
                        int vectorIndex = i * 3 + j;
                        if (vectorIndex < 300) {
                            vector[vectorIndex] = (charValue * (j + 1) / 65535.0) * 2 - 1;
                        }
                    }
                }

                return new ArrayRealVector(vector);
            }

            public String processVectors(String input) {
                if (input.trim().isEmpty()) {
                    return "";
                }

                StringBuilder vectorLengths = new StringBuilder();
                try {
                    List<String> tokens = tokenize(input);

                    for (String token : tokens) {
                        RealVector vector = createWordVector(token);
                        double vectorLength = vector.getNorm();

                        vectorLengths.append(token)
                                .append(": ")
                                .append(String.format("%.6f", vectorLength))
                                .append(", ");
                    }
                } catch (IOException e) {
                    throw new RuntimeException("分词处理失败", e);
                }

                
                String result = vectorLengths.toString();
                if (!result.isEmpty()) {
                    result = result.substring(0, result.length() - 2);
                }

                return result;
            }
        }

        return new SpacyVectorProcessor().processVectors(text);
    }
}