package com.example;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class MeanSquaredErrorCalculator {
    public String calculate(String X, String y) {
        
        String[] xValues = X.split(",");
        String[] yValues = y.split(",");

        double[] xArray = new double[xValues.length];
        double[] yArray = new double[yValues.length];

        
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException("The number of samples in X and y must be the same.");
        }

        
        for (int i = 0; i < xValues.length; i++) {
            xArray[i] = Double.parseDouble(xValues[i]);
            yArray[i] = Double.parseDouble(yValues[i]);
        }

        
        SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < xArray.length; i++) {
            regression.addData(xArray[i], yArray[i]);
        }

        
        double[] yPred = new double[xArray.length];
        for (int i = 0; i < xArray.length; i++) {
            yPred[i] = regression.predict(xArray[i]);
        }

        
        double mse = 0.0;
        for (int i = 0; i < yArray.length; i++) {
            double diff = yArray[i] - yPred[i];
            mse += diff * diff;
        }
        mse /= yArray.length;

        return String.valueOf(mse);
    }
}