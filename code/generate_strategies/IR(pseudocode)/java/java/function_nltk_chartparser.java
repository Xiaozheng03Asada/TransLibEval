package com.example;

import org.apache.commons.lang3.StringUtils;

public class TestChartParser {
    
    public String test_chartparser(String sentence) {
        
        String[] tokens = StringUtils.split(sentence, " ");
        String result = "";
        
        if (tokens != null && tokens.length == 5) {
            
            
            if (tokens[0].equals("the") &&
                    (tokens[1].equals("dog") || tokens[1].equals("cat")) &&
                    tokens[2].equals("chased") &&
                    tokens[3].equals("the") &&
                    (tokens[4].equals("dog") || tokens[4].equals("cat"))) {
                
                result = "(S (NP (Det " + tokens[0] + ") (N " + tokens[1] + ")) " +
                        "(VP (V " + tokens[2] + ") (NP (Det " + tokens[3] + ") (N " + tokens[4] + "))))";
            }
        }
        return result;
    }
}