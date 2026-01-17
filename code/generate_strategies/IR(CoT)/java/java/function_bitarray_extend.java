package com.example;

import org.apache.commons.lang3.StringUtils;

public class BitarrayExtender {
    
    
    
    
    
    public String extend_bits(String bits, String values) {
        
        if (bits == null) {
            throw new IllegalArgumentException("Input must be a string of '0' and '1'.");
        }
        
        if (values == null) {
            throw new IllegalArgumentException("Iterable input must be a string of '0' and '1'.");
        }
        
        for (int i = 0; i < values.length(); i++) {
            char v = values.charAt(i);
            if (v != '0' && v != '1') {
                throw new IllegalArgumentException("All elements in the iterable must be '0' or '1'.");
            }
        }
        
        String result = StringUtils.join(new String[]{bits, values}, "");
        return result;
    }
}