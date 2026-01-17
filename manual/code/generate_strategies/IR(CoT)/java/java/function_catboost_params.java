package com.example;

import ai.catboost.CatBoostModel;
import ai.catboost.CatBoostPredictions;

public class CatBoostParamFetcher {
    public String get_model_params(int iterations, int depth, float learning_rate) {
        class CatBoostParamFetcher {
            public String getModelParams(int iterations, int depth, float learning_rate) {
                
                
                return String.format("Model Params - Iterations: %d, Depth: %d, Learning Rate: %.2f",
                        iterations, depth, learning_rate);
            }
        }

        CatBoostParamFetcher paramFetcher = new CatBoostParamFetcher();
        return paramFetcher.getModelParams(iterations, depth, learning_rate);
    }
}