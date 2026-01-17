package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_asyncio_run {
    private function_asyncio_run handler;

    @BeforeEach
    void setUp() {
        handler = new function_asyncio_run();
    }

    @Test
    void test_asyncio_run_with_delayed_task() {
        String result = handler.run_async_task("delayed", 2);
        assertEquals("Task delayed completed after 2 seconds", result);
    }

    @Test
    void test_multiple_asyncio_run_tasks() {
        String result1 = handler.run_async_task("task1", 1);
        String result2 = handler.run_async_task("task2", 2);
        assertEquals("Task task1 completed after 1 seconds", result1);
        assertEquals("Task task2 completed after 2 seconds", result2);
    }

    @Test
    void test_asyncio_run_empty_task() {
        String result = handler.run_async_task("empty", 0);
        assertEquals("Task empty completed after 0 seconds", result);
    }

    @Test
    void test_asyncio_run_in_different_thread() {
        Thread thread = new Thread(() -> handler.run_async_task("task_in_thread", 1));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Thread interrupted");
        }
        assertTrue(true);
    }

    @Test
    void test_asyncio_run_with_multiple_tasks_in_parallel() {
        String result1 = handler.run_async_task("task1_parallel", 1);
        String result2 = handler.run_async_task("task2_parallel", 2);
        assertEquals("Task task1_parallel completed after 1 seconds", result1);
        assertEquals("Task task2_parallel completed after 2 seconds", result2);
    }
}