package com.example;

import reactor.core.publisher.Mono;
import java.time.Duration;

public class AsyncTaskHandler {
    public String run_async_task(String name, float delay) {
        class AsyncTaskHandler {
            public Mono<String> async_task(String taskName, float taskDelay) {
                return Mono.delay(Duration.ofMillis((long)(taskDelay * 1000)))
                        .map(ignored -> String.format("Task %s completed after %s seconds", taskName, taskDelay));
            }
        }

        AsyncTaskHandler handler = new AsyncTaskHandler();
        return handler.async_task(name, delay).block();
    }
}