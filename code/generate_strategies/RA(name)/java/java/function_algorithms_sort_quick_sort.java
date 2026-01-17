package com.example;

import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.stream.Collectors;

public class QuickSortFunction {
    public String quick_sort_from_string(String inputStr) {
        // 定义内部quickSort方法
        class QuickSortHelper {
            private void quickSort(int[] arr, int low, int high) {
                if (low < high) {
                    int pi = partition(arr, low, high);
                    quickSort(arr, low, pi - 1);
                    quickSort(arr, pi + 1, high);
                }
            }

            private int partition(int[] arr, int low, int high) {
                int pivot = arr[high];
                int i = (low - 1);

                for (int j = low; j < high; j++) {
                    if (arr[j] < pivot) {
                        i++;
                        int temp = arr[i];
                        arr[i] = arr[j];
                        arr[j] = temp;
                    }
                }

                int temp = arr[i + 1];
                arr[i + 1] = arr[high];
                arr[high] = temp;

                return i + 1;
            }
        }

        // 主要逻辑
        if (StringUtils.isEmpty(inputStr)) {
            return "";
        }

        // 将输入字符串转换为整数数组
        int[] numbers = Arrays.stream(inputStr.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        // 创建Helper实例并执行快速排序
        QuickSortHelper helper = new QuickSortHelper();
        helper.quickSort(numbers, 0, numbers.length - 1);

        // 将排序后的数组转换回字符串
        return Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
    }
}