package com.example;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateParser {

    // 唯一方法 parse_date 实现所有功能
    public String parse_date(String date_str, String format_str) {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(format_str);
            DateTime dt = formatter.parseDateTime(date_str);
            // 将解析后的日期格式化为 "yyyy-MM-dd HH:mm:ss"
            return dt.toString("yyyy-MM-dd HH:mm:ss");
        } catch (IllegalArgumentException e) {
            // 当格式化失败或输入有误时返回 "failed"
            return "failed";
        }
    }
}