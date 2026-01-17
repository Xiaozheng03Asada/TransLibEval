package com.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

public class test_peewee_execute {

    private function_peewee_execute executor;
    private String dbPath;

    @BeforeEach
    public void setUp() {
        executor = new function_peewee_execute();
        dbPath = "test_db.sqlite";
        // Ensure the database file is deleted before each test
        File dbFile = new File(dbPath);
        if (dbFile.exists()) {
            dbFile.delete();
        }
    }

    @AfterEach
    public void tearDown() {
        // Clean up the database file after each test
        File dbFile = new File(dbPath);
        if (dbFile.exists()) {
            dbFile.delete();
        }
    }


    @Test
    public void test_create_table() {
        String result = executor.execute_query(dbPath, "CREATE TABLE IF NOT EXISTS test (name TEXT);");
        assertEquals("0", result);
    }

    @Test
    public void test_insert_data() {
        executor.execute_query(dbPath, "CREATE TABLE IF NOT EXISTS test (name TEXT);");
        String result = executor.execute_query(dbPath, "INSERT INTO test (name) VALUES ('Alice');");
        assertEquals("1", result);
    }

    @Test
    public void test_multiple_inserts() {
        executor.execute_query(dbPath, "CREATE TABLE IF NOT EXISTS test (name TEXT);");
        String result = executor.execute_query(dbPath, "INSERT INTO test (name) VALUES ('Bob'), ('Charlie');");
        assertEquals("2", result);
    }

    @Test
    public void test_update_data() {
        executor.execute_query(dbPath, "CREATE TABLE IF NOT EXISTS test (name TEXT);");
        executor.execute_query(dbPath, "INSERT INTO test (name) VALUES ('David');");
        String result = executor.execute_query(dbPath, "UPDATE test SET name = 'Dave' WHERE name = 'David';");
        assertEquals("1", result);
    }

    @Test
    public void test_delete_data() {
        executor.execute_query(dbPath, "CREATE TABLE IF NOT EXISTS test (name TEXT);");
        executor.execute_query(dbPath, "INSERT INTO test (name) VALUES ('Eve');");
        String result = executor.execute_query(dbPath, "DELETE FROM test WHERE name = 'Eve';");
        assertEquals("1", result);
    }
}