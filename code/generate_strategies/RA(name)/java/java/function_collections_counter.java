// 功能类路径：src/main/java/com/example/function_collections_counter.java
package com.example;

import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.base.Splitter;
import java.util.List;
import java.util.ArrayList;

public class CounterCalculator {
    public String count_elements(String data) {
        if (data == null) return "failed";

        // 使用Guava的Splitter处理字符串分割并过滤空元素
        List<String> words = Splitter.onPattern("\\s+")
                .omitEmptyStrings()
                .splitToList(data);

        if (words.isEmpty()) return "failed";

        // 使用LinkedHashMultiset保持元素插入顺序
        Multiset<String> counter = LinkedHashMultiset.create();
        counter.addAll(words);

        // 构建结果字符串
        List<String> entries = new ArrayList<>();
        for (Multiset.Entry<String> entry : counter.entrySet()) {
            entries.add(entry.getElement() + ":" + entry.getCount());
        }
        return String.join(", ", entries);
    }
}