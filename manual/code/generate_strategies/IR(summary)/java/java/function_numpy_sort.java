package com.example;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import java.util.Arrays;

public class SortCalculator {
    public String sort(Double value1, Double value2, Double value3) {
        
        if (value1 == null || value2 == null || value3 == null) {
            value1 = 10.0;
            value2 = 5.0;
            value3 = 15.0;
        }

        
        double[] data = {value1, value2, value3};
        Arrays.sort(data);

        
        return String.format("Sorted Values: %.1f, %.1f, %.1f",
                data[0], data[1], data[2]).replace(".0", "");
    }
}