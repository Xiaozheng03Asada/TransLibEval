package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_peewee_executor {
    private function_peewee_executor executor;
    private String dbPath;

    @BeforeEach
    void setUp() {
        executor = new function_peewee_executor();
        dbPath = "test_db.sqlite";
    }

    @Test
    void test_insert_query() {
        String result = executor.execute_query(dbPath,
                "INSERT INTO testmodel (name) VALUES ('Alice')");
        assertEquals("0", result);
    }

    @Test
    void test_select_query() {
        String result = executor.execute_query(dbPath,
                "SELECT COUNT(*) FROM testmodel");
        assertTrue(result.matches("\\d+"));
    }

    @Test
    void test_update_query() {
        executor.execute_query(dbPath,
                "INSERT INTO testmodel (name) VALUES ('Bob')");
        String result = executor.execute_query(dbPath,
                "UPDATE testmodel SET name='Charlie' WHERE name='Bob'");
        assertEquals("0", result);
    }

    @Test
    void test_delete_query() {
        executor.execute_query(dbPath,
                "INSERT INTO testmodel (name) VALUES ('David')");
        String result = executor.execute_query(dbPath,
                "DELETE FROM testmodel WHERE name='David'");
        assertEquals("0", result);
    }

    @Test
    void test_invalid_query() {
        assertThrows(RuntimeException.class, () ->
                executor.execute_query(dbPath, "INVALID SQL"));
    }
}