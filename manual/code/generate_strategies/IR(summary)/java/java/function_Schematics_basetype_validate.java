package com.example;

import com.google.common.primitives.Ints;

public class PositiveIntegerType {
    
    public String validate(String value) {
        
        Integer intValue = Ints.tryParse(value);
        if (intValue == null) {
            
            return "Value must be an integer.";
        }
        
        if (intValue <= 0) {
            return "Value must be a positive integer.";
        }
        
        return "Validation successful.";
    }
}