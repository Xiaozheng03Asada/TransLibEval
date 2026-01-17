package com.example;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeFormatter {
    
    public String format_current_time(int year, int month, int day, int hour, int minute, int second) {
        
        DateTime inputTime = new DateTime(year, month, day, hour, minute, second);
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        
        String formatted_time = formatter.print(inputTime);
        return formatted_time;
    }
}