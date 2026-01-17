package com.example;

import java.util.*;
import java.util.stream.Collectors;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;

public class Word2VecModelTrainer {

    public String train_word2vec_model(String sentences_str) {
        try {
            
            List<String> sentenceList = Arrays.stream(sentences_str.split(";"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            
            if(sentenceList.isEmpty()){
                return "Training failed";
            }

            
            CollectionSentenceIterator sentenceIterator = new CollectionSentenceIterator(sentenceList);
            
            DefaultTokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();

            
            Word2Vec vec = new Word2Vec.Builder()
                    .minWordFrequency(1)
                    .iterations(1)
                    .layerSize(50)
                    .windowSize(3)
                    .seed(42)
                    .iterate(sentenceIterator)
                    .tokenizerFactory(tokenizerFactory)
                    .workers(4)
                    .build();
            vec.fit();

            
            List<String> vocab = new ArrayList<>(vec.getVocab().words());
            
            return String.join("|", vocab);
        } catch(Exception e) {
            return "Training failed";
        }
    }
}