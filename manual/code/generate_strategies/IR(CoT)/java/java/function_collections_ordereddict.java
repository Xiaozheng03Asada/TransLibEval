package com.example;

import org.apache.commons.collections4.map.ListOrderedMap;

public class OrderedDictCalculator {


    public String process_key_value_pairs(String data) {
        
        if (data == null || data.trim().isEmpty()) {
            return "failed";
        }

        
        ListOrderedMap<String, String> orderedDict = new ListOrderedMap<>();

        
        String[] items = data.split(",");
        for (String item : items) {
            
            if (item.trim().isEmpty()) {
                continue;
            }
            try {
                
                String[] parts = item.split(":", 2);
                if (parts.length < 2) {
                    continue;
                }
                String key = parts[0].trim();
                String value = parts[1].trim();
                orderedDict.put(key, value);
            } catch (Exception e) {
                
                continue;
            }
        }

        
        if (orderedDict.isEmpty()) {
            return "failed";
        }

        
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