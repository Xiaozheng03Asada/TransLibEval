package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_spacy_dep {

    private function_spacy_dep spacyDep;

    @BeforeEach
    public void setUp() {
        spacyDep = new function_spacy_dep();
    }

    @Test
    public void testEmptyText() {
        String result = spacyDep.test_dep("");
        assertNotNull(result);
        assertEquals("", result);
    }

    @Test
    public void testSubjectVerbObjectSentence() {
        String text = "I love flowers";
        String result = spacyDep.test_dep(text);
        assertEquals("I (nsubj), love (ROOT), flowers (dobj)", result);
    }

    @Test
    public void testComplexSentence() {
        String text = "The quick brown fox jumped over the lazy dog";
        String expected = "The (det), quick (amod), brown (amod), fox (nsubj), jumped (ROOT), over (prep), the (det), lazy (amod), dog (pobj)";
        String result = spacyDep.test_dep(text);
        assertEquals(expected, result);
    }

    @Test
    public void testWordWithDifferentDependencies() {
        String result1 = spacyDep.test_dep("The dog barked loudly.");
        String result2 = spacyDep.test_dep("I saw the dog with a collar.");

        String expectedResult1 = "The (det), dog (nsubj), barked (ROOT), loudly (advmod)";
        String expectedResult2 = "I (nsubj), saw (ROOT), the (det), dog (dobj), with (prep), a (det), collar (pobj)";

        assertEquals(expectedResult1, result1);
        assertEquals(expectedResult2, result2);
    }

    @Test
    public void testNullInput() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            spacyDep.test_dep(null);
        });
        String expectedMessage = "The input must be of string type.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}