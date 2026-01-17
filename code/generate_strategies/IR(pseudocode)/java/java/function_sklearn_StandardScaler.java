package com.example;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class StandardScalerFunction {
    public String quick_sort_from_string(String inputStr) {
        if (inputStr == null || inputStr.trim().isEmpty()) {
            return "";
        }

        
        String[] rows = inputStr.split(";");
        List<double[]> dataList = new ArrayList<>();
        for (String row : rows) {
            String[] elements = row.split(",");
            double[] doubleRow = new double[elements.length];
            for (int i = 0; i < elements.length; i++) {
                try {
                    doubleRow[i] = Double.parseDouble(elements[i].trim());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Input data must be numeric");
                }
            }
            dataList.add(doubleRow);
        }

        double[][] data = dataList.toArray(new double[0][]);

        
        if (data.length == 0 || !Arrays.stream(data).allMatch(row -> row.length == data[0].length)) {
            throw new IllegalArgumentException("Input data must be a 2-dimensional array");
        }

        
        int numFeatures = data[0].length;
        double[] means = new double[numFeatures];
        double[] stds = new double[numFeatures];
        Mean meanCalculator = new Mean();
        StandardDeviation stdCalculator = new StandardDeviation(false);

        for (int j = 0; j < numFeatures; j++) {
            double[] column = new double[data.length];
            for (int i = 0; i < data.length; i++) {
                column[i] = data[i][j];
            }
            means[j] = meanCalculator.evaluate(column);
            stds[j] = stdCalculator.evaluate(column);
        }

        
        double[][] transformedData = new double[data.length][numFeatures];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < numFeatures; j++) {
                transformedData[i][j] = stds[j] != 0 ? (data[i][j] - means[j]) / stds[j] : 0.0;
            }
        }

        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < transformedData.length; i++) {
            for (int j = 0; j < transformedData[i].length; j++) {
                result.append(transformedData[i][j]);
                if (j < transformedData[i].length - 1) {
                    result.append(",");
                }
            }
            if (i < transformedData.length - 1) {
                result.append(";");
            }
        }
        return result.toString();
    }
}