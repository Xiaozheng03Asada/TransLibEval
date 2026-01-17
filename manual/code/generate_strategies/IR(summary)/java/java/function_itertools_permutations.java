package com.example;

import org.apache.commons.collections4.iterators.PermutationIterator;
import java.util.*;
import java.util.stream.Collectors;

public class PermutationsProcessor {
    public String get_permutations(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data must be a string.");
        }

        List<Character> chars = data.chars()
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.toList());

        
        PermutationIterator<Character> permutations = new PermutationIterator<>(chars);
        List<String> permsList = new ArrayList<>();
        Map<String, Integer> freqMap = new HashMap<>();

        
        while (permutations.hasNext()) {
            String perm = permutations.next().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining());
            permsList.add(perm);
            freqMap.merge(perm, 1, Integer::sum);
        }

        
        Collections.sort(permsList);

        
        String permsStr = String.join(", ", permsList);
        String freqStr = freqMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));

        return permsStr + "\n" + freqStr;
    }
}