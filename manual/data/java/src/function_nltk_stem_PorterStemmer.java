package com.example;

import org.tartarus.snowball.ext.PorterStemmer;

public class Stemmer {
    public String test_stem(String word) {
        org.tartarus.snowball.ext.PorterStemmer porterStemmer = new PorterStemmer();
        porterStemmer.setCurrent(word);
        porterStemmer.stem();
        return porterStemmer.getCurrent();
    }
}