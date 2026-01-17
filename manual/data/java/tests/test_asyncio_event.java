package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_asyncio_event {
    private final function_asyncio_event handler = new function_asyncio_event();

    @Test
    void test_task_when_event_triggered() {
        String result = handler.might_fail_function("triggered", "A");
        assertEquals("Task A completed", result);
    }

    @Test
    void test_task_when_event_not_triggered() {
        String result = handler.might_fail_function("waiting", "B");
        assertEquals("Task B is waiting for the event", result);
    }

    @Test
    void test_task_with_empty_event_status() {
        String result = handler.might_fail_function("", "C");
        assertEquals("Task C is waiting for the event", result);
    }

    @Test
    void test_task_with_empty_task_name() {
        String result = handler.might_fail_function("triggered", "");
        assertEquals("Task  completed", result);
    }

    @Test
    void test_task_with_non_triggered_event_status() {
        String result = handler.might_fail_function("inactive", "D");
        assertEquals("Task D is waiting for the event", result);
    }
}