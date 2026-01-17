package com.example;

import weka.core.Instances;
import weka.core.DenseInstance;
import weka.core.Attribute;
import weka.classifiers.trees.RandomForest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class LightGBMPredictor {
    public String quick_sort_from_string(String input_str) {
        try {
            
            String[][] strArrays = Arrays.stream(input_str.split(";"))
                    .map(s -> s.split(","))
                    .toArray(String[][]::new);

            
            double[][] testData = new double[strArrays.length][];
            for (int i = 0; i < strArrays.length; i++) {
                testData[i] = Arrays.stream(strArrays[i])
                        .map(s -> {
                            try {
                                return Double.parseDouble(s);
                            } catch (NumberFormatException e) {
                                throw new IllegalArgumentException("The input data must be numeric");
                            }
                        })
                        .mapToDouble(Double::doubleValue)
                        .toArray();
            }

            
            ArrayList<Attribute> attributes = new ArrayList<>();
            int numFeatures = 5;
            for (int i = 0; i < numFeatures; i++) {
                attributes.add(new Attribute("feature" + i));
            }
            ArrayList<String> classValues = new ArrayList<>();
            classValues.add("0");
            classValues.add("1");
            attributes.add(new Attribute("class", classValues));

            
            if (testData[0].length != numFeatures) {
                throw new IllegalArgumentException("The dimension of the test data is inconsistent with that of the training data");
            }

            
            Instances trainingData = new Instances("TrainData", attributes, 100);
            trainingData.setClassIndex(numFeatures);
            Random rand = new Random();

            
            for (int i = 0; i < 100; i++) {
                double[] values = new double[numFeatures + 1];
                for (int j = 0; j < numFeatures; j++) {
                    values[j] = rand.nextDouble();
                }
                values[numFeatures] = rand.nextInt(2);
                trainingData.add(new DenseInstance(1.0, values));
            }

            
            Instances testInstances = new Instances("TestData", attributes, testData.length);
            testInstances.setClassIndex(numFeatures);
            for (double[] row : testData) {
                double[] instanceValue = Arrays.copyOf(row, row.length + 1);
                testInstances.add(new DenseInstance(1.0, instanceValue));
            }

            
            RandomForest model = new RandomForest();
            model.setNumIterations(5);
            model.buildClassifier(trainingData);

            
            double[] predictions = new double[testData.length];
            for (int i = 0; i < testData.length; i++) {
                
                double[] distribution = model.distributionForInstance(testInstances.instance(i));
                predictions[i] = distribution[1];
            }

            
            return Arrays.stream(predictions)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(","));

        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) e;
            }
            throw new RuntimeException("Error in prediction: " + e.getMessage());
        }
    }
}