package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserSchema {

    
    public String serialize_user_data(String user_data) {
        
        try {
            
            ObjectMapper mapper = new ObjectMapper();

            
            Map<String, Object> inputMap = mapper.readValue(user_data, new TypeReference<Map<String, Object>>() {});

            
            if (!inputMap.containsKey("age") || !inputMap.containsKey("score")) {
                throw new Exception("Missing required fields");
            }

            
            Object ageObj = inputMap.get("age");
            int age;
            if (ageObj instanceof Number) {
                age = ((Number) ageObj).intValue();
            } else {
                throw new Exception("Invalid type for age");
            }

            
            Object scoreObj = inputMap.get("score");
            double score;
            if (scoreObj instanceof Number) {
                score = ((Number) scoreObj).doubleValue();
            } else {
                throw new Exception("Invalid type for score");
            }

            
            Map<String, Object> dataMap = new LinkedHashMap<>();
            dataMap.put("age", age);
            dataMap.put("score", score);
            Map<String, Object> finalResult = new LinkedHashMap<>();
            finalResult.put("result", dataMap);

            
            return mapper.writeValueAsString(finalResult);
        } catch (Exception e) {
            
            return "Error: invalid input";
        }
    }
}