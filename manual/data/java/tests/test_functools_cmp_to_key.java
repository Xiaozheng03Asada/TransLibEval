package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class test_functools_cmp_to_key {

    private function_functools_cmp_to_key manager;

    @BeforeEach
    public void setUp() {
        manager = new function_functools_cmp_to_key();
    }

    @Test
    public void test_add_operation() {
        String result = manager.manage_number_dict("add,1,10;add,2,20;");
        // 此处“add”操作不产生返回输出，因此期望返回空字符串
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void test_remove_operation() {
        String result = manager.manage_number_dict("add,1,10;remove,1;get,1;");
        String expected = "default";
        assertEquals(expected, result);
    }

    @Test
    public void test_get_operation() {
        String result = manager.manage_number_dict("add,3,30;get,3;");
        String expected = "30";
        assertEquals(expected, result);
    }

    @Test
    public void test_sort_operation() {
        String result = manager.manage_number_dict("add,3,30;add,1,10;add,2,20;sort;");
        String expected = "1:10 2:20 3:30";
        assertEquals(expected, result);
    }

    @Test
    public void test_sum_operation() {
        String result = manager.manage_number_dict("add,1,10;add,2,20;sum;");
        String expected = "30";
        assertEquals(expected, result);
    }
}