package com.example;

import com.google.common.base.Preconditions;

public class Person {
    public String get_native_representation(String data) {
        // 检查输入格式是否正确（不能为空且必须包含两个逗号分隔的部分）
        if (data == null || data.isEmpty() || data.split(",").length != 2) {
            return "error: Invalid input format";
        }

        String[] parts = data.split(",", -1);
        String name = parts[0];
        int age;
        try {
            age = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return "error: Invalid input format";
        }

        // 使用Guava Preconditions进行数据验证
        try {
            Preconditions.checkNotNull(name, "name is required");
            Preconditions.checkArgument(age >= 0, "age must be greater than or equal to 0");
        } catch (IllegalArgumentException e) {
            return "error: " + e.getMessage();
        }

        // 默认城市为 "Unknown"
        String city = "Unknown";

        return name + " " + age + " " + city;
    }
}