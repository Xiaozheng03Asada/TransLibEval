package com.example;

import org.apache.commons.lang3.StringUtils;

public class ConvertToBytesFunction {
    public String convert_to_bytes(String bits_str) {
        
        if (bits_str == null || !bits_str.matches("[01]*")) {
            throw new IllegalArgumentException("Input must be a binary string consisting of '0' and '1'.");
        }

        
        if (bits_str.isEmpty()) {
            return "b''";
        }

        
        int padding = 8 - (bits_str.length() % 8);
        if (padding == 8) {
            padding = 0;
        }

        
        String paddedBits = bits_str + StringUtils.repeat("0", padding);

        
        byte[] bytes = new byte[paddedBits.length() / 8];
        for (int i = 0; i < paddedBits.length(); i += 8) {
            String byteStr = paddedBits.substring(i, i + 8);
            bytes[i / 8] = (byte) Integer.parseInt(byteStr, 2);
        }

        
        StringBuilder result = new StringBuilder("b'");
        for (byte b : bytes) {
            result.append(String.format("\\x%02x", b & 0xFF));
        }
        result.append("'");

        return result.toString();
    }
}