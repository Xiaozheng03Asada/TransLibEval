package com.example;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;
import java.util.stream.Collectors;

public class ProbabilityExample {
    public String compute_frequency(String data) {
        class TypeError extends RuntimeException {
            public TypeError(String message) {
                super(message);
            }
        }

        if (data == null) {
            throw new TypeError("Input cannot be null");
        }
        if (!(data instanceof String)) {
            throw new TypeError("Input must be a string");
        }

        Bag<Character> freqBag = new HashBag<>();
        data.chars().mapToObj(ch -> (char) ch).forEach(freqBag::add);

        if (freqBag.isEmpty()) {
            return "";
        }

        return freqBag.uniqueSet().stream()
                .sorted()
                .map(ch -> ch + ":" + freqBag.getCount(ch))
                .collect(Collectors.joining(", "));
    }
}