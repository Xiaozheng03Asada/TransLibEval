package com.example;

import com.google.common.base.Preconditions;

public class Person {
    public String get_native_representation(String data) {
        
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

        
        try {
            Preconditions.checkNotNull(name, "name is required");
            Preconditions.checkArgument(age >= 0, "age must be greater than or equal to 0");
        } catch (IllegalArgumentException e) {
            return "error: " + e.getMessage();
        }

        
        String city = "Unknown";

        return name + " " + age + " " + city;
    }
}