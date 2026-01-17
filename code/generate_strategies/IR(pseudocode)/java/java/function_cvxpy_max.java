package com.example;

import org.apache.commons.math3.stat.descriptive.rank.Max;
import java.util.Arrays;

public class CVXPYMaxFunction {
    public String compute_max_value(String vector) {
        try {
            
            String[] strArray = vector.replaceAll("[\\[\\]\\s]", "").split(",");

            
            double[] numbers = Arrays.stream(strArray)
                    .map(String::trim)
                    .mapToDouble(Double::parseDouble)
                    .toArray();

            
            if (Arrays.stream(numbers).anyMatch(Double::isNaN)) {
                throw new IllegalArgumentException("Input contains NaN values");
            }

            
            Max max = new Max();
            double maxValue = max.evaluate(numbers);

            
            return String.valueOf(maxValue);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input format.");
        }
    }
}