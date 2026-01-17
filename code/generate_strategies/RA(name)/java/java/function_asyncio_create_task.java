package com.example;

import reactor.core.publisher.Mono;
import java.time.Duration;

public class AsyncTaskHandler {
    public String run_task(String name, int delay) {
        class AsyncTaskHandler {
            public String run_task(String name, int delay) {
                if (delay < 0) {
                    throw new IllegalArgumentException("Delay must be a non-negative integer");
                }

                try {
                    return Mono.just(name)
                            .delayElement(Duration.ofSeconds(delay))
                            .map(n -> String.format("Task %s completed after %d seconds", n, delay))
                            .block();
                } catch (Exception e) {
                    throw new RuntimeException("Failed to execute async task", e);
                }
            }
        }
        return new AsyncTaskHandler().run_task(name, delay);
    }
}