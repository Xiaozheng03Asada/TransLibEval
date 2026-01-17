package com.example;

import org.apache.commons.collections4.map.ListOrderedMap;

public class OrderedDictCalculator {


    public String process_key_value_pairs(String data) {
        // 1. 检查输入字符串是否为空
        if (data == null || data.trim().isEmpty()) {
            return "failed";
        }

        // 2. 使用第三方依赖 ListOrderedMap 实现有序字典
        ListOrderedMap<String, String> orderedDict = new ListOrderedMap<>();

        // 3. 按逗号分割字符串得到各个项目
        String[] items = data.split(",");
        for (String item : items) {
            // 过滤空白项
            if (item.trim().isEmpty()) {
                continue;
            }
            try {
                // 4. 以冒号分割项，最多分割出2部分（键和值）
                String[] parts = item.split(":", 2);
                if (parts.length < 2) {
                    continue;
                }
                String key = parts[0].trim();
                String value = parts[1].trim();
                orderedDict.put(key, value);
            } catch (Exception e) {
                // 遇到异常跳过该项
                continue;
            }
        }

        // 5. 如果没有正确的键值对则返回 "failed"
        if (orderedDict.isEmpty()) {
            return "failed";
        }

        // 6. 拼接结果字符串，格式为 "key:value, key:value, ..."，顺序与原来顺序保持一致
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (java.util.Map.Entry<String, String> entry : orderedDict.entrySet()) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(entry.getKey()).append(":").append(entry.getValue());
        }
        return sb.toString();
    }
}