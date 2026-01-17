package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class test_sklearn_StandardScaler {

    @Test
    public void test_shape_consistency() {
        function_sklearn_StandardScaler standardScaler = new function_sklearn_StandardScaler();
        String data = "1.0,-1.0,2.0;2.0,0.0,0.0;0.0,1.0,-1.0;3.0,2.0,1.0";
        String result = standardScaler.quick_sort_from_string(data);
        assertTrue(result instanceof String);
    }

    @Test
    public void test_mean_value_near_zero() {
        function_sklearn_StandardScaler standardScaler = new function_sklearn_StandardScaler();
        String data = "1.0,-1.0,2.0;2.0,0.0,0.0;0.0,1.0,-1.0;3.0,2.0,1.0";
        String result = standardScaler.quick_sort_from_string(data);

        // Convert result back to array for validation
        String[] rows = result.split(";");
        double[][] resultArray = new double[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] elements = rows[i].split(",");
            resultArray[i] = new double[elements.length];
            for (int j = 0; j < elements.length; j++) {
                resultArray[i][j] = Double.parseDouble(elements[j]);
            }
        }

        // Calculate mean for each column
        for (int j = 0; j < resultArray[0].length; j++) {
            double columnMean = 0.0;
            for (double[] doubles : resultArray) {
                columnMean += doubles[j];
            }
            columnMean /= resultArray.length;
            assertTrue(Math.abs(columnMean) < 0.1);
        }
    }

    @Test
    public void test_std_value_near_one() {
        function_sklearn_StandardScaler standardScaler = new function_sklearn_StandardScaler();
        String data = "1.0,-1.0,2.0;2.0,0.0,0.0;0.0,1.0,-1.0;3.0,2.0,1.0";
        String result = standardScaler.quick_sort_from_string(data);

        String[] rows = result.split(";");
        double[][] resultArray = new double[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] elements = rows[i].split(",");
            resultArray[i] = new double[elements.length];
            for (int j = 0; j < elements.length; j++) {
                resultArray[i][j] = Double.parseDouble(elements[j]);
            }
        }

        // Calculate standard deviation for each column
        for (int j = 0; j < resultArray[0].length; j++) {
            double mean = 0.0;
            for (double[] row : resultArray) {
                mean += row[j];
            }
            mean /= resultArray.length;

            double variance = 0.0;
            for (double[] row : resultArray) {
                variance += Math.pow(row[j] - mean, 2);
            }
            variance /= resultArray.length;
            double std = Math.sqrt(variance);

            assertTrue(Math.abs(std - 1.0) < 0.1);
        }
    }

    @Test
    public void test_invalid_data_type() {
        function_sklearn_StandardScaler standardScaler = new function_sklearn_StandardScaler();
        String data = "a,2;3,b;5,6";
        assertThrows(IllegalArgumentException.class, () ->
                standardScaler.quick_sort_from_string(data));
    }

    @Test
    public void test_2d_array_data() {
        function_sklearn_StandardScaler standardScaler = new function_sklearn_StandardScaler();
        String data = "[[[1, 2], [3, 4]], [[5, 6], [7, 8]]]";
        assertThrows(IllegalArgumentException.class, () ->
                standardScaler.quick_sort_from_string(data));
    }
}