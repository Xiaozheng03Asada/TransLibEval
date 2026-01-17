package com.example;

import org.apache.commons.lang3.StringUtils;

public class BitarrayExtender {
    
    
    public int count_bits(String ba, int bit) {
        
        if (ba == null) {
            throw new IllegalArgumentException("Input must be a non-null string of '0' and '1'.");
        }
        
        if (bit != 0 && bit != 1) {
            throw new IllegalArgumentException("Invalid bit value: " + bit);
        }
        
        for (int i = 0; i < ba.length(); i++) {
            char ch = ba.charAt(i);
            if (ch != '0' && ch != '1') {
                throw new IllegalArgumentException("Input string must only contain '0' and '1'.");
            }
        }
        
        if (bit == 1) {
            return StringUtils.countMatches(ba, "1");
        } else {
            return StringUtils.countMatches(ba, "0");
        }
    }
}