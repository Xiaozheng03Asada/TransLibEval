package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class test_collections_deque {

    // 实例化 function 部分的类
    private function_collections_deque instance = new function_collections_deque();

    @Test
    public void test_append_and_appendleft() {
        String result = instance.perform_operation("append_and_appendleft");
        // 检查返回的结果是否与 "0, 1" 相同
        assertEquals("0, 1", result);
    }

    @Test
    public void test_pop_and_popleft() {
        String result = instance.perform_operation("pop_and_popleft");
        // 经过 popleft 与 pop 后仅剩1个元素，长度为 "1"
        assertEquals("1", result);
    }

    @Test
    public void test_remove() {
        String result = instance.perform_operation("remove");
        // 移除2后，剩余元素为 "1, 3"
        assertEquals("1, 3", result);
    }

    @Test
    public void test_clear() {
        String result = instance.perform_operation("clear");
        // 清空后长度为 "0"
        assertEquals("0", result);
    }

    @Test
    public void test_invalid_operation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            instance.perform_operation("invalid_operation");
        });
        assertEquals("Invalid operation type", exception.getMessage());
    }
}