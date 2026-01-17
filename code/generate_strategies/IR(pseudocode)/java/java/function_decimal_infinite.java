package com.example;

import org.apache.commons.lang3.math.NumberUtils;

public class InfiniteCheck {
    public String check_discount_for_large_order(String value) {
        try {
            if (value.equals("Infinity") || value.equals("-Infinity")) {
                return "True";
            }
            
            if (value.equals("NaN")) {
                return "False";
            }
            
            if (!NumberUtils.isCreatable(value)) {
                return "Error";
            }
            return "False";
        } catch (Exception e) {
            return "Error";
        }
    }
}