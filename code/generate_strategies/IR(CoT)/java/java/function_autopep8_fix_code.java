package com.example;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;

public class CodeFormatter {
    public String format_code(String code) {
        class CodeFormatter {
            public String format_code(String code) {
                try {
                    return new Formatter().formatSource(code);
                } catch (FormatterException e) {
                    
                    return code + (code.endsWith("\n") ? "" : "\n");
                }
            }
        }
        return new CodeFormatter().format_code(code);
    }
}