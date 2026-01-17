package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;

public class ProductProcessor {

    
    public String process_product_data(String product_data) {
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            Map<String, Object> data = mapper.readValue(product_data, Map.class);

            
            if (!data.containsKey("name") || !data.containsKey("price")) {
                throw new Exception();
            }

            
            Object nameObj = data.get("name");
            if (nameObj instanceof String) {
                String nameUpper = ((String) nameObj).toUpperCase();
                data.put("name", nameUpper);
            } else {
                throw new Exception();
            }

            
            Object priceObj = data.get("price");
            double price;
            if (priceObj instanceof Number) {
                price = ((Number) priceObj).doubleValue();
            } else {
                throw new Exception();
            }
            data.put("price", price);

            
            if (data.containsKey("in_stock")) {
                Object inStockObj = data.get("in_stock");
                if (!(inStockObj instanceof Boolean)) {
                    throw new Exception();
                }
            } else {
                data.put("in_stock", true);
            }

            
            return mapper.writeValueAsString(data);
        } catch (Exception e) {
            
            return "Error: invalid input";
        }
    }
}