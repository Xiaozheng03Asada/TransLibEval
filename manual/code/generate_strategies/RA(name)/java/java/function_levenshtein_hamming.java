package com.example;

import org.apache.commons.text.similarity.HammingDistance;

public class StringProcessor {
    public String calculate_hamming_distance(String str1, String str2) {
        // 主方法，包含所有逻辑
        class StringProcessor {
            public String calculate_hamming_distance(String str1, String str2) {
                // 检查输入是否为null
                if (str1 == null || str2 == null) {
                    return "Error: Both inputs must be strings";
                }

                // 检查字符串长度是否相等
                if (str1.length() != str2.length()) {
                    return "Strings must be of the same length for Hamming distance.";
                }

                // 使用Apache Commons Text库计算Hamming距离
                HammingDistance hammingDistance = new HammingDistance();
                return String.valueOf(hammingDistance.apply(str1, str2));
            }
        }

        // 创建内部类实例并调用方法
        StringProcessor processor = new StringProcessor();
        return processor.calculate_hamming_distance(str1, str2);
    }
}