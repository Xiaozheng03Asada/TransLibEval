package com.example;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.util.MathArrays;
import java.util.Arrays;
import java.util.stream.DoubleStream;

public class CalculateMeanFunction {
    public String calculate_mean(String dataStr, int chunks) {
        try {
            if (dataStr.isEmpty()) {
                throw new IllegalArgumentException("Empty input");
            }

            // 将输入字符串转换为double数组
            double[] data = Arrays.stream(dataStr.split(","))
                    .mapToDouble(Double::parseDouble)
                    .toArray();

            // 使用chunks将数组分块
            double[][] blocks = new double[chunks][];
            int blockSize = (int) Math.ceil((double) data.length / chunks);

            // 分块处理
            for (int i = 0; i < chunks; i++) {
                int start = i * blockSize;
                int end = Math.min(start + blockSize, data.length);
                if (start < data.length) {
                    blocks[i] = Arrays.copyOfRange(data, start, end);
                } else {
                    blocks[i] = new double[0];
                }
            }

            // 计算每个块的平均值
            double[] blockMeans = Arrays.stream(blocks)
                    .filter(block -> block.length > 0)
                    .mapToDouble(block -> {
                        DescriptiveStatistics stats = new DescriptiveStatistics(block);
                        return stats.getMean();
                    })
                    .toArray();

            // 计算总体平均值
            DescriptiveStatistics finalStats = new DescriptiveStatistics(blockMeans);
            return String.valueOf(finalStats.getMean());
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}