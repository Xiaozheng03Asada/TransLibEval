package com.example;

import org.jtransforms.fft.DoubleFFT_1D;
import java.util.regex.*;
import java.util.Random;

public class RFFTProcessor {

    // 此方法将所有功能封装在一个方法中，函数名与Python代码相同：compute_rfft
    public String compute_rfft(String input_array) {
        try {
            double[] data;

            // 判断输入字符串格式
            if (input_array.startsWith("np.array")) {
                // 匹配 np.array([...])
                Pattern pattern = Pattern.compile("np\\.array\\((\\[.*?\\])\\)");
                Matcher matcher = pattern.matcher(input_array);
                if (matcher.find()) {
                    String arrayContent = matcher.group(1).trim();
                    // 去掉首尾中括号
                    arrayContent = arrayContent.substring(1, arrayContent.length() - 1).trim();
                    if (arrayContent.isEmpty()) {
                        throw new Exception("Input array is empty.");
                    }
                    String[] parts = arrayContent.split(",");
                    data = new double[parts.length];
                    for (int i = 0; i < parts.length; i++) {
                        String token = parts[i].trim();
                        // 如果元素含有双引号或单引号则认为不是数字
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
                // 匹配 np.zeros(n)
                Pattern pattern = Pattern.compile("np\\.zeros\\((\\d+)\\)");
                Matcher matcher = pattern.matcher(input_array);
                if (matcher.find()) {
                    int size = Integer.parseInt(matcher.group(1));
                    if (size <= 0) {
                        throw new Exception("Input array is empty.");
                    }
                    data = new double[size];  // 默认初始化为0.0
                } else {
                    throw new Exception("Unsupported input format for np.zeros.");
                }
            } else if (input_array.startsWith("np.random.random")) {
                // 匹配 np.random.random(n)
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

            // 如果数组为空，直接抛出异常
            if (data.length == 0) {
                throw new Exception("Input array is empty.");
            }

            // 调用第三方FFT库实现RFFT（此处实际调用JTransforms的realForward方法）
            int n = data.length;
            DoubleFFT_1D fft = new DoubleFFT_1D(n);
            // 实际计算RFFT（in-place变换）
            fft.realForward(data);
            // 根据RFFT输出规则，结果长度为 n/2+1
            int shape = n / 2 + 1;
            return "(" + shape + ",)";
        } catch (Exception e) {
            throw new RuntimeException("Error in computing RFFT: " + e.getMessage());
        }
    }
}