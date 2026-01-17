package com.example;

import org.apache.commons.math3.util.Precision;

public class DataProcessor {
    // 单一方法实现所有功能，方法名与 Python 中的 test_singledispatch 保持一致
    public Object test_singledispatch(Object data) {
        // 如果数据是 Integer 类型，则返回 data * 2
        if (data instanceof Integer) {
            return ((Integer) data) * 2;
        }
        // 如果数据是 String 类型，则返回字符串大写
        else if (data instanceof String) {
            return ((String) data).toUpperCase();
        }
        // 如果数据是 Double 类型，则返回 data * 2 并保留两位小数
        else if (data instanceof Double) {
            double result = ((Double) data) * 2;
            return Precision.round(result, 2);
        }
        // 如果数据为 null，则返回 "NONE"
        else if (data == null) {
            return "NONE";
        }
        // 对于其他不支持的数据类型，抛出异常
        else {
            throw new UnsupportedOperationException("不支持的数据类型: " + data.getClass().getName());
        }
    }
}