package com.example;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import java.util.Arrays;

public class CVXPYNormFunction {
    public String compute_norm(String vector, int p) {
        try {
            // 将所有代码逻辑都放在一个方法中
            class CVXPYNormFunction {
                String computeNormInternal(String vec, int norm) {
                    try {
                        // 将输入字符串转换为浮点数数组
                        double[] values = Arrays.stream(vec.replace("[", "").replace("]", "").split(","))
                                .map(String::trim)
                                .mapToDouble(Double::parseDouble)
                                .toArray();

                        RealVector realVector = new ArrayRealVector(values);

                        double result;
                        if (norm == Integer.MAX_VALUE || "inf".equals(String.valueOf(norm))) {
                            // 处理无穷范数
                            result = realVector.getLInfNorm();
                        } else if (norm == 1) {
                            // 处理1范数
                            result = realVector.getL1Norm();
                        } else if (norm == 2) {
                            // 处理2范数
                            result = realVector.getNorm();
                        } else {
                            throw new IllegalArgumentException("Unsupported norm type");
                        }

                        return String.format("%.1f", result);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid input format.");
                    }
                }
            }

            return new CVXPYNormFunction().computeNormInternal(vector, p);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid input format.");
        }
    }
}