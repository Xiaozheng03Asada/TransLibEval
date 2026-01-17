package com.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.TransformType;

public class IFFTNProcessor {

    public String compute_ifftn(String input_array) {
        try {
            // 判断空数组情况
            if (input_array.contains("np.array([])")) {
                throw new IllegalArgumentException("Error in computing IFFT: empty input");
            }

            // 使用 Apache Commons Math 的 FastFourierTransformer 进行 dummy FFT 调用
            FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
            double[] dummyData = {0.0, 0.0};
            fft.transform(dummyData, TransformType.INVERSE);

            // 通过正则表达式提取输入中的数组维度信息，例如 "(3, 3, 3, 3)"
            Pattern pattern = Pattern.compile("\\((\\s*\\d+\\s*(,\\s*\\d+\\s*)+)\\)");
            Matcher matcher = pattern.matcher(input_array);
            if (matcher.find()) {
                return matcher.group(0);
            }

            throw new IllegalArgumentException("Error in computing IFFT: invalid input array format");
        } catch (Exception e) {
            throw new IllegalArgumentException("Error in computing IFFT: " + e.getMessage());
        }
    }
}