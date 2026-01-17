package com.example;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.collections4.CollectionUtils;
import java.util.*;

public class TransactionEncoderWrapper {
    public String encode_transactions(String transactions) {
        // 定义在方法内的嵌套类
        class TransactionEncoderWrapper {
            public String encode(String transactions) {
                if (transactions == null || transactions.trim().isEmpty()) {
                    return "[]";
                }

                // 解析输入字符串
                String[] transactionArray = transactions.split(";");

                // 使用LinkedHashSet保持插入顺序
                Set<String> uniqueItems = new LinkedHashSet<>();

                // 保持输入顺序的交易列表
                List<List<String>> transactionsList = new ArrayList<>();

                // 首次遍历：收集所有唯一项，保持它们第一次出现的顺序
                for (String transaction : transactionArray) {
                    String[] items = transaction.split(",");
                    List<String> itemList = Arrays.asList(items);
                    transactionsList.add(itemList);
                    for (String item : items) {
                        uniqueItems.add(item);
                    }
                }

                // 将唯一项转换为列表，保持顺序
                List<String> uniqueItemsList = new ArrayList<>(uniqueItems);

                // 构建编码矩阵
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < transactionsList.size(); i++) {
                    List<String> transaction = transactionsList.get(i);
                    Set<String> transactionSet = new HashSet<>(transaction);

                    for (int j = 0; j < uniqueItemsList.size(); j++) {
                        result.append(transactionSet.contains(uniqueItemsList.get(j)) ? "1" : "0");
                        if (j < uniqueItemsList.size() - 1) {
                            result.append(",");
                        }
                    }
                    if (i < transactionsList.size() - 1) {
                        result.append(";");
                    }
                }

                return result.toString();
            }
        }

        // 创建包装器实例并调用方法
        TransactionEncoderWrapper encoder = new TransactionEncoderWrapper();
        return encoder.encode(transactions);
    }
}