package com.example;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class ReshapeCalculator {
    public String reshape(Double price, Double quantity) {
        // 在Java方法内部定义类
        class ReshapeCalculator {
            private String calculate(Double price, Double quantity) {
                if (price == null || quantity == null) {
                    price = 10.0;
                    quantity = 5.0;
                }

                // 使用nd4j创建数组并重塑
                double[] data = {price, quantity};
                INDArray array = Nd4j.create(data);
                INDArray reshaped = array.reshape(1, 2);

                // 计算总金额
                double totalAmount = reshaped.getDouble(0, 0) * reshaped.getDouble(0, 1);

                // 格式化结果字符串
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