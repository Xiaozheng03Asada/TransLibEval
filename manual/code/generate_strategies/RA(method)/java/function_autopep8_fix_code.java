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
                    // 如果格式化失败，返回原始代码
                    return code + (code.endsWith("\n") ? "" : "\n");
                }
            }
        }
        return new CodeFormatter().format_code(code);
    }
}