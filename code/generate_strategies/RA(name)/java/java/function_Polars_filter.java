package com.example;

import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.apache.commons.math3.util.MathArrays;
import java.util.ArrayList;
import java.util.List;

public class PolarsExample {
    public Float filter_numbers(float threshold, float x, Float y, Float z) {
        // 收集非null的值
        List<Float> valuesList = new ArrayList<>();
        valuesList.add(x);
        if (y != null) valuesList.add(y);
        if (z != null) valuesList.add(z);

        if (valuesList.isEmpty()) {
            return null;
        }

        // 转换为double数组并过滤
        double[] values = valuesList.stream()
                .mapToDouble(Float::doubleValue)
                .filter(val -> val > threshold)
                .toArray();

        if (values.length == 0) {
            return null;
        }

        // 使用Apache Commons Math来找最大值
        Max maxCalculator = new Max();
        double maxValue = maxCalculator.evaluate(values);

        return (float) maxValue;
    }
}