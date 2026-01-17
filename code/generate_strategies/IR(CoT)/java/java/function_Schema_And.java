package com.example;

import org.apache.commons.lang3.StringUtils;

public class StringValidator {
    public String validate_user_input(String username, String password) {
        try {
            
            if (StringUtils.isEmpty(username)) {
                return "Validation failed";
            }

            
            if (StringUtils.isEmpty(password) || password.length() < 8) {
                return "Validation failed";
            }

            return String.format("Validation passed: username = %s, password = %s",
                    username, password);
        } catch (Exception e) {
            return "Validation failed";
        }
    }
}