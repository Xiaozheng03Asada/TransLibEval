package com.example;

import weka.core.Instances;
import weka.core.DenseInstance;
import weka.core.Attribute;
import weka.classifiers.trees.RandomForest;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FeatureImportance {
    public String quick_sort_from_string(String input_str) {
        try {
            // 解析输入字符串
            String[] parts = input_str.split(",");
            int num_samples = Integer.parseInt(parts[0]);
            int num_features = Integer.parseInt(parts[1]);

            // 创建特征属性列表
            ArrayList<Attribute> attributes = new ArrayList<>();
            for (int i = 0; i < num_features; i++) {
                attributes.add(new Attribute("feature" + i));
            }
            // 添加类别属性
            ArrayList<String> classValues = new ArrayList<>();
            classValues.add("0");
            classValues.add("1");
            attributes.add(new Attribute("class", classValues));

            // 创建数据集
            Instances dataset = new Instances("RandomData", attributes, num_samples);
            dataset.setClassIndex(num_features);

            // 生成随机数据
            Random random = new Random();
            for (int i = 0; i < num_samples; i++) {
                double[] values = new double[num_features + 1];
                double sum = 0;
                for (int j = 0; j < num_features; j++) {
                    values[j] = random.nextDouble();
                    sum += values[j];
                }
                values[num_features] = (sum > num_features / 2.0) ? 1.0 : 0.0;
                dataset.add(new DenseInstance(1.0, values));
            }

            // 配置随机森林
            RandomForest forest = new RandomForest();
            forest.setNumIterations(5);  // 设置树的数量
            forest.buildClassifier(dataset);

            // 使用信息增益评估特征重要性
            AttributeSelection attSelect = new AttributeSelection();
            InfoGainAttributeEval evaluator = new InfoGainAttributeEval();
            Ranker ranker = new Ranker();

            attSelect.setEvaluator(evaluator);
            attSelect.setSearch(ranker);
            attSelect.SelectAttributes(dataset);

            // 获取特征重要性得分
            double[] importance = new double[num_features];
            for (int i = 0; i < num_features; i++) {
                importance[i] = evaluator.evaluateAttribute(i);
            }

            // 将特征重要性转换为正整数并连接成字符串
            return IntStream.range(0, num_features)
                    .mapToObj(i -> String.valueOf((int)((importance[i] + 1) * 1000))) // 确保值为正
                    .collect(Collectors.joining(","));

        } catch (Exception e) {
            throw new RuntimeException("Error in Weka processing: " + e.getMessage());
        }
    }
}