package com.example;

import com.google.common.collect.Collections2;
import java.util.Collections;
import java.util.stream.Collectors;

public class RepeatExample {
    public String repeat_element(String element, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
        if (count == 0) {
            return "";
        }
        return Collections.nCopies(count, element)
                .stream()
                .collect(Collectors.joining(", "));
    }
}