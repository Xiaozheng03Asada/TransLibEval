package com.example;

import org.apache.commons.lang3.ObjectUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CreatePivotTable {
    // 将所有逻辑封装在唯一方法 create_pivot_table 中
    public String create_pivot_table(String date1, String date2, String category1, String category2, Integer value1, Integer value2) {
        // 如果任意参数为null，则全部赋默认值
        if(date1 == null || date2 == null || category1 == null || category2 == null || value1 == null || value2 == null) {
            date1 = "2023-01-01";
            date2 = "2023-01-02";
            category1 = "A";
            category2 = "B";
            value1 = 1;
            value2 = 2;
        }

        // 利用TreeMap构造模拟透视表，TreeMap确保按日期自然排序
        Map<String, Map<String, Integer>> pivot = new TreeMap<>();

        // 处理第一行数据
        pivot.computeIfAbsent(date1, k -> new HashMap<>())
                .put(category1, pivot.get(date1).getOrDefault(category1, 0) + value1);

        // 处理第二行数据
        pivot.computeIfAbsent(date2, k -> new HashMap<>())
                .put(category2, pivot.get(date2).getOrDefault(category2, 0) + value2);

        // 对每一行保证“Category A”和“Category B”均有数值，若缺失填充0
        for (Map<String, Integer> row : pivot.values()) {
            row.putIfAbsent("A", 0);
            row.putIfAbsent("B", 0);
        }

        // 获取排序后的第一行（即pivot表的第一行）
        String firstDate = pivot.keySet().iterator().next();
        Map<String, Integer> firstRow = pivot.get(firstDate);
        int colA = firstRow.get("A");
        int colB = firstRow.get("B");

        // 构造返回结果字符串
        return "Date: " + firstDate + ", Category A: " + colA + ", Category B: " + colB;
    }
}