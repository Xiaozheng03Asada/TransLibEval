package com.example;

import com.google.common.base.Preconditions;

public class InvertBitsFunction {
    public String invert_bitarray(String bits, Integer start, Integer stop) {
        // Validate that bits is not null and consists only of '0' and '1'.
        Preconditions.checkArgument(bits != null && bits.matches("[01]+"),
                "Input must be a string containing only '0' and '1'.");

        int length = bits.length();
        char[] bitChars = bits.toCharArray();

        // If both start and stop are null, invert the entire bit array.
        if (start == null && stop == null) {
            for (int i = 0; i < length; i++) {
                bitChars[i] = (bitChars[i] == '0') ? '1' : '0';
            }
        } else {
            // Both indices must be provided.
            if (start == null || stop == null) {
                throw new IllegalArgumentException("Both start and stop indices must be provided or both null.");
            }
            // Validate indices.
            if (start < 0 || stop > length || start >= stop) {
                throw new IllegalArgumentException("Invalid start and stop indices.");
            }
            for (int i = start; i < stop; i++) {
                bitChars[i] = (bitChars[i] == '0') ? '1' : '0';
            }
        }
        return new String(bitChars);
    }
}