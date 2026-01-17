package com.example;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import java.util.Set;

public class ProductValidator {
    public String create_product(int stock, float price) {
        class Product {
            @Min(0)
            @Max(1000)
            private final int stock;

            @Positive
            private final float price;

            public Product(int stock, float price) {
                this.stock = stock;
                this.price = price;
            }

            @Override
            public String toString() {
                return String.format("stock=%d price=%.2f", stock, price);
            }
        }

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Product product = new Product(stock, price);
            Set<ConstraintViolation<Product>> violations = validator.validate(product);

            if (violations.isEmpty()) {
                return product.toString();
            } else {
                StringBuilder errors = new StringBuilder();
                for (ConstraintViolation<Product> violation : violations) {
                    errors.append("[{\"loc\":[\"").append(violation.getPropertyPath())
                            .append("\"],\"msg\":\"").append(violation.getMessage())
                            .append("\",\"type\":\"value_error\"}]");
                }
                return errors.toString();
            }
        }
    }
}