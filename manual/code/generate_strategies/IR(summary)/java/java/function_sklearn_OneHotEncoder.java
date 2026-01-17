package com.example;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class OneHotEncoderFunction {
    public String test_one_hot_encoding(String data) {
        
        class TypeError extends RuntimeException {
            public TypeError(String message) {
                super(message);
            }
        }

        class IndexError extends RuntimeException {
            public IndexError(String message) {
                super(message);
            }
        }

        
        if (data == null) {
            throw new TypeError("Input data must be a string.");
        }

        
        String[] parts = data.split(" ");
        if (parts.length < 2) {
            throw new IndexError("The input data has incorrect structure.");
        }

        String categoricalData;
        int numericData;
        try {
            
            categoricalData = parts[0];
            numericData = Integer.parseInt(parts[1]);
        } catch (Exception e) {
            throw new IndexError("Data structure is incorrect. Maybe missing categorical or numeric part.");
        }

        
        
        RealMatrix encodedMatrix = MatrixUtils.createRealMatrix(new double[][]{{1.0}});
        double encodedValue = encodedMatrix.getEntry(0, 0);

        
        String finalData = numericData + "," + encodedValue;
        return finalData;
    }
}