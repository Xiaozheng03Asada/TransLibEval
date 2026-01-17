package com.example;

import reactor.core.publisher.Mono;
import java.time.Duration;

public class AsyncTaskHandler {
    public String run_async_task(String name, int delay) {
        class AsyncTaskHandler {
            public String run_async_task(String name, int delay) {
                return Mono.delay(Duration.ofSeconds(delay))
                        .map(tick -> String.format("Task %s completed after %d seconds", name, delay))
                        .block();
            }
        }
        return new AsyncTaskHandler().run_async_task(name, delay);
    }
}