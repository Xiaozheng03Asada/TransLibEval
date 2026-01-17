package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_peewee_insert {
    private function_peewee_insert executor;
    private String db_path;

    // 对应 Python 中的 setUp 方法
    @BeforeEach
    public void setUp() {
        executor = new function_peewee_insert();
        db_path = "test.db";
    }

    @Test
    public void test_valid_insert() {
        String result = executor.insert_record(db_path, "Alice", 25);
        // 应返回插入记录的ID
        assertTrue(result.matches("\\d+"));
    }

    @Test
    public void test_empty_name() {
        String result = executor.insert_record(db_path, "", 30);
        // 即使名字为空，也应成功插入并返回数字ID
        assertTrue(result.matches("\\d+"));
    }

    @Test
    public void test_zero_age() {
        String result = executor.insert_record(db_path, "Bob", 0);
        // 允许年龄为0，返回记录ID
        assertTrue(result.matches("\\d+"));
    }

    @Test
    public void test_negative_age() {
        String result = executor.insert_record(db_path, "Charlie", -5);
        // 允许负数年龄，数据库应处理并返回记录ID
        assertTrue(result.matches("\\d+"));
    }

    @Test
    public void test_large_age() {
        String result = executor.insert_record(db_path, "Dave", 150);
        // 允许较大的年龄值，返回记录ID
        assertTrue(result.matches("\\d+"));
    }
}