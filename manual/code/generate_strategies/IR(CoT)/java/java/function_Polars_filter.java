package com.example;

import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.apache.commons.math3.util.MathArrays;
import java.util.ArrayList;
import java.util.List;

public class PolarsExample {
    public Float filter_numbers(float threshold, float x, Float y, Float z) {
        
        List<Float> valuesList = new ArrayList<>();
        valuesList.add(x);
        if (y != null) valuesList.add(y);
        if (z != null) valuesList.add(z);

        if (valuesList.isEmpty()) {
            return null;
        }

        
        double[] values = valuesList.stream()
                .mapToDouble(Float::doubleValue)
                .filter(val -> val > threshold)
                .toArray();

        if (values.length == 0) {
            return null;
        }

        
        Max maxCalculator = new Max();
        double maxValue = maxCalculator.evaluate(values);

        return (float) maxValue;
    }
}