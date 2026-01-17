package com.example;

import org.apache.commons.lang3.StringUtils;

public class BitarrayExtender {
    // 此方法实现了原Python中的extend_bits功能，
    // 要求输入bits和values均为只包含'0'和'1'的字符串，
    // 如果bits不是字符串则抛出TypeError，
    // 如果values中存在非'0'或'1'的字符则抛出ValueError，
    // 最后返回扩展后的二进制字符串。
    public String extend_bits(String bits, String values) {
        // 检查bits是否为非null字符串，否则模拟TypeError
        if (bits == null) {
            throw new IllegalArgumentException("Input must be a string of '0' and '1'.");
        }
        // 检查values是否为非null字符串，否则模拟TypeError
        if (values == null) {
            throw new IllegalArgumentException("Iterable input must be a string of '0' and '1'.");
        }
        // 遍历values中的每个字符，判断是否都是'0'或'1'
        for (int i = 0; i < values.length(); i++) {
            char v = values.charAt(i);
            if (v != '0' && v != '1') {
                throw new IllegalArgumentException("All elements in the iterable must be '0' or '1'.");
            }
        }
        // 利用Apache Commons Lang第三方依赖实现字符串连接操作，模拟bitarray的extend操作
        String result = StringUtils.join(new String[]{bits, values}, "");
        return result;
    }
}