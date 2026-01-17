package com.example;

import org.apache.commons.collections4.map.LRUMap;

public class LRUCacheManager {

    
    public String manage_lru_cache(String cache_size, String operations) {
        int capacity = Integer.parseInt(cache_size);
        
        LRUMap<String, String> cache = new LRUMap<>(capacity);

        StringBuilder resultBuilder = new StringBuilder();
        if (operations == null || operations.trim().isEmpty()) {
            return "";
        }

        
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
                
                cache.put(parts[1], parts[2]);
            } else if ("get".equals(command)) {
                
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