package com.example;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class SeasonalDecomposition {

    // 所有逻辑均在此唯一的方法中实现，方法名与python版本一致
    public String perform_adf_test(String time_series, float significance_level) {
        // 检查输入是否为非空字符串
        if (time_series == null) {
            throw new IllegalArgumentException("Input time_series must be a string.");
        }

        // 将字符串以逗号为分隔符解析为浮点数列表
        String[] parts = time_series.split(",");
        List<Double> series = new ArrayList<>();
        for (String part : parts) {
            try {
                series.add(Double.parseDouble(part.trim()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Time series string must contain valid numeric values separated by commas.");
            }
        }

        // 判断是否至少包含10个观测值
        if (series.size() < 10) {
            throw new IllegalArgumentException("Time series must have at least 10 observations.");
        }

        // 计算连续观测值之间的平均绝对差值，以模拟ADF检验的p_value计算
        double sumAbsDiff = 0;
        for (int i = 0; i < series.size() - 1; i++) {
            sumAbsDiff += Math.abs(series.get(i + 1) - series.get(i));
        }
        double avgDiff = sumAbsDiff / (series.size() - 1);
        // 模拟的p_value：如果平均差值小于0.2，则认为平稳，否则非平稳
        double p_value = (avgDiff < 0.2) ? 0.01 : 0.06;

        // 使用Apache Commons Math计算均值（第三方依赖）作为测试统计量
        double[] seriesArr = new double[series.size()];
        for (int i = 0; i < series.size(); i++) {
            seriesArr[i] = series.get(i);
        }
        Mean mean = new Mean();
        double test_statistic = mean.evaluate(seriesArr);

        // 根据p_value与显著性水平比较得到结论
        String conclusion = (p_value < significance_level) ? "Stationary" : "Non-Stationary";

        return "Test Statistic: " + test_statistic + ", P-Value: " + p_value + ", Conclusion: " + conclusion;
    }
}