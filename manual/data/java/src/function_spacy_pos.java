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
                // 初始化pipeline
                Properties props = new Properties();
                props.setProperty("annotators", "tokenize, ssplit, pos");
                pipeline = new StanfordCoreNLP(props);
            }

            public String processText(String inputText) {
                if (inputText == null) {
                    throw new IllegalArgumentException("The input must be of string type.");
                }

                try {
                    // 创建注释对象
                    Annotation document = new Annotation(inputText);
                    pipeline.annotate(document);

                    // 获取句子
                    List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
                    StringJoiner posTagsJoiner = new StringJoiner(", ");

                    // 处理每个句子中的token
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
                // 扩展映射以更好地匹配spaCy的标签
                return switch (stanfordTag) {
                    // Nouns
                    case "NN", "NNS", "NNP", "NNPS" -> "NOUN";
                    // Verbs
                    case "VB", "VBD", "VBG", "VBN", "VBP", "VBZ" -> "VERB";
                    // Adjectives
                    case "JJ", "JJR", "JJS" -> "ADJ";
                    // Adverbs
                    case "RB", "RBR", "RBS" -> "ADV";
                    // Punctuation
                    case ".", ",", ":", ";", "!", "?" -> "PUNCT";
                    // Articles and Determiners
                    case "DT" -> "DET";
                    // Pronouns
                    case "PRP", "PRP$", "WP", "WP$" -> "PRON";
                    // Prepositions
                    case "IN" -> "ADP";
                    // Conjunctions
                    case "CC" -> "CONJ";
                    // Modal verbs
                    case "MD" -> "VERB";
                    // Default case
                    default -> stanfordTag;
                };
            }
        }

        SpacyPosProcessor processor = new SpacyPosProcessor();
        return processor.processText(text);
    }
}