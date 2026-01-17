package com.example;

import reactor.core.publisher.Mono;

public class EventTaskHandler {
    public String might_fail_function(String event_status, String task) {
        class EventTaskHandler {
            Mono<String> waitForEvent() {
                return Mono.just(event_status)
                        .map(status -> {
                            if ("triggered".equals(status)) {
                                return "Task " + task + " completed";
                            }
                            return "Task " + task + " is waiting for the event";
                        });
            }
        }

        return new EventTaskHandler().waitForEvent().block();
    }
}