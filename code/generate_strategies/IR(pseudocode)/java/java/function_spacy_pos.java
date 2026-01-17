package com.example;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;
import java.util.List;
import java.util.StringJoiner;

public class SpacyPosProcessor {
    public String test_pos(String text) {
        class SpacyPosProcessor {
            private StanfordCoreNLP pipeline;

            public SpacyPosProcessor() {
                
                Properties props = new Properties();
                props.setProperty("annotators", "tokenize, ssplit, pos");
                pipeline = new StanfordCoreNLP(props);
            }

            public String processText(String inputText) {
                if (inputText == null) {
                    throw new IllegalArgumentException("The input must be of string type.");
                }

                try {
                    
                    Annotation document = new Annotation(inputText);
                    pipeline.annotate(document);

                    
                    List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
                    StringJoiner posTagsJoiner = new StringJoiner(", ");

                    
                    for (CoreMap sentence : sentences) {
                        for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                            String word = token.word();
                            String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                            String mappedPos = mapPosTag(pos);
                            if (!mappedPos.isEmpty()) {
                                posTagsJoiner.add(word + " (" + mappedPos + ")");
                            }
                        }
                    }

                    return posTagsJoiner.toString();
                } catch (Exception e) {
                    System.out.println("OtherError: " + e.getMessage());
                    throw e;
                }
            }

            private String mapPosTag(String stanfordTag) {
                
                return switch (stanfordTag) {
                    
                    case "NN", "NNS", "NNP", "NNPS" -> "NOUN";
                    
                    case "VB", "VBD", "VBG", "VBN", "VBP", "VBZ" -> "VERB";
                    
                    case "JJ", "JJR", "JJS" -> "ADJ";
                    
                    case "RB", "RBR", "RBS" -> "ADV";
                    
                    case ".", ",", ":", ";", "!", "?" -> "PUNCT";
                    
                    case "DT" -> "DET";
                    
                    case "PRP", "PRP$", "WP", "WP$" -> "PRON";
                    
                    case "IN" -> "ADP";
                    
                    case "CC" -> "CONJ";
                    
                    case "MD" -> "VERB";
                    
                    default -> stanfordTag;
                };
            }
        }

        SpacyPosProcessor processor = new SpacyPosProcessor();
        return processor.processText(text);
    }
}