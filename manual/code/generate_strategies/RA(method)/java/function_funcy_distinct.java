package com.example;

import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ListProcessor {
    public String process_list(String input_str) {
        Set<String> uniqueElements = ImmutableSet.copyOf(Arrays.asList(input_str.split(",")));
        return String.join(",", uniqueElements);
    }
}