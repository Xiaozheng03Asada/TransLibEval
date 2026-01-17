package com.example;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.MaxAttemptsRetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import java.util.function.Predicate;

public class RetryFunction {
    public int might_fail_function(final int value, final boolean should_raise) {
        // 创建重试模板
        RetryTemplate template = new RetryTemplate();

        // 设置固定等待时间为1秒
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(1000L);
        template.setBackOffPolicy(backOffPolicy);

        // 设置最大重试次数为3次
        MaxAttemptsRetryPolicy retryPolicy = new MaxAttemptsRetryPolicy(3);
        template.setRetryPolicy(retryPolicy);

        try {
            return template.execute(new RetryCallback<Integer, RuntimeException>() {
                @Override
                public Integer doWithRetry(RetryContext context) {
                    System.out.println("The current value is: " + value);
                    try {
                        Thread.sleep(100); // 等待0.1秒
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    if (should_raise && value < 5) {
                        throw new IllegalArgumentException("Value is too small");
                    }

                    // 如果结果小于5，通过抛出异常触发重试
                    if (value < 5) {
                        throw new RuntimeException("Result is less than 5");
                    }

                    return value;
                }
            });
        } catch (RuntimeException e) {
            throw e;
        }
    }
}