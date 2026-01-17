package com.example;

import org.apache.commons.math3.stat.StatUtils;

public class CatBoostFeatureImportance {
    // 模拟存储模型和训练数据状态的字段
    private boolean modelInitialized = false;
    private boolean trainPoolInitialized = false;
    private double importanceValue = 0.0;

    // 唯一方法 process 实现所有功能，输入输出均为 int、float、String 或 null
    public float process(String action, float feature1, float feature2, float feature3, Integer label, String importance_type) {
        // 初始化模型（相当于 Python 中 if not hasattr(self, 'model')）
        if (!modelInitialized) {
            // 模拟创建 CatBoostClassifier 模型，使用第三方依赖 Apache Commons Math3 计算均值（后面用到）
            modelInitialized = true;
            trainPoolInitialized = false;
        }

        if ("train".equals(action)) {
            if (label == null) {
                throw new IllegalArgumentException("Label is required for training.");
            }
            // 构造训练样本
            // sample1 = (feature1, feature2, feature3)
            // sample2 = (feature1 + 0.1, feature2 - 0.2, feature3 + 0.3)
            // sample3 = (feature1 - 0.2, feature2 + 0.1, feature3 - 0.1)
            // 训练标签为 [label, 1 - label, label]
            // 模拟模型的 fit 操作，这里简单将 importanceValue 设为 (feature1 + feature2 + feature3 + label)
            importanceValue = feature1 + feature2 + feature3 + label;
            trainPoolInitialized = true;
            return 0.0f;
        } else if ("importance".equals(action)) {
            if (!("PredictionValuesChange".equals(importance_type) || "LossFunctionChange".equals(importance_type) || "ShapValues".equals(importance_type))) {
                throw new IllegalArgumentException("Invalid importance type. Choose from 'PredictionValuesChange', 'LossFunctionChange', or 'ShapValues'.");
            }
            double result;
            if ("LossFunctionChange".equals(importance_type) || "ShapValues".equals(importance_type)) {
                if (!trainPoolInitialized) {
                    throw new IllegalArgumentException("Training dataset is required for this importance type.");
                }
                if ("LossFunctionChange".equals(importance_type)) {
                    // 模拟根据训练数据获取特征重要性，此处返回 importanceValue 加 1.0
                    result = importanceValue + 1.0;
                } else { // "ShapValues"
                    // 模拟获取 ShapValues 返回二维数组，并计算均值
                    double[] importances = new double[] { importanceValue + 2.0, importanceValue + 2.5, importanceValue + 3.0 };
                    result = StatUtils.mean(importances);
                }
            } else { // "PredictionValuesChange"
                // 对于 PredictionValuesChange，不需要训练数据，直接返回 importanceValue
                result = importanceValue;
            }
            return (float) result;
        } else {
            throw new IllegalArgumentException("Invalid action. Use 'train' or 'importance'.");
        }
    }
}