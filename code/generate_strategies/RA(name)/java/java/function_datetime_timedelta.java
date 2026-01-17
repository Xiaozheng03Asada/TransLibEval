package com.example;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeModifier {
    // 该方法保持与 Python 中同名，负责修改日期时间
    public String get_modified_datetime(String original_datetime_str, int days, int hours, int weeks) {
        // 检查参数是否为 null，模拟 Python 中判断类型非 str（因为 Java 是静态类型，不可能传入非字符串）
        if (original_datetime_str == null) {
            throw new IllegalArgumentException("original_datetime must be a string");
        }
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime original_datetime;
        try {
            // 将字符串解析为 DateTime 对象
            original_datetime = formatter.parseDateTime(original_datetime_str);
        } catch (IllegalArgumentException e) {
            // 如果解析失败，则抛出异常，提示格式错误
            throw new IllegalArgumentException("original_datetime_str format is invalid");
        }
        // 使用 Joda-Time 的方法分别增加（可以负值）days, hours, weeks
        DateTime modified_datetime = original_datetime.plusDays(days).plusHours(hours).plusWeeks(weeks);
        // 将修改后的 DateTime 格式化为字符串并返回
        return formatter.print(modified_datetime);
    }
}