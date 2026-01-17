package com.example;

import org.apache.commons.collections4.map.LRUMap;

public class LRUCacheManager {

    // 本方法实现了 LRU cache 功能，输入参数均为 String 类型，且返回 String 类型
    public String manage_lru_cache(String cache_size, String operations) {
        int capacity = Integer.parseInt(cache_size);
        // 使用 Apache Commons Collections 的 LRUMap 实现 LRU 缓存
        LRUMap<String, String> cache = new LRUMap<>(capacity);

        StringBuilder resultBuilder = new StringBuilder();
        if (operations == null || operations.trim().isEmpty()) {
            return "";
        }

        // 解析操作字符串，按分号分割成单个操作，再按逗号分割详情
        String[] ops = operations.split(";");
        boolean firstResult = true;

        for (String opStr : ops) {
            if (opStr.trim().isEmpty()) {
                continue;
            }
            String[] parts = opStr.split(",");
            if (parts.length < 2) {
                continue;
            }
            String command = parts[0];
            if ("set".equals(command)) {
                if (parts.length < 3) {
                    continue;
                }
                // 执行 set 操作：将 key-value 存入缓存
                cache.put(parts[1], parts[2]);
            } else if ("get".equals(command)) {
                // 执行 get 操作：根据 key 获取缓存值，若不存在则返回 "None"
                String value = cache.get(parts[1]);
                String output = (value != null) ? value : "None";
                if (!firstResult) {
                    resultBuilder.append(",");
                } else {
                    firstResult = false;
                }
                resultBuilder.append(output);
            }
        }

        return resultBuilder.toString();
    }
}