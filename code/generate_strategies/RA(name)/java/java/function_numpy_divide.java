package com.example;

import org.apache.commons.math3.util.Precision;

public class DivisionCalculator {
    // This method implements the division functionality.
    // It accepts parameters arr1, arr2, and scalar as Double.
    // When arr2 is not null, it performs arr1 / arr2.
    // When arr2 is null and scalar is not null, it performs arr1 / scalar.
    // Otherwise, it throws an exception.
    public double divide(Double arr1, Double arr2, Double scalar) {
        try {
            if (arr2 != null) {
                // Two number division: arr1 / arr2.
                double result = arr1 / arr2;
                // Using Apache Commons Math library for result processing.
                return Precision.round(result, 6);
            } else if (scalar != null) {
                // Single number divided by scalar.
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