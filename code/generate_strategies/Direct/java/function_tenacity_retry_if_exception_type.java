package com.example;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import dev.failsafe.function.CheckedSupplier;
import java.time.Duration;

public class RetryFunction {
    public int might_fail_function(int value, boolean shouldRaise) {
        // 定义 ValueError 作为局部类
        class ValueError extends RuntimeException {
            public ValueError(String message) {
                super(message);
            }
        }

        // 定义重试策略
        RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
                .handle(ValueError.class)
                .withMaxAttempts(3)
                .withDelay(Duration.ofSeconds(1))
                .build();

        // 定义要重试的逻辑
        CheckedSupplier<Integer> supplier = () -> {
            System.out.println("The current value is: " + value);
            try {
                Thread.sleep(100); // 0.1 秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (shouldRaise) {
                throw new ValueError("Value is too small");
            }
            return value;
        };

        // 执行重试
        return Failsafe.with(retryPolicy).get(supplier);
    }
}