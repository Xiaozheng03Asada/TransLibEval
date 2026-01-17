package com.example;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import dev.failsafe.function.CheckedSupplier;
import java.time.Duration;

public class RetryFunction {
    public int might_fail_function(int value, boolean shouldRaise) {
        
        class ValueError extends RuntimeException {
            public ValueError(String message) {
                super(message);
            }
        }

        
        RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
                .handle(ValueError.class)
                .withMaxAttempts(3)
                .withDelay(Duration.ofSeconds(1))
                .build();

        
        CheckedSupplier<Integer> supplier = () -> {
            System.out.println("The current value is: " + value);
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (shouldRaise) {
                throw new ValueError("Value is too small");
            }
            return value;
        };

        
        return Failsafe.with(retryPolicy).get(supplier);
    }
}