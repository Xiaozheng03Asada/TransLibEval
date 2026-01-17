package com.example;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeModifier {

    
    public String get_current_datetime(String date_string) {
        if (date_string != null && !date_string.isEmpty()) {
            try {
                
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime dt = formatter.parseDateTime(date_string);
                return dt.toString("yyyy-MM-dd HH:mm:ss");
            } catch (IllegalArgumentException e) {
                
                throw new IllegalArgumentException("The date string format is incorrect.");
            }
        } else {
            return "1900-01-01 00:00:00";
        }
    }
}