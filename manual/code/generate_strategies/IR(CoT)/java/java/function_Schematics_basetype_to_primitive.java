package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class IntegerListType {
    
    public String to_primitive(String value) {
        
        if (value == null || !value.startsWith("[") || !value.endsWith("]")) {
            throw new IllegalArgumentException("Value must be a string representing a list.");
        }

        
        ObjectMapper mapper = new ObjectMapper();
        try {
            
            List<Integer> list = mapper.readValue(value, new TypeReference<List<Integer>>() {});
            
            for (Integer item : list) {
                if (item == null) {
                    throw new IllegalArgumentException("All items in the list must be integers.");
                }
            }
            
            return list.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("Value must be a valid list of integers in string format.");
        }
    }
}