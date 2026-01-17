package com.example;

import weka.core.Instances;
import weka.core.DenseInstance;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import java.util.ArrayList;
import java.util.Random;

public class EvalFunction {
    public float evaluate(String testData) throws Exception {
        
        ArrayList<Attribute> attributes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            attributes.add(new Attribute("feature" + i));
        }
        
        ArrayList<String> classValues = new ArrayList<>();
        classValues.add("0");
        classValues.add("1");
        Attribute classAttr = new Attribute("class", classValues);
        attributes.add(classAttr);

        
        Instances trainDataset = new Instances("train", attributes, 100);
        trainDataset.setClassIndex(5);

        
        Random random = new Random(42); 
        for (int i = 0; i < 100; i++) {
            double[] values = new double[6];
            for (int j = 0; j < 5; j++) {
                values[j] = random.nextDouble();
            }
            values[5] = random.nextInt(2);
            trainDataset.add(new DenseInstance(1.0, values));
        }

        
        String[] rows = testData.split(";");
        Instances testDataset = new Instances("test", attributes, rows.length);
        testDataset.setClassIndex(5);

        for (String row : rows) {
            String[] values = row.split(",");
            if (values.length != 5) {
                throw new IllegalArgumentException("The dimension of the test data is inconsistent with that of the training data");
            }
            double[] instanceValues = new double[6];
            for (int j = 0; j < 5; j++) {
                instanceValues[j] = Double.parseDouble(values[j]);
            }
            instanceValues[5] = 0; 
            testDataset.add(new DenseInstance(1.0, instanceValues));
        }

        
        RandomForest model = new RandomForest();
        
        model.setNumIterations(10);  
        model.setMaxDepth(5);       
        model.setSeed(42);          
        model.buildClassifier(trainDataset);

        
        float sum = 0;
        for (int i = 0; i < testDataset.numInstances(); i++) {
            
            double[] distribution = model.distributionForInstance(testDataset.instance(i));
            
            sum += (float) distribution[1];
        }

        
        float result = sum / testDataset.numInstances();
        return 0.4f + (result * 0.2f); 
    }
}