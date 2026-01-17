package com.example;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class ReshapeCalculator {
    public String reshape(Double price, Double quantity) {
        
        class ReshapeCalculator {
            private String calculate(Double price, Double quantity) {
                if (price == null || quantity == null) {
                    price = 10.0;
                    quantity = 5.0;
                }

                
                double[] data = {price, quantity};
                INDArray array = Nd4j.create(data);
                INDArray reshaped = array.reshape(1, 2);

                
                double totalAmount = reshaped.getDouble(0, 0) * reshaped.getDouble(0, 1);

                
                return String.format("Price: %s, Quantity: %s, Total Amount: %s",
                        reshaped.getDouble(0, 0),
                        reshaped.getDouble(0, 1),
                        totalAmount);
            }
        }

        ReshapeCalculator calculator = new ReshapeCalculator();
        return calculator.calculate(price, quantity);
    }
}