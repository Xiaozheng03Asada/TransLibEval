package com.example;

import org.apache.commons.collections4.ListUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChunkManager {

    public String chunk_data(String data_str, int chunk_size) {
        if (data_str == null || !(data_str instanceof String) || !(chunk_size instanceof Integer)) {
            return "Error";
        }

        try {
            List<String> data = Arrays.asList(data_str.split(","));
            List<List<String>> result = ListUtils.partition(data, chunk_size);

            return result.stream()
                    .map(chunk -> String.join(",", chunk))
                    .collect(Collectors.joining(";"));
        } catch (Exception e) {
            return "Error";
        }
    }
}