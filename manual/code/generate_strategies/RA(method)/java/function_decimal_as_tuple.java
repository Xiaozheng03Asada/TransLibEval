package com.example;

import java.math.BigDecimal;
import org.apache.commons.lang3.tuple.Triple;

public class GetDecimalTuple {
    public String check_discount_for_large_order(String value) {
        String result = (String) new Object() {
            public String process() {
                try {
                    BigDecimal decimal = new BigDecimal(value);
                    String sign = decimal.signum() < 0 ? "1" : "0";
                    String absStr = decimal.abs().toPlainString();
                    String[] parts = absStr.split("\\.");
                    String digitsStr = parts[0] + (parts.length > 1 ? parts[1] : "");
                    if (digitsStr.equals("0")) {
                        return "0,0,0";
                    }
                    int exponent = 0;
                    if (parts.length > 1) {
                        exponent = -parts[1].length();
                    }
                    return sign + "," + digitsStr + "," + exponent;
                } catch (Exception e) {
                    return "Error";
                }
            }
        }.process();
        return result;
    }
}