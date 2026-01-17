package com.example;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.*;

public class PersonValidator {
    public String validate(String name, String age, String city) {
        class Person {
            @NotNull
            @NotEmpty
            private String name;

            @Min(18)
            @Max(100)
            private int age;

            @NotNull
            @Pattern(regexp = "[a-zA-Z ]+")
            private String city;

            public void setName(String name) {
                this.name = name;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getName() {
                return name;
            }

            public int getAge() {
                return age;
            }

            public String getCity() {
                return city;
            }
        }

        try {
            // 手动验证输入
            // 验证name
            if (name == null || name.trim().isEmpty()) {
                return "Invalid data: One or more fields are incorrect";
            }

            // 验证age
            int ageValue;
            try {
                ageValue = Integer.parseInt(age);
                if (ageValue < 18 || ageValue > 100) {
                    return "Invalid data: One or more fields are incorrect";
                }
            } catch (NumberFormatException e) {
                return "Invalid data: One or more fields are incorrect";
            }

            // 验证city
            if (city == null || !city.matches("[a-zA-Z ]+")) {
                return "Invalid data: One or more fields are incorrect";
            }

            // 如果所有验证都通过，返回成功消息
            return String.format("Valid data: name = %s, age = %d, city = %s",
                    name, ageValue, city);

        } catch (Exception e) {
            return "Invalid data: One or more fields are incorrect";
        }
    }
}