package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PatsyParser {
    public String parse_formula(String formula) {
        class FormulaParseException extends Exception {
            public FormulaParseException() {
                super("Invalid formula format");
            }
        }

        if (formula == null) {
            return "Error: Formula must be a string.";
        }

        try {
            // 使用正则表达式解析公式
            Pattern pattern = Pattern.compile("(.*?)\\s*~\\s*(.*)");
            Matcher matcher = pattern.matcher(formula);

            if (!matcher.matches()) {
                throw new FormulaParseException();
            }

            String lhs = matcher.group(1).trim();
            String rhs = matcher.group(2).trim();

            // 验证右侧表达式的有效性
            if (rhs.endsWith("+") || rhs.endsWith("~")) {
                throw new FormulaParseException();
            }

            // 计算左侧项数
            int lhsTerms = lhs.isEmpty() ? 0 : 1;

            // 计算右侧项数
            int rhsTerms = 0;
            if (!rhs.isEmpty()) {
                // 处理 -1 的情况
                String processedRhs = rhs.replaceAll("\\s*-\\s*1\\s*$", "").trim();
                boolean hasIntercept = !rhs.matches(".*-\\s*1\\s*$");

                // 确保表达式没有连续的+号或其他无效字符
                if (processedRhs.matches(".*[+]\\s*[+].*") ||
                        processedRhs.matches(".*[+]\\s*$")) {
                    throw new FormulaParseException();
                }

                if (!processedRhs.isEmpty()) {
                    String[] terms = processedRhs.split("\\s*\\+\\s*");
                    rhsTerms = terms.length;
                }

                // 如果有截距项，增加项数
                if (hasIntercept) {
                    rhsTerms++;
                }
            }

            // 创建JSON响应
            Map<String, Integer> result = new HashMap<>();
            result.put("lhs_terms", lhsTerms);
            result.put("rhs_terms", rhsTerms);

            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(result);

        } catch (Exception e) {
            return "Error: Failed to parse formula.";
        }
    }
}