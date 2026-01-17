package com.example;

import org.apache.commons.math3.stat.StatUtils;

public class SeasonalDecomposition {

    public String perform_seasonal_decomposition(String data_str, String model, Integer period) {
        // 1. 检查输入是否为字符串（Java中如果传入null则认为不是有效字符串）
        if (data_str == null) {
            throw new IllegalArgumentException("Input data must be a string.");
        }

        // 2. 将字符串按逗号分割并转换为double数组（逐行翻译Python的列表解析）
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

        // 3. 校验model是否为"additive"或者"multiplicative"
        if (!("additive".equals(model) || "multiplicative".equals(model))) {
            throw new IllegalArgumentException("Model must be 'additive' or 'multiplicative'.");
        }

        // 4. 校验period，如果不为null，则必须是正整数
        if (period != null && period <= 0) {
            throw new IllegalArgumentException("Period must be a positive integer.");
        }

        // 5. 利用第三方依赖Apache Commons Math计算趋势：即数据的均值
        double trend = StatUtils.mean(data);

        // 6. 模拟 seasonal 计算：np.sin(np.linspace(0, 2 * np.pi, len(data)))
        double[] seasonal = new double[n];
        if (n == 1) {
            seasonal[0] = Math.sin(0);
        } else {
            for (int i = 0; i < n; i++) {
                double value = i * (2 * Math.PI) / (n - 1);
                seasonal[i] = Math.sin(value);
            }
        }

        // 7. 计算 residual = data - trend - seasonal（逐元素相减）
        double[] residual = new double[n];
        for (int i = 0; i < n; i++) {
            residual[i] = data[i] - trend - seasonal[i];
        }

        // 8. 将各部分结果转换为字符串（拼接各元素，以", "分隔）
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

        // 9. 拼接返回结果字符串，格式与原python代码一致
        String result = "Trend: " + trend +
                ", Seasonal: " + seasonalStr.toString() +
                ", Residual: " + residualStr.toString() +
                ", Observed: " + observedStr.toString();
        return result;
    }
}