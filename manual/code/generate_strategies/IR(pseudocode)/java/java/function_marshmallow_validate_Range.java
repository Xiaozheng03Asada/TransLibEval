package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

public class UserValidator {

    
    public String validate_user_data(String user_data) {
        try {
            
            ObjectMapper mapper = new ObjectMapper();
            
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            
            class User {
                public int age;      
                public double score; 
            }

            
            User user = mapper.readValue(user_data, User.class);

            
            if (user.age < 0 || user.age > 120 || user.score < 0.0 || user.score > 100.0) {
                return "Error: invalid input";
            }

            
            return mapper.writeValueAsString(user);
        } catch (Exception e) {
            
            return "Error: invalid input";
        }
    }
}