package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class test_peewee_get {

    private String db_path;
    private function_peewee_get executor;

    @BeforeEach
    public void setUp() {
        // Use a shared database file for tests.
        db_path = "test_db.sqlite";
        executor = new function_peewee_get();
    }

    @Test
    public void test_existing_record() {
        assertEquals("Alice", executor.get_name_by_id(db_path, 1));
    }

    @Test
    public void test_existing_record_2() {
        assertEquals("Bob", executor.get_name_by_id(db_path, 2));
    }

    @Test
    public void test_nonexistent_record() {
        assertEquals("Not Found", executor.get_name_by_id(db_path, 99));
    }

    @Test
    public void test_zero_id() {
        assertEquals("Not Found", executor.get_name_by_id(db_path, 0));
    }

    @Test
    public void test_negative_id() {
        assertEquals("Not Found", executor.get_name_by_id(db_path, -1));
    }
}