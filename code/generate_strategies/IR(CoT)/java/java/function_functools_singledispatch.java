package com.example;

import org.apache.commons.math3.util.Precision;

public class DataProcessor {
    
    public Object test_singledispatch(Object data) {
        
        if (data instanceof Integer) {
            return ((Integer) data) * 2;
        }
        
        else if (data instanceof String) {
            return ((String) data).toUpperCase();
        }
        
        else if (data instanceof Double) {
            double result = ((Double) data) * 2;
            return Precision.round(result, 2);
        }
        
        else if (data == null) {
            return "NONE";
        }
        
        else {
            throw new UnsupportedOperationException("不支持的数据类型: " + data.getClass().getName());
        }
    }
}