package com.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.common.collect.Iterables;

public class CycleProcessor {
    // 所有功能均封装在唯一的 test_cycle 方法中
    public String test_cycle(String input_sequence, int num_elements) {
        // 如果输入序列为空，则返回空字符串
        if (input_sequence == null || input_sequence.isEmpty()) {
            return "";
        }
        // 将字符串中的每个字符转换为Character列表
        List<Character> charList = new ArrayList<>();
        for (int i = 0; i < input_sequence.length(); i++) {
            charList.add(input_sequence.charAt(i));
        }
        // 使用Guava的Iterables.cycle实现无限循环迭代
        Iterable<Character> cycleIterable = Iterables.cycle(charList);
        Iterator<Character> cycleIterator = cycleIterable.iterator();

        StringBuilder result = new StringBuilder();
        // 按照 num_elements 次取出字符
        for (int i = 0; i < num_elements; i++) {
            result.append(cycleIterator.next());
        }
        return result.toString();
    }
}