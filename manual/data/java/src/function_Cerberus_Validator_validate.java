package com.example;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Set;

public class NumberComparer {
    public String compare_numbers(Number num1, Number num2) {
        // 主方法实现，在这个方法内定义所需的所有内部类和验证逻辑
        class NumberPair {
            @NotNull
            private final Number number1;
            @NotNull
            private final Number number2;

            public NumberPair(Number n1, Number n2) {
                this.number1 = n1;
                this.number2 = n2;
            }
        }

        // 如果输入为null，返回验证错误
        if (num1 == null || num2 == null) {
            String fieldName = num1 == null ? "num1" : "num2";
            return String.format("Error: Invalid input. {'%s': ['must be of number type']}", fieldName);
        }

        // 验证输入是否为数字类型
        try {
            double n1 = num1.doubleValue();
            double n2 = num2.doubleValue();

            if (n1 > n2) {
                return "Greater";
            } else if (n1 < n2) {
                return "Smaller";
            } else {
                return "Equal";
            }
        } catch (NumberFormatException e) {
            return "Error: Invalid input. {'num1': ['must be of number type']}";
        }
    }
}