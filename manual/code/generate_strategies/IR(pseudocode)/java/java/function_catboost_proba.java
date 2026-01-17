package com.example;

import weka.classifiers.functions.Logistic;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import java.util.ArrayList;

public class CatBoostProbabilityPredictor {
    public float predict_probability(float x1, float x2, float x3, float x4, float x5,
                                     int y1, int y2, int y3, int y4, int y5, float value) {
        
        class CatBoostProbabilityPredictor {
            
            private void validateInputs(float x1, float x2, float x3, float x4, float x5,
                                        int y1, int y2, int y3, int y4, int y5, float value) {
                Number[] numbers = {x1, x2, x3, x4, x5, value};
                for (Number num : numbers) {
                    if (num == null || Float.isNaN(num.floatValue())) {
                        throw new IllegalArgumentException("Feature values and input must be integers or floats.");
                    }
                }
            }

            public float predictProbability() {
                
                validateInputs(x1, x2, x3, x4, x5, y1, y2, y3, y4, y5, value);

                try {
                    
                    ArrayList<Attribute> attributes = new ArrayList<>();
                    attributes.add(new Attribute("feature"));

                    
                    ArrayList<String> classValues = new ArrayList<>();
                    classValues.add("0");
                    classValues.add("1");
                    attributes.add(new Attribute("class", classValues));

                    
                    Instances trainingData = new Instances("TrainingData", attributes, 5);
                    trainingData.setClassIndex(1);

                    
                    float[] trainValues = {x1, x2, x3, x4, x5};
                    int[] trainLabels = {y1, y2, y3, y4, y5};

                    for (int i = 0; i < 5; i++) {
                        DenseInstance instance = new DenseInstance(2);
                        instance.setValue(0, trainValues[i]);
                        instance.setValue(1, trainLabels[i]);
                        trainingData.add(instance);
                    }

                    
                    Logistic model = new Logistic();
                    model.setMaxIts(10);
                    model.setRidge(0.1);
                    model.buildClassifier(trainingData);

                    
                    Instances testData = new Instances("TestData", attributes, 1);
                    testData.setClassIndex(1);
                    DenseInstance testInstance = new DenseInstance(2);
                    testInstance.setValue(0, value);
                    testData.add(testInstance);

                    
                    double[] probabilities = model.distributionForInstance(testData.instance(0));
                    return (float) probabilities[1];
                } catch (Exception e) {
                    
                    throw new IllegalArgumentException("Error in model prediction: " + e.getMessage());
                }
            }
        }

        CatBoostProbabilityPredictor predictor = new CatBoostProbabilityPredictor();
        return predictor.predictProbability();
    }
}