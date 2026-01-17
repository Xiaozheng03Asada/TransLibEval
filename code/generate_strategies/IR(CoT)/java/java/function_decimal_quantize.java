package com.example;

import org.apache.commons.math3.util.Precision;
import java.text.DecimalFormat;
import java.text.ParseException;

public class SetDecimalPrecision {
    public String check_discount_for_large_order(String value, int precision) {
        try {
            
            double doubleValue = Double.parseDouble(value);
            
            double roundedValue = Precision.round(doubleValue, precision);

            
            StringBuilder pattern = new StringBuilder("#.");
            for (int i = 0; i < precision; i++) {
                pattern.append("0");
            }
            DecimalFormat df = new DecimalFormat(pattern.toString());

            return df.format(roundedValue);
        } catch (NumberFormatException e) {
            return "Error";
        }
    }
}