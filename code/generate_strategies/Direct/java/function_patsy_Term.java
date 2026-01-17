package com.example;

import org.apache.commons.math3.analysis.function.Add;
import org.apache.commons.math3.analysis.function.Multiply;
import org.apache.commons.math3.analysis.function.Divide;
import org.apache.commons.math3.analysis.function.Subtract;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;

public class PatsyTerm {
    public String generate_and_convert_term(String formula) {
        class Term {
            private final String formula;

            public Term(String formula) {
                this.formula = formula;
            }

            @Override
            public String toString() {
                if (formula == null || formula.trim().isEmpty()) {
                    return "";
                }

                // 首先在运算符两边添加空格
                String processed = formula
                        .replaceAll("\\s*\\+\\s*", " + ")
                        .replaceAll("\\s*-\\s*", " - ")
                        .replaceAll("\\s*\\*\\s*", " * ")
                        .replaceAll("\\s*/\\s*", " / ")
                        .trim();

                // 处理x后面的数字
                StringBuilder result = new StringBuilder();
                String[] parts = processed.split("\\s+");

                for (int i = 0; i < parts.length; i++) {
                    String part = parts[i];
                    if (part.startsWith("x")) {
                        result.append("x ").append(part.substring(1));
                    } else {
                        result.append(part);
                    }

                    // 如果不是最后一个部分，添加空格
                    if (i < parts.length - 1) {
                        result.append(" ");
                    }
                }

                return result.toString();
            }
        }

        try {
            Term term = new Term(formula);
            return term.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error in generating Term from formula: " + e.getMessage());
        }
    }
}