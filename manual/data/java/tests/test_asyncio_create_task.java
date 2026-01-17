package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_asyncio_create_task {
    private function_asyncio_create_task handler;

    @BeforeEach
    void setUp() {
        handler = new function_asyncio_create_task();
    }

    @Test
    void test_task_with_delay() {
        String result = handler.run_task("delayed", 2);
        assertEquals("Task delayed completed after 2 seconds", result);
    }

    @Test
    void test_multiple_tasks() {
        String result1 = handler.run_task("task1", 1);
        String result2 = handler.run_task("task2", 2);
        assertEquals("Task task1 completed after 1 seconds", result1);
        assertEquals("Task task2 completed after 2 seconds", result2);
    }

    @Test
    void test_task_with_no_delay() {
        String result = handler.run_task("immediate", 0);
        assertEquals("Task immediate completed after 0 seconds", result);
    }

    @Test
    void test_task_with_long_delay() {
        String result = handler.run_task("long_delay", 5);
        assertEquals("Task long_delay completed after 5 seconds", result);
    }

    @Test
    void test_task_with_invalid_delay() {
        assertThrows(IllegalArgumentException.class, () -> {
            handler.run_task("invalid", -1);
        });
    }
}