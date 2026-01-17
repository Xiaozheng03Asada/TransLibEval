package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class test_datetime_strftime {

    @Test
    public void test_format_with_specific_time() {
        function_datetime_strftime ft = new function_datetime_strftime();
        String expectedResult = "2024-11-11 10:30:45";
        assertEquals(expectedResult, ft.format_current_time(2024, 11, 11, 10, 30, 45));
    }

    @Test
    public void test_format_date_only() {
        function_datetime_strftime ft = new function_datetime_strftime();
        String expectedResult = "2023-01-01 00:00:00";
        assertEquals(expectedResult, ft.format_current_time(2023, 1, 1, 0, 0, 0));
    }

    @Test
    public void test_format_midnight_time() {
        function_datetime_strftime ft = new function_datetime_strftime();
        String expectedResult = "2024-01-01 00:00:00";
        assertEquals(expectedResult, ft.format_current_time(2024, 1, 1, 0, 0, 0));
    }

    @Test
    public void test_format_end_of_year() {
        function_datetime_strftime ft = new function_datetime_strftime();
        String expectedResult = "2023-12-31 23:59:59";
        assertEquals(expectedResult, ft.format_current_time(2023, 12, 31, 23, 59, 59));
    }

    @Test
    public void test_format_leap_year() {
        function_datetime_strftime ft = new function_datetime_strftime();
        String expectedResult = "2024-02-29 12:00:00";
        assertEquals(expectedResult, ft.format_current_time(2024, 2, 29, 12, 0, 0));
    }
}