package com.example;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class ComputePartitionMeansFunction {

    // 唯一方法 compute_partition_means，功能与Python对应
    public String compute_partition_means(String data_str, String column) {
        try {
            // 利用Apache Commons CSV解析CSV数据
            StringReader sr = new StringReader(data_str);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(sr);
            Map<String, Integer> headerMap = ((org.apache.commons.csv.CSVParser) records).getHeaderMap();

            // 判断指定的列是否存在
            if (!headerMap.containsKey(column)) {
                return "Error";
            }

            // 读取指定列的数据，尝试将每个值转为double
            List<Double> values = new ArrayList<>();
            for (CSVRecord record : records) {
                String field = record.get(column);
                try {
                    // 如果字段为空或不能转换为数字则抛异常
                    double num = Double.parseDouble(field);
                    values.add(num);
                } catch (Exception e) {
                    return "Error";
                }
            }

            int n = values.size();
            if (n == 0) {
                return "Error";
            }

            // 使用固定分区数2进行分区；此处分区与dask算法略有不同：
            // 如果只有一行数据，则只返回一组均值。
            int mid;
            if(n >= 2) {
                mid = n / 2;
            } else {
                mid = n;
            }

            List<Double> partitionMeans = new ArrayList<>();
            // 计算第一分区均值（如果分区不空，则计算）
            if (mid > 0) {
                double sum = 0;
                for (int i = 0; i < mid; i++) {
                    sum += values.get(i);
                }
                double mean = sum / mid;
                partitionMeans.add(mean);
            }

            // 计算第二分区均值（仅当存在第二分区数据时）
            if (mid < n) {
                double sum = 0;
                int count = 0;
                for (int i = mid; i < n; i++) {
                    sum += values.get(i);
                    count++;
                }
                if(count > 0){
                    double mean = sum / count;
                    partitionMeans.add(mean);
                }
            }

            // 拼接各分区均值并以逗号分隔，每个均值格式为一个小数点
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < partitionMeans.size(); i++) {
                // 格式化均值为一位小数
                sb.append(String.format("%.1f", partitionMeans.get(i)));
                if (i != partitionMeans.size() - 1) {
                    sb.append(",");
                }
            }
            return sb.toString();

        } catch (Exception e) {
            return "Error";
        }
    }
}