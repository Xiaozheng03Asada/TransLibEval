package com.example;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeModifier {
    
    public String get_modified_datetime(String original_datetime_str, int days, int hours, int weeks) {
        
        if (original_datetime_str == null) {
            throw new IllegalArgumentException("original_datetime must be a string");
        }
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime original_datetime;
        try {
            
            original_datetime = formatter.parseDateTime(original_datetime_str);
        } catch (IllegalArgumentException e) {
            
            throw new IllegalArgumentException("original_datetime_str format is invalid");
        }
        
        DateTime modified_datetime = original_datetime.plusDays(days).plusHours(hours).plusWeeks(weeks);
        
        return formatter.print(modified_datetime);
    }
}