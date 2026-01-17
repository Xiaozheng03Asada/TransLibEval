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
            // 解析输入字符串为二维数组
            String[][] strArrays = Arrays.stream(input_str.split(";"))
                    .map(s -> s.split(","))
                    .toArray(String[][]::new);

            // 验证输入数据并转换为double数组
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

            // 创建训练数据属性
            ArrayList<Attribute> attributes = new ArrayList<>();
            int numFeatures = 5;
            for (int i = 0; i < numFeatures; i++) {
                attributes.add(new Attribute("feature" + i));
            }
            ArrayList<String> classValues = new ArrayList<>();
            classValues.add("0");
            classValues.add("1");
            attributes.add(new Attribute("class", classValues));

            // 检查测试数据维度
            if (testData[0].length != numFeatures) {
                throw new IllegalArgumentException("The dimension of the test data is inconsistent with that of the training data");
            }

            // 创建训练数据集
            Instances trainingData = new Instances("TrainData", attributes, 100);
            trainingData.setClassIndex(numFeatures);
            Random rand = new Random();

            // 生成随机训练数据
            for (int i = 0; i < 100; i++) {
                double[] values = new double[numFeatures + 1];
                for (int j = 0; j < numFeatures; j++) {
                    values[j] = rand.nextDouble();
                }
                values[numFeatures] = rand.nextInt(2);
                trainingData.add(new DenseInstance(1.0, values));
            }

            // 创建测试数据集
            Instances testInstances = new Instances("TestData", attributes, testData.length);
            testInstances.setClassIndex(numFeatures);
            for (double[] row : testData) {
                double[] instanceValue = Arrays.copyOf(row, row.length + 1);
                testInstances.add(new DenseInstance(1.0, instanceValue));
            }

            // 训练随机森林模型
            RandomForest model = new RandomForest();
            model.setNumIterations(5);
            model.buildClassifier(trainingData);

            // 进行预测
            double[] predictions = new double[testData.length];
            for (int i = 0; i < testData.length; i++) {
                // 获取概率分布并使用第二个类的概率（对应于正类的概率）
                double[] distribution = model.distributionForInstance(testInstances.instance(i));
                predictions[i] = distribution[1];
            }

            // 转换预测结果为字符串
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