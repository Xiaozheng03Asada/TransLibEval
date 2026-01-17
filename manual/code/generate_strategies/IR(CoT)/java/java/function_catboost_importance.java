package com.example;

import org.apache.commons.math3.stat.StatUtils;

public class CatBoostFeatureImportance {
    
    private boolean modelInitialized = false;
    private boolean trainPoolInitialized = false;
    private double importanceValue = 0.0;

    
    public float process(String action, float feature1, float feature2, float feature3, Integer label, String importance_type) {
        
        if (!modelInitialized) {
            
            modelInitialized = true;
            trainPoolInitialized = false;
        }

        if ("train".equals(action)) {
            if (label == null) {
                throw new IllegalArgumentException("Label is required for training.");
            }
            
            
            
            
            
            
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
                    
                    result = importanceValue + 1.0;
                } else { 
                    
                    double[] importances = new double[] { importanceValue + 2.0, importanceValue + 2.5, importanceValue + 3.0 };
                    result = StatUtils.mean(importances);
                }
            } else { 
                
                result = importanceValue;
            }
            return (float) result;
        } else {
            throw new IllegalArgumentException("Invalid action. Use 'train' or 'importance'.");
        }
    }
}