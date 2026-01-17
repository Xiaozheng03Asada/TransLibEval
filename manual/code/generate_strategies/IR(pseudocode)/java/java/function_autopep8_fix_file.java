package com.example;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;

public class AutoPep8Formatter {
    public String format_code(String code) {
        class AutoPep8Formatter {
            public String format(String input) {
                
                if (!input.contains("class ") && !input.contains("interface ")) {
                    
                    String wrappedCode = "class Temp { void method() { " + input + " } }";
                    try {
                        String formatted = new Formatter().formatSource(wrappedCode);
                        
                        String[] lines = formatted.split("\n");
                        StringBuilder result = new StringBuilder();
                        for (int i = 2; i < lines.length - 2; i++) {
                            
                            result.append(lines[i].substring(4)).append("\n");
                        }
                        return result.toString();
                    } catch (FormatterException e) {
                        return input;
                    }
                }

                try {
                    return new Formatter().formatSource(input);
                } catch (FormatterException e) {
                    return input;
                }
            }
        }
        return new AutoPep8Formatter().format(code);
    }
}