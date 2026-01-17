package com.example;

import org.apache.commons.math3.stat.StatUtils;

public class SeasonalDecomposition {

    public String perform_seasonal_decomposition(String data_str, String model, Integer period) {
        
        if (data_str == null) {
            throw new IllegalArgumentException("Input data must be a string.");
        }

        
        String[] parts = data_str.split(",");
        int n = parts.length;
        double[] data = new double[n];
        for (int i = 0; i < n; i++) {
            try {
                data[i] = Double.parseDouble(parts[i].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number in input data.");
            }
        }

        
        if (!("additive".equals(model) || "multiplicative".equals(model))) {
            throw new IllegalArgumentException("Model must be 'additive' or 'multiplicative'.");
        }

        
        if (period != null && period <= 0) {
            throw new IllegalArgumentException("Period must be a positive integer.");
        }

        
        double trend = StatUtils.mean(data);

        
        double[] seasonal = new double[n];
        if (n == 1) {
            seasonal[0] = Math.sin(0);
        } else {
            for (int i = 0; i < n; i++) {
                double value = i * (2 * Math.PI) / (n - 1);
                seasonal[i] = Math.sin(value);
            }
        }

        
        double[] residual = new double[n];
        for (int i = 0; i < n; i++) {
            residual[i] = data[i] - trend - seasonal[i];
        }

        
        StringBuilder seasonalStr = new StringBuilder();
        StringBuilder residualStr = new StringBuilder();
        StringBuilder observedStr = new StringBuilder();
        for (int i = 0; i < n; i++) {
            seasonalStr.append(seasonal[i]);
            residualStr.append(residual[i]);
            observedStr.append(data[i]);
            if (i < n - 1) {
                seasonalStr.append(", ");
                residualStr.append(", ");
                observedStr.append(", ");
            }
        }

        
        String result = "Trend: " + trend +
                ", Seasonal: " + seasonalStr.toString() +
                ", Residual: " + residualStr.toString() +
                ", Observed: " + observedStr.toString();
        return result;
    }
}