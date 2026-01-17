package com.example;

import weka.core.Instances;
import weka.core.DenseInstance;
import weka.core.Attribute;
import weka.classifiers.trees.RandomForest;
import java.util.ArrayList;
import java.util.Random;

public class LightGBMTrainer {
    public float train(String boosting_type, String objective, String metric, int num_leaves,
                       float learning_rate, float feature_fraction, float bagging_fraction,
                       int bagging_freq, int verbose, int num_rounds, int data_size, int feature_size) {
        try {
            
            if (!(boosting_type instanceof String)) {
                throw new IllegalArgumentException("boosting_type must be a string");
            }
            if (!(objective instanceof String)) {
                throw new IllegalArgumentException("objective must be a string");
            }
            if (!(metric instanceof String)) {
                throw new IllegalArgumentException("metric must be a string");
            }
            if (objective.isEmpty()) {
                throw new IllegalArgumentException("train_params must contain the 'objective' key");
            }

            
            Random random = new Random(42);

            
            ArrayList<Attribute> attributes = new ArrayList<>();
            for (int i = 0; i < feature_size; i++) {
                attributes.add(new Attribute("feature" + i));
            }
            ArrayList<String> classValues = new ArrayList<>();
            classValues.add("0");
            classValues.add("1");
            attributes.add(new Attribute("class", classValues));

            
            Instances trainingData = new Instances("TrainData", attributes, data_size);
            trainingData.setClassIndex(feature_size);

            
            for (int i = 0; i < data_size; i++) {
                double[] values = new double[feature_size + 1];
                for (int j = 0; j < feature_size; j++) {
                    values[j] = random.nextDouble();
                }
                values[feature_size] = random.nextInt(2);
                trainingData.add(new DenseInstance(1.0, values));
            }

            
            RandomForest model = new RandomForest();
            model.setNumIterations(num_rounds);
            model.setNumFeatures((int)(feature_size * feature_fraction));
            model.setSeed(42);
            if (verbose == 0) {
                model.setDebug(false);
            }

            
            model.buildClassifier(trainingData);

            
            Instances testData = new Instances("TestData", attributes, 10);
            testData.setClassIndex(feature_size);
            double[] testInstance = new double[feature_size + 1];
            for (int i = 0; i < feature_size; i++) {
                testInstance[i] = random.nextDouble();
            }
            testData.add(new DenseInstance(1.0, testInstance));

            
            double[] probabilities = model.distributionForInstance(testData.instance(0));
            return (float) probabilities[1];  

        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) e;
            }
            throw new RuntimeException("Error in training: " + e.getMessage());
        }
    }
}