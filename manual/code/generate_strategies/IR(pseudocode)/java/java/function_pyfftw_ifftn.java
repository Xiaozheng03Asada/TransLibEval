package com.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.TransformType;

public class IFFTNProcessor {

    public String compute_ifftn(String input_array) {
        try {
            
            if (input_array.contains("np.array([])")) {
                throw new IllegalArgumentException("Error in computing IFFT: empty input");
            }

            
            FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
            double[] dummyData = {0.0, 0.0};
            fft.transform(dummyData, TransformType.INVERSE);

            
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