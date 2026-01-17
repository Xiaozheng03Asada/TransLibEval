package com.example;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

public class UserProfileHandler {
    public String create_user_profile(String username, String email) {
        class UserProfile {
            @Size(min = 3, max = 20, message = "username length must be between 3 and 20")
            @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "username must match pattern")
            private String username;

            @Email(message = "value is not a valid email address")
            private String email;

            public UserProfile(String username, String email) {
                this.username = username;
                this.email = email;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }

        try {
            UserProfile profile = new UserProfile(username, email);
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<UserProfile>> violations = validator.validate(profile);

            if (violations.isEmpty()) {
                return String.format("username='%s' email='%s'", profile.getUsername(), profile.getEmail());
            } else {
                return violations.stream()
                        .map(violation -> String.format(
                                "[{\"loc\":[\"body\",\"%s\"],\"msg\":\"%s\",\"type\":\"value_error\"}]",
                                violation.getPropertyPath(),
                                violation.getMessage()))
                        .collect(Collectors.joining(", "));
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}