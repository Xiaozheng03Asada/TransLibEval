package com.example;

import com.google.common.collect.TreeMultiset;
import java.util.List;
import java.util.ArrayList;

public class SortedListHandler {

    // 此方法实现了从构建排序列表、添加元素、删除指定位置的元素、异常捕获，
    // 并返回格式化后的字符串（输入为 int，输出为 String）
    public String modify_sorted_list(int index) {
        // 使用 Guava 的 TreeMultiset 模拟 SortedList，TreeMultiset允许重复元素，并保持自然排序
        TreeMultiset<Integer> sortedList = TreeMultiset.create();
        // 逐行添加各个元素（注意添加顺序与原代码相同，最后得到的有序列表为 [1, 3, 5, 8]）
        sortedList.add(5);
        sortedList.add(3);
        sortedList.add(8);
        sortedList.add(1);
        try {
            // 因为 TreeMultiset 不支持按下标直接 pop，
            // 故将当前集合转换成 ArrayList（转换后顺序为自然排序）
            List<Integer> list = new ArrayList<>(sortedList);
            if(index < 0 || index >= list.size()){
                throw new IndexOutOfBoundsException();
            }
            // 取得并移除指定下标的元素（仅移除一个出现的元素）
            int removedItem = list.get(index);
            sortedList.remove(removedItem);
            // 将剩余元素转换为列表形式
            List<Integer> remaining = new ArrayList<>(sortedList);
            // 组合返回结果格式必须与 python 代码完全一致
            return "Removed item: " + removedItem + ", Remaining list: " + remaining.toString();
        } catch(IndexOutOfBoundsException e) {
            return "Index out of range";
        }
    }
}