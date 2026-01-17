package com.example;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import java.util.concurrent.TimeUnit;

public class RetryFunction {

    public int might_fail_function(int value, float extra_sleep, boolean handle_retry_error) throws Exception {

        class ValueError extends Exception {
            public ValueError(String message) {
                super(message);
            }
        }

        class RetryError extends Exception {
            public RetryError(Throwable cause) {
                super(cause);
            }
        }

        RetryTemplate retryTemplate = new RetryTemplate();

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000); // 1 second
        backOffPolicy.setMaxInterval(10000); // 10 seconds
        backOffPolicy.setMultiplier(1);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(retryPolicy);

        retryTemplate.setListeners(new RetryListener[] {
                new RetryListener() {
                    @Override
                    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                        System.out.println("Retry attempt " + context.getRetryCount() + " failed with exception: " + throwable.getMessage());
                    }
                }
        });

        return retryTemplate.execute(context -> {
            if (extra_sleep > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep((long) (extra_sleep * 1000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }

            System.out.println("The current value is: " + value);
            if (value < 5) {
                throw new ValueError("Value is too small");
            }
            return value;
        }, context -> {
            // Recovery callback (not used in this example, but required)
            return null;
        });
    }
}