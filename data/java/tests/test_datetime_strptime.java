package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class test_datetime_strptime {

    @Test
    public void test_valid_date() {
        function_datetime_strptime calc = new function_datetime_strptime();
        String result = calc.parse_date("2023-03-25 14:30:00", "yyyy-MM-dd HH:mm:ss");
        assertEquals("2023-03-25 14:30:00", result);
    }

    @Test
    public void test_invalid_date_format() {
        function_datetime_strptime calc = new function_datetime_strptime();
        String result = calc.parse_date("25/03/2023", "yyyy-MM-dd HH:mm:ss");
        assertEquals("failed", result);
    }

    @Test
    public void test_different_format() {
        function_datetime_strptime calc = new function_datetime_strptime();
        String result = calc.parse_date("25/03/2023", "dd/MM/yyyy");
        // 当仅指定日期格式时，时间部分默认为 00:00:00
        assertEquals("2023-03-25 00:00:00", result);
    }

    @Test
    public void test_empty_input() {
        function_datetime_strptime calc = new function_datetime_strptime();
        String result = calc.parse_date("", "yyyy-MM-dd HH:mm:ss");
        assertEquals("failed", result);
    }

    @Test
    public void test_non_date_input() {
        function_datetime_strptime calc = new function_datetime_strptime();
        String result = calc.parse_date("invalid-date", "yyyy-MM-dd HH:mm:ss");
        assertEquals("failed", result);
    }
}