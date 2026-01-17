package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_patsy_ModelDesc_from_formula {

    private function_patsy_ModelDesc_from_formula parser;

    @BeforeEach
    void setUp() {
        parser = new function_patsy_ModelDesc_from_formula();
    }

    @Test
    void testSimpleFormula() {
        String formula = "y ~ x1 + x2";
        String result = parser.parse_formula(formula);
        assertTrue(result.contains("\"lhs_terms\":1"));
        assertTrue(result.contains("\"rhs_terms\":3"));
    }

    @Test
    void testFormulaWithNoResponse() {
        String formula = "~ x1 + x2";
        String result = parser.parse_formula(formula);
        assertTrue(result.contains("\"lhs_terms\":0"));
        assertTrue(result.contains("\"rhs_terms\":3"));
    }

    @Test
    void testInvalidFormula() {
        String formula = "y ~ x1 +";
        String result = parser.parse_formula(formula);
        assertEquals("Error: Failed to parse formula.", result);
    }

    @Test
    void testNonStringInput() {
        String formula = null;
        String result = parser.parse_formula(formula);
        assertEquals("Error: Formula must be a string.", result);
    }

    @Test
    void testFormulaNoIntercept() {
        String formula = "y ~ x1 + x2 - 1";
        String result = parser.parse_formula(formula);
        assertTrue(result.contains("\"rhs_terms\":2"));
    }
}