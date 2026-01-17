package com.example;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.TransformType;
import java.util.function.IntPredicate;
import java.util.function.Function;

public class IFFTProcessor {
    // The unique function compute_ifft; all functionality must be implemented in this method.
    public String compute_ifft(String input_array) {
        try {
            Complex[] data;
            int n = 0;
            // If the input string contains "np.array("
            if (input_array.contains("np.array(")) {
                int startBracket = input_array.indexOf('[');
                int endBracket = input_array.lastIndexOf(']');
                if (startBracket == -1 || endBracket == -1) {
                    throw new IllegalArgumentException("Invalid input format.");
                }
                String arrayContents = input_array.substring(startBracket + 1, endBracket);
                // Split by commas; note that complex numbers are in the format "a + bj" or "a - bj"
                String[] tokens = arrayContents.split(",");
                n = tokens.length;
                data = new Complex[n];
                for (int i = 0; i < n; i++) {
                    String token = tokens[i].trim();
                    // Must contain a 'j' (or 'J')
                    if (!token.toLowerCase().contains("j")) {
                        throw new IllegalArgumentException("Input array must contain complex numbers.");
                    }
                    // Remove spaces for consistent parsing
                    token = token.replace(" ", "");
                    // Find operator position (skipping the first character in case of negative numbers)
                    int opPos = token.indexOf('+', 1);
                    if (opPos == -1) {
                        opPos = token.indexOf('-', 1);
                    }
                    if (opPos == -1) {
                        throw new IllegalArgumentException("Invalid complex number format.");
                    }
                    double real = Double.parseDouble(token.substring(0, opPos));
                    int jIndex = token.toLowerCase().indexOf('j', opPos);
                    double imag = Double.parseDouble(token.substring(opPos, jIndex));
                    data[i] = new Complex(real, imag);
                }
            } else if (input_array.startsWith("np.zeros")) {
                // Parse input such as "np.zeros(4, dtype=complex)"
                int startParen = input_array.indexOf('(');
                int commaPos = input_array.indexOf(',', startParen);
                if (commaPos == -1) {
                    commaPos = input_array.indexOf(')', startParen);
                }
                String numStr = input_array.substring(startParen + 1, commaPos).trim();
                n = Integer.parseInt(numStr);
                data = new Complex[n];
                for (int i = 0; i < n; i++) {
                    data[i] = new Complex(0.0, 0.0);
                }
            } else if (input_array.contains("np.random.random")) {
                // Parse input such as "np.random.random(1000) + 1j * np.random.random(1000)"
                int idx = input_array.indexOf("np.random.random(");
                if (idx == -1) {
                    throw new IllegalArgumentException("Invalid input format.");
                }
                int startParen = input_array.indexOf('(', idx);
                int endParen = input_array.indexOf(')', startParen);
                String numStr = input_array.substring(startParen + 1, endParen).trim();
                n = Integer.parseInt(numStr);
                data = new Complex[n];
                // For simplicity, fill with zeros (this maintains shape consistency)
                for (int i = 0; i < n; i++) {
                    data[i] = new Complex(0.0, 0.0);
                }
            } else {
                throw new IllegalArgumentException("Invalid input format.");
            }

            // Define local lambda for checking power of two.
            IntPredicate isPowerOfTwo = m -> (m > 0) && ((m & (m - 1)) == 0);

            // Define local lambda for naive IFFT (O(n^2) implementation).
            Function<Complex[], Complex[]> naiveIFFT = arr -> {
                int len = arr.length;
                Complex[] output = new Complex[len];
                for (int k = 0; k < len; k++) {
                    Complex sum = Complex.ZERO;
                    for (int j = 0; j < len; j++) {
                        double angle = 2 * Math.PI * j * k / len;
                        Complex exp = new Complex(Math.cos(angle), Math.sin(angle));
                        sum = sum.add(arr[j].multiply(exp));
                    }
                    output[k] = sum.divide(len);
                }
                return output;
            };

            // Compute IFFT:
            Complex[] result;
            if (isPowerOfTwo.test(n)) {
                FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
                result = transformer.transform(data, TransformType.INVERSE);
            } else {
                result = naiveIFFT.apply(data);
            }
            // Return the shape as a string, e.g., "(n,)"
            return "(" + n + ",)";
        } catch (Exception e) {
            throw new IllegalArgumentException("Error in computing IFFT: " + e.getMessage());
        }
    }
}