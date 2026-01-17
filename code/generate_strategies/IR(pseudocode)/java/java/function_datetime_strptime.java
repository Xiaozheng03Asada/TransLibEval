package com.example;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateParser {

    
    public String parse_date(String date_str, String format_str) {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(format_str);
            DateTime dt = formatter.parseDateTime(date_str);
            
            return dt.toString("yyyy-MM-dd HH:mm:ss");
        } catch (IllegalArgumentException e) {
            
            return "failed";
        }
    }
}