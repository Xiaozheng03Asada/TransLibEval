package com.example;

import org.apache.commons.lang3.StringUtils;

public class BitarrayExtender {
    // 此方法实现了原Python中的count_bits功能，要求输入为仅包含'0'和'1'的字符串，
    // 第二个参数必须为0或1，返回出现该位的次数。
    public int count_bits(String ba, int bit) {
        // 检查输入是否为非null字符串
        if (ba == null) {
            throw new IllegalArgumentException("Input must be a non-null string of '0' and '1'.");
        }
        // 检查bit参数是否有效，必须为0或1
        if (bit != 0 && bit != 1) {
            throw new IllegalArgumentException("Invalid bit value: " + bit);
        }
        // 检查字符串中仅出现'0'和'1'
        for (int i = 0; i < ba.length(); i++) {
            char ch = ba.charAt(i);
            if (ch != '0' && ch != '1') {
                throw new IllegalArgumentException("Input string must only contain '0' and '1'.");
            }
        }
        // 根据bit值使用Apache Commons Lang的StringUtils.countMatches方法统计出现次数
        if (bit == 1) {
            return StringUtils.countMatches(ba, "1");
        } else {
            return StringUtils.countMatches(ba, "0");
        }
    }
}