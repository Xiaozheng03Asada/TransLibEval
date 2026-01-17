package com.example;

import com.google.common.collect.Maps; // Guava 第三方依赖，用于创建排序字典
import java.util.Map;
import java.util.SortedMap;
import org.apache.commons.lang3.tuple.ImmutablePair; // Apache Commons Lang3，用于创建 tuple
import org.apache.commons.lang3.tuple.Pair;

public class SortedDictHandler {
    public String modify_sorted_dict(int index) {
        // 构建排序字典（使用 Guava 的 Maps.newTreeMap）
        SortedMap<Integer, String> sortedDict = Maps.newTreeMap();
        sortedDict.put(3, "three");  // 对应 Python 中的 3: "three"
        sortedDict.put(1, "one");    // 对应 Python 中的 1: "one"
        sortedDict.put(5, "five");   // 对应 Python 中的 5: "five"

        // 处理负索引：负数表示从末尾开始计数
        if (index < 0) {
            index = sortedDict.size() + index;
        }
        // 索引非法则返回错误字符串
        if (index < 0 || index >= sortedDict.size()) {
            return "error: Invalid index";
        }

        // 查找索引位置对应的键值对
        int cur = 0;
        Integer keyToRemove = null;
        String valueToRemove = null;
        for (Map.Entry<Integer, String> entry : sortedDict.entrySet()) {
            if (cur == index) {
                keyToRemove = entry.getKey();
                valueToRemove = entry.getValue();
                break;
            }
            cur++;
        }

        // 从字典中移除该项
        sortedDict.remove(keyToRemove);
        // 利用 Apache Commons Lang3 创建一个 tuple，模拟 Python 的 popitem 返回值
        Pair<Integer, String> removedItem = new ImmutablePair<>(keyToRemove, valueToRemove);

        // 构造剩余字典的字符串表示，格式须为 "{key: 'value', ...}"
        StringBuilder dictString = new StringBuilder();
        dictString.append("{");
        int count = 0;
        for (Map.Entry<Integer, String> entry : sortedDict.entrySet()) {
            if (count > 0) {
                dictString.append(", ");
            }
            dictString.append(entry.getKey()).append(": '").append(entry.getValue()).append("'");
            count++;
        }
        dictString.append("}");

        // 构造被移除元素的字符串表示，格式为 "(key, 'value')"
        String removedItemString = "(" + removedItem.getLeft() + ", '" + removedItem.getRight() + "')";

        return dictString.toString() + " " + removedItemString;
    }
}