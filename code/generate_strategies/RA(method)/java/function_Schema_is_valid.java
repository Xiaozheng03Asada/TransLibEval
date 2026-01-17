package com.example;

import org.apache.commons.lang3.StringUtils;

public class UserValidator {
    public String validate_user(String name, String age, String email) {
        // 内部类定义数据结构
        class UserData {
            private final String name;
            private final int age;  // 声明为final，确保必须初始化
            private final String email;

            public UserData(String name, String age, String email) {
                this.name = name;
                // 确保age总是被初始化为一个值
                int parsedAge;
                try {
                    parsedAge = Integer.parseInt(age);
                } catch (NumberFormatException | NullPointerException e) {
                    parsedAge = -1;  // 使用-1表示无效年龄
                }
                this.age = parsedAge;
                this.email = email;
            }

            public boolean isValid() {
                // 验证name
                if (StringUtils.isBlank(name)) {
                    return false;
                }

                // 验证age（必须为正整数）
                if (age <= 0) {
                    return false;
                }

                // 验证email（可选字段）
                return email == null || email.isEmpty() || email.contains("@");
            }
        }

        UserData userData = new UserData(name, age, email);
        return userData.isValid() ? "Valid data" : "Invalid data";
    }
}