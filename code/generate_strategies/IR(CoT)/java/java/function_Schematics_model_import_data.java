package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class Product {
    
    public String import_and_validate(String data) {
        try {
            
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> dataMap = mapper.readValue(data, new TypeReference<Map<String, Object>>() {});

            
            if (!dataMap.containsKey("name")) {
                throw new Exception("Missing required field: name");
            }
            if (!(dataMap.get("name") instanceof String)) {
                throw new Exception("Field 'name' must be a string");
            }

            
            if (!dataMap.containsKey("price")) {
                throw new Exception("Missing required field: price");
            }
            
            Object priceObj = dataMap.get("price");
            int price;
            if (priceObj instanceof Number) {
                price = ((Number) priceObj).intValue();
            } else {
                throw new Exception("Field 'price' must be an integer");
            }
            
            if (price < 0) {
                throw new Exception("Field 'price' must be >= 0");
            }

            
            if (!dataMap.containsKey("category")) {
                dataMap.put("category", "General");
            } else {
                if (!(dataMap.get("category") instanceof String)) {
                    throw new Exception("Field 'category' must be a string");
                }
            }
            
            String result = "{'name': '" + dataMap.get("name") + "', 'price': " + price + ", 'category': '" + dataMap.get("category") + "'}";
            return result;
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }
}