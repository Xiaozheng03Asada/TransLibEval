package com.example;

import weka.core.Instances;
import weka.core.DenseInstance;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import java.util.ArrayList;

public class CatBoostPredictor {
    public int predict_class(float x1, float x2, float x3, float x4, float x5,
                                    int y1, int y2, int y3, int y4, int y5, float value) {
        // Input validation for float values
        Float[] features = {x1, x2, x3, x4, x5, value};
        for (Float f : features) {
            if (f == null || Float.isNaN(f) || Float.isInfinite(f)) {
                throw new IllegalArgumentException("Feature values and input must be valid integers or floats.");
            }
        }

        // Input validation for integer values
        Integer[] labels = {y1, y2, y3, y4, y5};
        for (Integer i : labels) {
            if (i == null) {
                throw new IllegalArgumentException("Labels must be integers.");
            }
            // Validate that the label is 0 or 1 (binary classification)
            if (i != 0 && i != 1) {
                throw new IllegalArgumentException("Labels must be either 0 or 1.");
            }
        }

        try {
            // Create attributes for the dataset
            ArrayList<Attribute> attributes = new ArrayList<>();
            attributes.add(new Attribute("feature"));

            // Create nominal class attribute
            ArrayList<String> classValues = new ArrayList<>();
            classValues.add("0");
            classValues.add("1");
            Attribute classAttribute = new Attribute("class", classValues);
            attributes.add(classAttribute);

            // Create training dataset
            Instances trainingData = new Instances("TrainingData", attributes, 5);
            trainingData.setClassIndex(1);

            // Add training instances
            float[][] trainData = {
                    {x1, y1}, {x2, y2}, {x3, y3}, {x4, y4}, {x5, y5}
            };

            for (float[] data : trainData) {
                DenseInstance instance = new DenseInstance(2);
                instance.setDataset(trainingData);
                instance.setValue(0, data[0]); // feature value
                instance.setClassValue(String.valueOf((int)data[1])); // class value
                trainingData.add(instance);
            }

            // Configure and train the model
            RandomForest model = new RandomForest();
            model.setNumIterations(10);
            model.setMaxDepth(3);
            model.buildClassifier(trainingData);

            // Create test instance
            Instances testData = new Instances(trainingData, 0);
            DenseInstance testInstance = new DenseInstance(2);
            testInstance.setDataset(testData);
            testInstance.setValue(0, value);
            testData.add(testInstance);

            // Make prediction
            double prediction = model.classifyInstance(testData.firstInstance());
            return (int)prediction;

        } catch (Exception e) {
            // Convert any unexpected Weka exceptions to IllegalArgumentException
            // if they are related to invalid input
            if (e.getMessage().contains("bounds") ||
                    e.getMessage().contains("invalid") ||
                    e.getMessage().contains("Input") ||
                    e.getMessage().contains("data")) {
                throw new IllegalArgumentException("Invalid input: " + e.getMessage());
            }
            throw new RuntimeException("Error during model training or prediction: " + e.getMessage());
        }
    }
}
