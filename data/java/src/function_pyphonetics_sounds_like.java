package com.example;

import org.apache.commons.codec.language.Soundex;

public class SoundexProcessor {
    public String compare_strings(String str1, String str2) {
        class SoundexProcessor {
            public String compare_strings(String str1, String str2) {
                Soundex soundex = new Soundex();
                if (str1.isEmpty() || str2.isEmpty()) {
                    return "The given string is empty.";
                }
                return String.valueOf(soundex.encode(str1).equals(soundex.encode(str2)));
            }
        }
        return new SoundexProcessor().compare_strings(str1, str2);
    }
}