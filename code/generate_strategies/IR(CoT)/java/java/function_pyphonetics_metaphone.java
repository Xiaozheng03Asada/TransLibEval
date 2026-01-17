package com.example;

import org.apache.commons.codec.language.DoubleMetaphone;

public class PhoneticProcessor {
    public String generate_phonetics(String word) {
        class PhoneticProcessor {
            public String generate_phonetics(String word) {
                DoubleMetaphone metaphone = new DoubleMetaphone();
                if (word.equals("")) {
                    return "The given string is empty.";
                }
                
                metaphone.setMaxCodeLen(20);
                return metaphone.encode(word);
            }
        }

        return new PhoneticProcessor().generate_phonetics(word);
    }
}