package com.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.common.collect.Iterables;

public class CycleProcessor {
    
    public String test_cycle(String input_sequence, int num_elements) {
        
        if (input_sequence == null || input_sequence.isEmpty()) {
            return "";
        }
        
        List<Character> charList = new ArrayList<>();
        for (int i = 0; i < input_sequence.length(); i++) {
            charList.add(input_sequence.charAt(i));
        }
        
        Iterable<Character> cycleIterable = Iterables.cycle(charList);
        Iterator<Character> cycleIterator = cycleIterable.iterator();

        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < num_elements; i++) {
            result.append(cycleIterator.next());
        }
        return result.toString();
    }
}