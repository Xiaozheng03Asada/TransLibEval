package com.example;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeModifier {

    // 将所有逻辑放在这个单一方法中，方法名与原Python函数相同：get_current_datetime
    public String get_current_datetime(String date_string) {
        if (date_string != null && !date_string.isEmpty()) {
            try {
                // 使用Joda-Time第三方依赖进行日期解析和格式化
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime dt = formatter.parseDateTime(date_string);
                return dt.toString("yyyy-MM-dd HH:mm:ss");
            } catch (IllegalArgumentException e) {
                // 当日期格式不正确时，抛出带有相同提示信息的异常
                throw new IllegalArgumentException("The date string format is incorrect.");
            }
        } else {
            return "1900-01-01 00:00:00";
        }
    }
}