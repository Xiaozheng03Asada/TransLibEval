package com.example;

import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class RetryFunction {
    public int might_fail_function(int value, float extra_sleep, boolean handle_retry_error) {
        class ValueError extends RuntimeException {
            public ValueError(String message) {
                super(message);
            }
        }

        class RetryFunction {
            @Retryable(
                    maxAttempts = 3,
                    value = {ValueError.class},
                    backoff = @Backoff(delay = 1000)
            )
            public int execute() {
                try {
                    System.out.println("The current value is: " + value);
                    try {
                        Thread.sleep((long)(extra_sleep * 1000));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    if (value < 5) {
                        throw new ValueError("Value is too small");
                    }
                    return value;
                } catch (ValueError e) {
                    if (handle_retry_error) {
                        throw new RuntimeException("Operation failed after retries: " + e.getMessage(), e);
                    }
                    throw e;
                }
            }

            @Recover
            public int recover(ValueError e) {
                if (handle_retry_error) {
                    throw new RuntimeException("Operation failed after retries: " + e.getMessage(), e);
                }
                throw e;
            }
        }

        RetryFunction retryFunction = new RetryFunction();
        return retryFunction.execute();
    }
}