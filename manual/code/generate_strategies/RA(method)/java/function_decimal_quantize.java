package com.example;

import org.apache.commons.math3.util.Precision;
import java.text.DecimalFormat;
import java.text.ParseException;

public class SetDecimalPrecision {
    public String check_discount_for_large_order(String value, int precision) {
        try {
            // 将字符串解析为double
            double doubleValue = Double.parseDouble(value);
            // 使用Apache Commons Math的Precision.round进行精确舍入
            double roundedValue = Precision.round(doubleValue, precision);

            // 创建格式化器以确保显示指定的小数位数
            StringBuilder pattern = new StringBuilder("#.");
            for (int i = 0; i < precision; i++) {
                pattern.append("0");
            }
            DecimalFormat df = new DecimalFormat(pattern.toString());

            return df.format(roundedValue);
        } catch (NumberFormatException e) {
            return "Error";
        }
    }
}