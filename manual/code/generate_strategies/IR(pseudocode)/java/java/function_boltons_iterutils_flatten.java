package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;

public class DataFlattener {

    public String flatten_data(String dataStr) {
        if (!(dataStr instanceof String)) {
            return "Error";
        }

        if (dataStr == null) {
            return "Error";
        }

        try {
            String[] parts = dataStr.split(";");
            List<Integer[]> data = new ArrayList<>();
            for (String part : parts) {
                String[] nums = part.split(",");
                Integer[] intNums = new Integer[nums.length];
                for (int i = 0; i < nums.length; i++) {
                    intNums[i] = Integer.parseInt(nums[i]);
                }
                data.add(intNums);
            }

            List<Integer> flattenedList = new ArrayList<>();
            for (Integer[] arr : data) {
                flattenedList.addAll(Arrays.asList(arr));
            }

            return flattenedList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));

        } catch (Exception e) {
            return "Error";
        }
    }
}