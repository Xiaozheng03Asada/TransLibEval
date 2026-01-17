package com.example;

import org.apache.commons.lang3.StringUtils;

public class UserValidator {
    public String validate_user(String name, String age, String email) {
        
        class UserData {
            private final String name;
            private final int age;  
            private final String email;

            public UserData(String name, String age, String email) {
                this.name = name;
                
                int parsedAge;
                try {
                    parsedAge = Integer.parseInt(age);
                } catch (NumberFormatException | NullPointerException e) {
                    parsedAge = -1;  
                }
                this.age = parsedAge;
                this.email = email;
            }

            public boolean isValid() {
                
                if (StringUtils.isBlank(name)) {
                    return false;
                }

                
                if (age <= 0) {
                    return false;
                }

                
                return email == null || email.isEmpty() || email.contains("@");
            }
        }

        UserData userData = new UserData(name, age, email);
        return userData.isValid() ? "Valid data" : "Invalid data";
    }
}