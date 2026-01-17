package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_mlxtend_SequentialFeatureSelector {

    @Test
    public void test_single_feature() {
        function_mlxtend_SequentialFeatureSelector fs = new function_mlxtend_SequentialFeatureSelector();
        String result = fs.sequential_feature_selection("sepal length", "species", 1);
        assertEquals("[petal width (cm)]", result);
    }

    @Test
    public void test_two_features() {
        function_mlxtend_SequentialFeatureSelector fs = new function_mlxtend_SequentialFeatureSelector();
        String result = fs.sequential_feature_selection("sepal length", "species", 2);
        assertEquals("[petal length (cm), petal width (cm)]", result);
    }

    @Test
    public void test_all_features() {
        function_mlxtend_SequentialFeatureSelector fs = new function_mlxtend_SequentialFeatureSelector();
        String result = fs.sequential_feature_selection("sepal length", "species", 4);
        assertEquals("[petal length (cm), petal width (cm), sepal length (cm), sepal width (cm)]", result);
    }

    @Test
    public void test_invalid_feature_count() {
        function_mlxtend_SequentialFeatureSelector fs = new function_mlxtend_SequentialFeatureSelector();
        String result = fs.sequential_feature_selection("sepal length", "species", 0);
        assertEquals("[]", result);
    }

    @Test
    public void test_no_features() {
        function_mlxtend_SequentialFeatureSelector fs = new function_mlxtend_SequentialFeatureSelector();
        String result = fs.sequential_feature_selection("sepal length", "species", 5);
        assertEquals("[]", result);
    }
}