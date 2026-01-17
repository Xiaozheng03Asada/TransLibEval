package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test_asyncio_sleep {
    private function_asyncio_sleep handler;

    @BeforeEach
    void setUp() {
        handler = new function_asyncio_sleep();
    }

    @Test
    void test_asyncio_run_with_delay() {
        String result = handler.run_async_task("Delayed", 2);
        assertEquals("Task Delayed completed after 2.0 seconds", result);
    }

    @Test
    void test_multiple_asyncio_run_tasks() {
        String result1 = handler.run_async_task("Task1", 1);
        String result2 = handler.run_async_task("Task2", 2);
        assertEquals("Task Task1 completed after 1.0 seconds", result1);
        assertEquals("Task Task2 completed after 2.0 seconds", result2);
    }

    @Test
    void test_asyncio_run_with_multiple_delays() {
        String result1 = handler.run_async_task("Delayed 1", 2);
        String result2 = handler.run_async_task("Delayed 2", 3);
        assertEquals("Task Delayed 1 completed after 2.0 seconds", result1);
        assertEquals("Task Delayed 2 completed after 3.0 seconds", result2);
    }

    @Test
    void test_asyncio_run_with_float_delay() {
        String result = handler.run_async_task("Float Delay", 0.5f);
        assertEquals("Task Float Delay completed after 0.5 seconds", result);
    }

    @Test
    void test_asyncio_run_with_custom_task() {
        String result = handler.run_async_task("Custom Task", 1);
        assertEquals("Task Custom Task completed after 1.0 seconds", result);
    }
}