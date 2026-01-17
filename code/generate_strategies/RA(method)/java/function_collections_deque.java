package com.example;

import com.google.common.collect.Queues; // Guava依赖，用于创建ArrayDeque
import java.util.Deque;
import java.util.ArrayList;
import java.util.List;

public class DequeOperations {

    // 此方法对应 Python 中的 perform_operation 函数
    public String perform_operation(String operation_type) {
        // 使用 Guava 创建 Deque 实例（第三方依赖）
        Deque<Integer> d = Queues.newArrayDeque();

        if ("append_and_appendleft".equals(operation_type)) {
            // Python: d.append(1)
            d.addLast(1);
            // Python: d.appendleft(0)
            d.addFirst(0);
            // 为了获取指定位置的元素, 转换为List
            List<Integer> list = new ArrayList<>(d);
            // Python: return str(d[0]) + ", " + str(d[1])
            return list.get(0).toString() + ", " + list.get(1).toString();
        } else if ("pop_and_popleft".equals(operation_type)) {
            // 创建新 deque, 模拟 Python: deque([1, 2, 3])
            d.clear();
            d.addLast(1);
            d.addLast(2);
            d.addLast(3);
            // Python: d.popleft()
            d.removeFirst();
            // Python: d.pop()
            d.removeLast();
            // Python: return str(len(d))
            return Integer.toString(d.size());
        } else if ("remove".equals(operation_type)) {
            // 创建新 deque, 模拟 Python: deque([1, 2, 3])
            d.clear();
            d.addLast(1);
            d.addLast(2);
            d.addLast(3);
            // Python: d.remove(2)
            d.remove(2);
            List<Integer> list = new ArrayList<>(d);
            // Python: return str(d[0]) + ", " + str(d[1])
            return list.get(0).toString() + ", " + list.get(1).toString();
        } else if ("clear".equals(operation_type)) {
            // 创建新 deque, 模拟 Python: deque([1, 2, 3])
            d.clear();
            d.addLast(1);
            d.addLast(2);
            d.addLast(3);
            // Python: d.clear()
            d.clear();
            // Python: return str(len(d))
            return Integer.toString(d.size());
        } else {
            // Python: raise ValueError("Invalid operation type")
            throw new IllegalArgumentException("Invalid operation type");
        }
    }
}