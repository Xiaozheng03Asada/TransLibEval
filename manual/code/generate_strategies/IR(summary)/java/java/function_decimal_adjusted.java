package com.example;

import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.lang3.math.NumberUtils;

public class GetDecimalAdjusted {
    public String check_discount_for_large_order(String value) {
        try {
            if (!NumberUtils.isCreatable(value)) {
                return "Error";
            }

            BigFraction fraction = new BigFraction(Double.parseDouble(value));
            double doubleValue = fraction.doubleValue();

            if (doubleValue == 0) {
                return "-Infinity";
            }

            String strValue = String.valueOf(Math.abs(doubleValue));

            
            if (strValue.contains("E")) {
                String[] parts = strValue.split("E");
                int exponent = Integer.parseInt(parts[1]);
                return String.valueOf(exponent);
            }

            
            String[] parts = strValue.split("\\.");
            String intPart = parts[0];

            if (Math.abs(doubleValue) >= 1) {
                
                return String.valueOf(intPart.length() - 1);
            } else {
                
                String decimalPart = parts.length > 1 ? parts[1] : "";
                int zeroCount = 0;
                while (zeroCount < decimalPart.length() && decimalPart.charAt(zeroCount) == '0') {
                    zeroCount++;
                }
                return String.valueOf(-(zeroCount + 1));
            }
        } catch (Exception e) {
            return "Error";
        }
    }
}