package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class test_datetime_timedelta {
    private function_datetime_timedelta datetimeModifier;

    @BeforeEach
    public void setUp() {
        datetimeModifier = new function_datetime_timedelta();
    }

    @Test
    public void test_add_days() {
        String result = datetimeModifier.get_modified_datetime("2024-11-11 10:30:45", 5, 0, 0);
        assertEquals("2024-11-16 10:30:45", result);
    }

    @Test
    public void test_subtract_days() {
        String result = datetimeModifier.get_modified_datetime("2024-11-11 10:30:45", -3, 0, 0);
        assertEquals("2024-11-08 10:30:45", result);
    }

    @Test
    public void test_add_hours() {
        String result = datetimeModifier.get_modified_datetime("2024-11-11 10:30:45", 0, 5, 0);
        assertEquals("2024-11-11 15:30:45", result);
    }

    @Test
    public void test_subtract_hours() {
        String result = datetimeModifier.get_modified_datetime("2024-11-11 10:30:45", 0, -5, 0);
        assertEquals("2024-11-11 05:30:45", result);
    }

    @Test
    public void test_add_weeks() {
        String result = datetimeModifier.get_modified_datetime("2024-11-11 10:30:45", 0, 0, 2);
        assertEquals("2024-11-25 10:30:45", result);
    }
}