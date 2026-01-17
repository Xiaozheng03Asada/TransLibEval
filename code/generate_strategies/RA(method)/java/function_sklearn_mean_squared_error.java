package com.example;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class MeanSquaredErrorCalculator {
    public String calculate(String X, String y) {
        // 将输入字符串转换为浮点数数组
        String[] xValues = X.split(",");
        String[] yValues = y.split(",");

        double[] xArray = new double[xValues.length];
        double[] yArray = new double[yValues.length];

        // 检查输入数组长度是否相等
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException("The number of samples in X and y must be the same.");
        }

        // 转换字符串为浮点数
        for (int i = 0; i < xValues.length; i++) {
            xArray[i] = Double.parseDouble(xValues[i]);
            yArray[i] = Double.parseDouble(yValues[i]);
        }

        // 创建SimpleRegression对象并拟合数据
        SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < xArray.length; i++) {
            regression.addData(xArray[i], yArray[i]);
        }

        // 计算预测值
        double[] yPred = new double[xArray.length];
        for (int i = 0; i < xArray.length; i++) {
            yPred[i] = regression.predict(xArray[i]);
        }

        // 计算均方误差
        double mse = 0.0;
        for (int i = 0; i < yArray.length; i++) {
            double diff = yArray[i] - yPred[i];
            mse += diff * diff;
        }
        mse /= yArray.length;

        return String.valueOf(mse);
    }
}