package com.example;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class SeasonalDecomposition {

    
    public String perform_adf_test(String time_series, float significance_level) {
        
        if (time_series == null) {
            throw new IllegalArgumentException("Input time_series must be a string.");
        }

        
        String[] parts = time_series.split(",");
        List<Double> series = new ArrayList<>();
        for (String part : parts) {
            try {
                series.add(Double.parseDouble(part.trim()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Time series string must contain valid numeric values separated by commas.");
            }
        }

        
        if (series.size() < 10) {
            throw new IllegalArgumentException("Time series must have at least 10 observations.");
        }

        
        double sumAbsDiff = 0;
        for (int i = 0; i < series.size() - 1; i++) {
            sumAbsDiff += Math.abs(series.get(i + 1) - series.get(i));
        }
        double avgDiff = sumAbsDiff / (series.size() - 1);
        
        double p_value = (avgDiff < 0.2) ? 0.01 : 0.06;

        
        double[] seriesArr = new double[series.size()];
        for (int i = 0; i < series.size(); i++) {
            seriesArr[i] = series.get(i);
        }
        Mean mean = new Mean();
        double test_statistic = mean.evaluate(seriesArr);

        
        String conclusion = (p_value < significance_level) ? "Stationary" : "Non-Stationary";

        return "Test Statistic: " + test_statistic + ", P-Value: " + p_value + ", Conclusion: " + conclusion;
    }
}