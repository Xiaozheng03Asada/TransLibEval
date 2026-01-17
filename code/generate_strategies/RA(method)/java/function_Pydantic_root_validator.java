package com.example;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.NotNull;

public class Order {
    public String check_discount_for_large_order(int quantity, float price, float discount) {
        class OrderModel {
            @NotNull
            private final int quantity;

            @NotNull
            private final float price;

            @PositiveOrZero
            private final float discount;

            public OrderModel(int quantity, float price, float discount) {
                this.quantity = quantity;
                this.price = price;
                this.discount = discount;
            }

            public int getQuantity() {
                return quantity;
            }

            public float getPrice() {
                return price;
            }

            public float getDiscount() {
                return discount;
            }

            public void validate() {
                if (quantity > 10 && discount == 0) {
                    throw new IllegalArgumentException("Discount must be greater than 0 for orders with quantity greater than 10");
                }
                if (discount > 0 && price <= 0) {
                    throw new IllegalArgumentException("Price must be positive when discount is applied");
                }
            }

            @Override
            public String toString() {
                return String.format("quantity=%d price=%.1f discount=%.1f", quantity, price, discount);
            }
        }

        try {
            OrderModel order = new OrderModel(quantity, price, discount);
            order.validate();
            return order.toString();
        } catch (IllegalArgumentException e) {
            return String.format("[{\"type\":\"value_error\",\"msg\":\"%s\"}]", e.getMessage());
        }
    }
}