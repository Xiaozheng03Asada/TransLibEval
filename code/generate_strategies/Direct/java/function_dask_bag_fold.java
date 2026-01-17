package com.example;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import java.util.Arrays;

public class CumulativeSumFunction {

    public String cumulative_sum(String inputStr) {
        try {
            double[] inputList = Arrays.stream(inputStr.split(","))
                    .mapToDouble(Double::parseDouble)
                    .toArray();

            DescriptiveStatistics stats = new DescriptiveStatistics();
            Arrays.stream(inputList).forEach(stats::addValue);

            double result = stats.getSum();

            return String.valueOf(result);
        } catch (Exception e) {
            return "Error";
        }
    }
}