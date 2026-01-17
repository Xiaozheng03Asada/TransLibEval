package com.example;

import weka.classifiers.functions.Logistic;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import java.util.ArrayList;

public class CatBoostProbabilityPredictor {
    public float predict_probability(float x1, float x2, float x3, float x4, float x5,
                                     int y1, int y2, int y3, int y4, int y5, float value) {
        // 创建内部预测器类
        class CatBoostProbabilityPredictor {
            // 验证输入值
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
                // 首先进行输入验证
                validateInputs(x1, x2, x3, x4, x5, y1, y2, y3, y4, y5, value);

                try {
                    // 创建特征属性
                    ArrayList<Attribute> attributes = new ArrayList<>();
                    attributes.add(new Attribute("feature"));

                    // 创建类别属性
                    ArrayList<String> classValues = new ArrayList<>();
                    classValues.add("0");
                    classValues.add("1");
                    attributes.add(new Attribute("class", classValues));

                    // 创建训练数据集
                    Instances trainingData = new Instances("TrainingData", attributes, 5);
                    trainingData.setClassIndex(1);

                    // 添加训练实例
                    float[] trainValues = {x1, x2, x3, x4, x5};
                    int[] trainLabels = {y1, y2, y3, y4, y5};

                    for (int i = 0; i < 5; i++) {
                        DenseInstance instance = new DenseInstance(2);
                        instance.setValue(0, trainValues[i]);
                        instance.setValue(1, trainLabels[i]);
                        trainingData.add(instance);
                    }

                    // 创建和训练模型
                    Logistic model = new Logistic();
                    model.setMaxIts(10);
                    model.setRidge(0.1);
                    model.buildClassifier(trainingData);

                    // 创建测试实例
                    Instances testData = new Instances("TestData", attributes, 1);
                    testData.setClassIndex(1);
                    DenseInstance testInstance = new DenseInstance(2);
                    testInstance.setValue(0, value);
                    testData.add(testInstance);

                    // 预测概率
                    double[] probabilities = model.distributionForInstance(testData.instance(0));
                    return (float) probabilities[1];
                } catch (Exception e) {
                    // 将其他异常包装为IllegalArgumentException
                    throw new IllegalArgumentException("Error in model prediction: " + e.getMessage());
                }
            }
        }

        CatBoostProbabilityPredictor predictor = new CatBoostProbabilityPredictor();
        return predictor.predictProbability();
    }
}