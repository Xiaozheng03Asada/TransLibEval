package com.example;

import org.jtransforms.fft.DoubleFFT_1D;
import java.util.regex.*;
import java.util.Random;

public class RFFTProcessor {

    
    public String compute_rfft(String input_array) {
        try {
            double[] data;

            
            if (input_array.startsWith("np.array")) {
                
                Pattern pattern = Pattern.compile("np\\.array\\((\\[.*?\\])\\)");
                Matcher matcher = pattern.matcher(input_array);
                if (matcher.find()) {
                    String arrayContent = matcher.group(1).trim();
                    
                    arrayContent = arrayContent.substring(1, arrayContent.length() - 1).trim();
                    if (arrayContent.isEmpty()) {
                        throw new Exception("Input array is empty.");
                    }
                    String[] parts = arrayContent.split(",");
                    data = new double[parts.length];
                    for (int i = 0; i < parts.length; i++) {
                        String token = parts[i].trim();
                        
                        if (token.startsWith("\"") || token.startsWith("'")) {
                            throw new Exception("Input array must contain numeric data.");
                        }
                        try {
                            data[i] = Double.parseDouble(token);
                        } catch (NumberFormatException nfe) {
                            throw new Exception("Input array must contain numeric data.");
                        }
                    }
                } else {
                    throw new Exception("Unsupported input format for np.array.");
                }
            } else if (input_array.startsWith("np.zeros")) {
                
                Pattern pattern = Pattern.compile("np\\.zeros\\((\\d+)\\)");
                Matcher matcher = pattern.matcher(input_array);
                if (matcher.find()) {
                    int size = Integer.parseInt(matcher.group(1));
                    if (size <= 0) {
                        throw new Exception("Input array is empty.");
                    }
                    data = new double[size];  
                } else {
                    throw new Exception("Unsupported input format for np.zeros.");
                }
            } else if (input_array.startsWith("np.random.random")) {
                
                Pattern pattern = Pattern.compile("np\\.random\\.random\\((\\d+)\\)");
                Matcher matcher = pattern.matcher(input_array);
                if (matcher.find()) {
                    int size = Integer.parseInt(matcher.group(1));
                    if (size <= 0) {
                        throw new Exception("Input array is empty.");
                    }
                    data = new double[size];
                    Random rand = new Random();
                    for (int i = 0; i < size; i++) {
                        data[i] = rand.nextDouble();
                    }
                } else {
                    throw new Exception("Unsupported input format for np.random.random.");
                }
            } else {
                throw new Exception("Unsupported input format.");
            }

            
            if (data.length == 0) {
                throw new Exception("Input array is empty.");
            }

            
            int n = data.length;
            DoubleFFT_1D fft = new DoubleFFT_1D(n);
            
            fft.realForward(data);
            
            int shape = n / 2 + 1;
            return "(" + shape + ",)";
        } catch (Exception e) {
            throw new RuntimeException("Error in computing RFFT: " + e.getMessage());
        }
    }
}