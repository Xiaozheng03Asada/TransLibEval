package com.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Collections;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;

public class Word2VecModelTrainer {
    
    public String train_word2vec_model(String sentences_str, int vector_size, int window, int min_count) {
        try {
            
            String[] sentenceArr = sentences_str.split(";");
            List<String> sentencesList = new ArrayList<>();
            for (String sentence : sentenceArr) {
                if (!sentence.trim().isEmpty()) {
                    sentencesList.add(sentence.trim());
                }
            }
            
            if (sentencesList.isEmpty()) {
                return "Training failed";
            }
            
            CollectionSentenceIterator iterator = new CollectionSentenceIterator(sentencesList);
            
            DefaultTokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
            
            Word2Vec vec = new Word2Vec.Builder()
                    .minWordFrequency(min_count)
                    .layerSize(vector_size)
                    .windowSize(window)
                    .seed(42) 
                    .iterate(iterator)
                    .tokenizerFactory(tokenizerFactory)
                    .build();
            
            vec.fit();
            
            Collection<String> vocabWords = vec.getVocab().words();
            if (vocabWords == null || vocabWords.isEmpty()) {
                return "Training failed";
            }
            List<String> wordsList = new ArrayList<>(vocabWords);
            
            return String.join("|", wordsList);
        } catch (Exception e) {
            return "Training failed";
        }
    }
}