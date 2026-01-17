package com.example;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class LinearSearchFunction {
    public int linear_search_from_string(String arr, String target) {
        if (StringUtils.isEmpty(arr)) {
            return -1;
        }

        int[] numbers = Arrays.stream(arr.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
        int targetNum = Integer.parseInt(target);

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == targetNum) {
                return i;
            }
        }
        return -1;
    }
}