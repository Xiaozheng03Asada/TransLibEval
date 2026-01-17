package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class test_datetime_now {

    @Test
    public void test_get_current_datetime_input() {
        function_datetime_now dtModifier = new function_datetime_now();
        String date_string = "2024-11-11 10:30:45";
        String result = dtModifier.get_current_datetime(date_string);
        assertEquals("2024-11-11 10:30:45", result);
    }

    @Test
    public void test_get_current_datetime_default() {
        function_datetime_now dtModifier = new function_datetime_now();
        // 使用null模拟未传入参数，返回默认值
        String result = dtModifier.get_current_datetime(null);
        assertEquals(19, result.length());
        assertTrue(result.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
    }

    @Test
    public void test_get_current_datetime_invalid_format() {
        function_datetime_now dtModifier = new function_datetime_now();
        String date_string = "2024-11-11 10:30";  // 格式错误
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dtModifier.get_current_datetime(date_string);
        });
        assertEquals("The date string format is incorrect.", exception.getMessage());
    }

    @Test
    public void test_get_current_datetime_changes() {
        function_datetime_now dtModifier = new function_datetime_now();
        String date_string = "2024-11-11 10:30:45";
        String result1 = dtModifier.get_current_datetime(date_string);
        String result2 = dtModifier.get_current_datetime(date_string);
        assertEquals(result1, result2);
    }

    @Test
    public void test_get_current_datetime_empty_string() {
        function_datetime_now dtModifier = new function_datetime_now();
        String date_string = "";
        String result = dtModifier.get_current_datetime(date_string);
        assertEquals("1900-01-01 00:00:00", result);
    }
}