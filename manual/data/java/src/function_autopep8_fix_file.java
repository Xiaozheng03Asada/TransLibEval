package com.example;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;

public class AutoPep8Formatter {
    public String format_code(String code) {
        class AutoPep8Formatter {
            public String format(String input) {
                // 检查代码是否包含类定义
                if (!input.contains("class ") && !input.contains("interface ")) {
                    // 如果没有类定义，将代码包装在一个临时类中
                    String wrappedCode = "class Temp { void method() { " + input + " } }";
                    try {
                        String formatted = new Formatter().formatSource(wrappedCode);
                        // 提取实际的代码部分
                        String[] lines = formatted.split("\n");
                        StringBuilder result = new StringBuilder();
                        for (int i = 2; i < lines.length - 2; i++) {
                            // 移除前面的缩进
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