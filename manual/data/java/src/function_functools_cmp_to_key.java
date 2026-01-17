package com.example;

import java.util.*;
import org.apache.commons.lang3.builder.CompareToBuilder; // 第三方依赖，需要在 pom.xml 中配置

public class NumberDictManager {

    // 逐行翻译 Python 部分函数，所有逻辑均在这个唯一方法中实现
    public String manage_number_dict(String operations_str) {
        // 将输入字符串按照分号分割成各个操作指令
        String[] operations = operations_str.split(";");
        Map<Integer, Integer> number_dict = new HashMap<>();

        // 定义比较函数，利用 Apache Commons Lang 的 CompareToBuilder，按升序排序数字
        Comparator<Map.Entry<Integer, Integer>> comparator = new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> entry1, Map.Entry<Integer, Integer> entry2) {
                return new CompareToBuilder().append(entry1.getKey(), entry2.getKey()).toComparison();
            }
        };

        // 遍历分割后的每个操作
        for (String op : operations) {
            // 忽略空字符串（例如末尾可能存在的空操作）
            if (op.trim().isEmpty()) {
                continue;
            }
            String[] op_parts = op.split(",");
            switch (op_parts[0]) {
                case "add":
                    int keyAdd = Integer.parseInt(op_parts[1]);
                    int valueAdd = Integer.parseInt(op_parts[2]);
                    number_dict.put(keyAdd, valueAdd);
                    break;
                case "remove":
                    int keyRemove = Integer.parseInt(op_parts[1]);
                    // 若存在则删除该键对应的元素
                    number_dict.remove(keyRemove);
                    break;
                case "get":
                    int keyGet = Integer.parseInt(op_parts[1]);
                    Integer value = number_dict.get(keyGet);
                    // 若不存在返回 "default"
                    return value == null ? "default" : String.valueOf(value);
                case "sort":
                    // 对字典进行排序，按键的升序排序，构造排序后的列表
                    List<Map.Entry<Integer, Integer>> sortedItems = new ArrayList<>(number_dict.entrySet());
                    Collections.sort(sortedItems, comparator);
                    StringBuilder sb = new StringBuilder();
                    // 拼接排序后的键值对，格式为 "key:value"
                    for (Map.Entry<Integer, Integer> entry : sortedItems) {
                        if (sb.length() != 0) {
                            sb.append(" ");
                        }
                        sb.append(entry.getKey()).append(":").append(entry.getValue());
                    }
                    return sb.toString();
                case "sum":
                    int sum = 0;
                    for (int v : number_dict.values()) {
                        sum += v;
                    }
                    return String.valueOf(sum);
                default:
                    // 不认识的操作，不做处理
                    break;
            }
        }
        return "";
    }
}