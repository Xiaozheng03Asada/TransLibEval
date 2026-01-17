package com.example;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.Cache;
import java.util.ArrayList;
import java.util.List;

public class LRUCacheManager {
    public String manage_cache_operations(int cache_size, String operations_str) {
        class LRUCacheManager {
            private final Cache<String, String> cache;

            public LRUCacheManager(int size) {
                cache = CacheBuilder.newBuilder()
                        .maximumSize(size)
                        .build();
            }

            public void set(String key, String value) {
                cache.put(key, value);
            }

            public String get(String key) {
                return cache.getIfPresent(key);
            }
        }

        if (operations_str.isEmpty()) {
            return "";
        }

        LRUCacheManager cacheManager = new LRUCacheManager(cache_size);
        List<String> results = new ArrayList<>();
        String[] operations = operations_str.split(",");

        int i = 0;
        while (i < operations.length) {
            String operation_type = operations[i];
            String key = operations[i + 1];

            if ("set".equals(operation_type)) {
                String value = operations[i + 2];
                cacheManager.set(key, value);
                i += 3;
            } else if ("get".equals(operation_type)) {
                String result = cacheManager.get(key);
                results.add(result == null ? "None" : result);
                i += 2;
            } else {
                throw new IllegalArgumentException("Invalid operation type");
            }
        }

        return String.join(",", results);
    }
}