package com.example;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.regex.Pattern;

public class UserValidator {
    public String create_user(String name, int age, String email) {
        class User {
            private final String name;
            private final int age;
            private final String email;

            public User(String name, int age, String email) {
                this.name = name;
                this.age = age;
                this.email = email;
            }

            public JSONArray validate() {
                JSONArray errors = new JSONArray();

                if (name == null || name.trim().isEmpty()) {
                    errors.put(createError("name", "name cannot be empty", "value_error"));
                }

                if (age < 0) {
                    errors.put(createError("age", "age must be positive", "value_error"));
                }

                if (email == null || email.trim().isEmpty()) {
                    errors.put(createError("email", "email cannot be empty", "value_error"));
                } else if (!isValidEmail(email)) {
                    errors.put(createError("email", "invalid email format", "value_error"));
                }

                return errors;
            }

            private boolean isValidEmail(String email) {
                String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
                Pattern pattern = Pattern.compile(emailRegex);
                return pattern.matcher(email).matches();
            }

            private JSONObject createError(String field, String message, String type) {
                JSONObject error = new JSONObject();
                JSONArray loc = new JSONArray();
                loc.put(field);
                error.put("loc", loc);
                error.put("msg", message);
                error.put("type", type);
                return error;
            }

            @Override
            public String toString() {
                return String.format("name='%s' age=%d email='%s'", name, age, email);
            }
        }

        try {
            User user = new User(name, age, email);
            JSONArray validationErrors = user.validate();

            if (validationErrors.length() > 0) {
                return validationErrors.toString();
            }

            return user.toString();
        } catch (Exception e) {
            JSONArray errors = new JSONArray();
            errors.put(new JSONObject()
                    .put("loc", new JSONArray().put("unknown"))
                    .put("msg", e.getMessage())
                    .put("type", "value_error"));
            return errors.toString();
        }
    }
}