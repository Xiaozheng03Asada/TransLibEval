package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils; // 第三方依赖，用于数值转换

public class HanSeok {
    // 该方法实现了从 Python 中 SortedList 的 bisect_left 功能
    public int find_insert_position(int value, String sorted_list) {
        // 如果输入的列表字符串为空，则使用默认值 "1,3,5,8"
        if (sorted_list == null) {
            sorted_list = "1,3,5,8";
        }
        // 按逗号分割字符串
        String[] parts = sorted_list.split(",");
        // 逐行转换为整数，并存放在 ArrayList 中
        List<Integer> list = new ArrayList<>();
        for (String part : parts) {
            // 使用第三方依赖 NumberUtils 进行转换
            list.add(NumberUtils.toInt(part.trim()));
        }
        // 确保列表是有序的
        Collections.sort(list);
        // 使用 Collections.binarySearch 实现二分查找逻辑（模拟 bisect_left）
        int index = Collections.binarySearch(list, value);
        if (index < 0) {
            // 如果未找到，则二分查找返回 -(插入点) - 1
            index = -index - 1;
        } else {
            // 如果找到了相等的值，则向左查找最左边的位置
            while (index > 0 && list.get(index - 1) == value) {
                index--;
            }
        }
        return index;
    }
}