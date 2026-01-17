package com.example;

import com.google.common.primitives.Ints;

public class PositiveIntegerType {
    // 该方法对应Python中 PositiveIntegerType.validate
    public String validate(String value) {
        // 尝试将字符串转换为整数，使用 Guava 的 Ints.tryParse 进行转换
        Integer intValue = Ints.tryParse(value);
        if (intValue == null) {
            // 如果转换失败，返回错误信息
            return "Value must be an integer.";
        }
        // 判断是否为正整数
        if (intValue <= 0) {
            return "Value must be a positive integer.";
        }
        // 检查通过
        return "Validation successful.";
    }
}