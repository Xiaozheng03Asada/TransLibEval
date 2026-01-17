
package com.example;

import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.base.Splitter;
import java.util.List;
import java.util.ArrayList;

public class CounterCalculator {
    public String count_elements(String data) {
        if (data == null) return "failed";

        
        List<String> words = Splitter.onPattern("\\s+")
                .omitEmptyStrings()
                .splitToList(data);

        if (words.isEmpty()) return "failed";

        
        Multiset<String> counter = LinkedHashMultiset.create();
        counter.addAll(words);

        
        List<String> entries = new ArrayList<>();
        for (Multiset.Entry<String> entry : counter.entrySet()) {
            entries.add(entry.getElement() + ":" + entry.getCount());
        }
        return String.join(", ", entries);
    }
}