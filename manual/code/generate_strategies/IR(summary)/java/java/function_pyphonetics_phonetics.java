package com.example;

import org.apache.commons.codec.language.Soundex;

public class SoundexProcessor {
    public String generate_soundex(String input_string) {
        class SoundexProcessor {
            public String generate_soundex(String input_string) {
                if (input_string.equals("")) {
                    return "The given string is empty.";
                }
                Soundex soundex = new Soundex();
                return soundex.encode(input_string);
            }
        }
        return new SoundexProcessor().generate_soundex(input_string);
    }
}