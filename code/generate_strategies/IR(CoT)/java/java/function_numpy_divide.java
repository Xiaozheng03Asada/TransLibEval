package com.example;

import org.apache.commons.math3.util.Precision;

public class DivisionCalculator {
    
    
    
    
    
    public double divide(Double arr1, Double arr2, Double scalar) {
        try {
            if (arr2 != null) {
                
                double result = arr1 / arr2;
                
                return Precision.round(result, 6);
            } else if (scalar != null) {
                
                double result = arr1 / scalar;
                return Precision.round(result, 6);
            } else {
                throw new IllegalArgumentException("Either arr2 or scalar must be provided.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error in division: " + e.getMessage());
        }
    }
}