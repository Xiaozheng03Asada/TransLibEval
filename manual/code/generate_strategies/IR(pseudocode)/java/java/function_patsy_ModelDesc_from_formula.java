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
            
            Pattern pattern = Pattern.compile("(.*?)\\s*~\\s*(.*)");
            Matcher matcher = pattern.matcher(formula);

            if (!matcher.matches()) {
                throw new FormulaParseException();
            }

            String lhs = matcher.group(1).trim();
            String rhs = matcher.group(2).trim();

            
            if (rhs.endsWith("+") || rhs.endsWith("~")) {
                throw new FormulaParseException();
            }

            
            int lhsTerms = lhs.isEmpty() ? 0 : 1;

            
            int rhsTerms = 0;
            if (!rhs.isEmpty()) {
                
                String processedRhs = rhs.replaceAll("\\s*-\\s*1\\s*$", "").trim();
                boolean hasIntercept = !rhs.matches(".*-\\s*1\\s*$");

                
                if (processedRhs.matches(".*[+]\\s*[+].*") ||
                        processedRhs.matches(".*[+]\\s*$")) {
                    throw new FormulaParseException();
                }

                if (!processedRhs.isEmpty()) {
                    String[] terms = processedRhs.split("\\s*\\+\\s*");
                    rhsTerms = terms.length;
                }

                
                if (hasIntercept) {
                    rhsTerms++;
                }
            }

            
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