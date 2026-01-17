package com.example;

import org.apache.commons.collections4.list.TreeList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HeapifyFunction {
    public String heapify_from_string(String arr_str) {
        // 在唯一的方法中实现所有功能
        class MinHeap {
            private int[] heap;
            private int size;

            public MinHeap(int[] arr) {
                this.heap = arr.clone();
                this.size = arr.length;
                for (int i = size / 2 - 1; i >= 0; i--) {
                    heapify(i);
                }
            }

            private void heapify(int i) {
                int smallest = i;
                int left = 2 * i + 1;
                int right = 2 * i + 2;

                if (left < size && heap[left] < heap[smallest]) {
                    smallest = left;
                }

                if (right < size && heap[right] < heap[smallest]) {
                    smallest = right;
                }

                if (smallest != i) {
                    int swap = heap[i];
                    heap[i] = heap[smallest];
                    heap[smallest] = swap;
                    heapify(smallest);
                }
            }

            public int[] getHeap() {
                return heap;
            }
        }

        // 将输入字符串转换为整数数组
        int[] arr = Arrays.stream(arr_str.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();

        // 创建并heapify数组
        MinHeap minHeap = new MinHeap(arr);

        // 将结果转换回字符串
        return Arrays.stream(minHeap.getHeap())
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
    }
}