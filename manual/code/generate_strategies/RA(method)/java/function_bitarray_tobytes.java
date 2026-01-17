package com.example;

import org.apache.commons.lang3.StringUtils;

public class ConvertToBytesFunction {
    public String convert_to_bytes(String bits_str) {
        // 1. 输入验证
        if (bits_str == null || !bits_str.matches("[01]*")) {
            throw new IllegalArgumentException("Input must be a binary string consisting of '0' and '1'.");
        }

        // 2. 如果输入为空，返回空字节数组的字符串表示
        if (bits_str.isEmpty()) {
            return "b''";
        }

        // 3. 将二进制字符串转换为字节
        int padding = 8 - (bits_str.length() % 8);
        if (padding == 8) {
            padding = 0;
        }

        // 补齐8位
        String paddedBits = bits_str + StringUtils.repeat("0", padding);

        // 转换为字节数组
        byte[] bytes = new byte[paddedBits.length() / 8];
        for (int i = 0; i < paddedBits.length(); i += 8) {
            String byteStr = paddedBits.substring(i, i + 8);
            bytes[i / 8] = (byte) Integer.parseInt(byteStr, 2);
        }

        // 4. 构造Python bytes风格的字符串表示
        StringBuilder result = new StringBuilder("b'");
        for (byte b : bytes) {
            result.append(String.format("\\x%02x", b & 0xFF));
        }
        result.append("'");

        return result.toString();
    }
}