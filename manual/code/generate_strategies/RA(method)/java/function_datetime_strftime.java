package com.example;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeFormatter {
    // 所有逻辑均在此单一方法中实现，输入输出均为 int 和 String
    public String format_current_time(int year, int month, int day, int hour, int minute, int second) {
        // 根据输入的年、月、日、时、分、秒创建一个 DateTime 对象
        DateTime inputTime = new DateTime(year, month, day, hour, minute, second);
        // 定义日期时间格式，模式与 Python 版一致："%Y-%m-%d %H:%M:%S" 对应 "yyyy-MM-dd HH:mm:ss"
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        // 将 DateTime 对象转换为格式化后的字符串
        String formatted_time = formatter.print(inputTime);
        return formatted_time;
    }
}