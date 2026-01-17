package com.example;

import weka.core.DenseInstance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.core.Attribute;
import java.util.ArrayList;

public class SimpleImputerFunction {
    public String quick_sort_from_string(String input_str) {
        if (input_str == null || input_str.isEmpty()) {
            return "";
        }

        try {
            // 创建ReplaceMissingValues实例
            ReplaceMissingValues replaceMissingValues = new ReplaceMissingValues();

            // 解析输入字符串
            String[] rows = input_str.split(";");
            ArrayList<Attribute> attributes = new ArrayList<>();
            attributes.add(new Attribute("col1"));
            attributes.add(new Attribute("col2"));

            // 创建Weka数据集
            Instances dataset = new Instances("dataset", attributes, 0);

            // 添加数据
            for (String row : rows) {
                String[] values = row.split(",");
                double[] instanceValues = new double[2];
                for (int i = 0; i < 2; i++) {
                    if (values[i].equals("None")) {
                        instanceValues[i] = Double.NaN;
                    } else {
                        instanceValues[i] = Double.parseDouble(values[i]);
                    }
                }
                dataset.add(new DenseInstance(1.0, instanceValues));
            }

            // 使用Weka的ReplaceMissingValues进行缺失值填充
            replaceMissingValues.setInputFormat(dataset);
            Instances newData = Filter.useFilter(dataset, replaceMissingValues);

            // 构建结果字符串
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < newData.numInstances(); i++) {
                if (i > 0) {
                    result.append(";");
                }
                result.append(newData.instance(i).value(0))
                        .append(",")
                        .append(newData.instance(i).value(1));
            }

            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error processing data", e);
        }
    }
}