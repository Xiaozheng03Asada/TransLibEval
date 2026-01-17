package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_patsy_Term {
    private function_patsy_Term term;

    @BeforeEach
    void setUp() {
        term = new function_patsy_Term();
    }

    @Test
    void test_generate_and_convert_term() {
        String termStr = term.generate_and_convert_term("x1 + x2 + x3");
        assertEquals("x 1 + x 2 + x 3", termStr);
    }

    @Test
    void test_empty_formula() {
        String termStr = term.generate_and_convert_term("");
        assertEquals("", termStr);
    }

    @Test
    void test_complex_formula_with_mixed_interactions() {
        String termStr = term.generate_and_convert_term("x1 * x2 + x3 / x4 - x5 + x6");
        assertEquals("x 1 * x 2 + x 3 / x 4 - x 5 + x 6", termStr);
    }

    @Test
    void test_formula_with_interaction() {
        String termStr = term.generate_and_convert_term("x1 * x2 + x3");
        assertEquals("x 1 * x 2 + x 3", termStr);
    }

    @Test
    void test_multiple_interaction_terms() {
        String termStr = term.generate_and_convert_term("x1 * x2 + x3 / x4 + x5");
        assertEquals("x 1 * x 2 + x 3 / x 4 + x 5", termStr);
    }
}