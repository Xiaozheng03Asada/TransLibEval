package com.example;

import org.apache.commons.math3.stat.descriptive.rank.Max;
import java.util.Arrays;

public class CVXPYMaxFunction {
    public String compute_max_value(String vector) {
        try {
            // 去除字符串中的方括号并分割成字符串数组
            String[] strArray = vector.replaceAll("[\\[\\]\\s]", "").split(",");

            // 将字符串数组转换为double数组
            double[] numbers = Arrays.stream(strArray)
                    .map(String::trim)
                    .mapToDouble(Double::parseDouble)
                    .toArray();

            // 检查NaN值
            if (Arrays.stream(numbers).anyMatch(Double::isNaN)) {
                throw new IllegalArgumentException("Input contains NaN values");
            }

            // 使用Apache Commons Math计算最大值
            Max max = new Max();
            double maxValue = max.evaluate(numbers);

            // 转换为字符串并返回
            return String.valueOf(maxValue);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input format.");
        }
    }
}