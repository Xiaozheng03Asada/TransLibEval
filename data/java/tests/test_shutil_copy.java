package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_shutil_copy {
    private function_shutil_copy handler;
    private String src_file;
    private String dst_file;

    @BeforeEach
    public void setUp() {
        handler = new function_shutil_copy();
        String result = handler.handle_file_operations(null, "copy", null);
        String[] files = result.split("\\|");
        src_file = files[0];
        dst_file = files[1];
    }

    @AfterEach
    public void tearDown() {
        handler.handle_file_operations(src_file, "remove", null);
        handler.handle_file_operations(dst_file, "remove", null);
    }

    @Test
    public void test_copy_file_to_directory() {
        assertEquals("true", handler.handle_file_operations(dst_file, "exists", null));
    }

    @Test
    public void test_copy_file_content() {
        String src_content = handler.handle_file_operations(src_file, "read", null);
        String dst_content = handler.handle_file_operations(dst_file, "read", null);
        assertEquals(src_content, dst_content);
    }

    @Test
    public void test_copy_file_overwrite_existing_file() {
        String new_content = "New content";
        handler.handle_file_operations(new_content, "copy", dst_file);
        String updated_content = handler.handle_file_operations(dst_file, "read", null);
        assertEquals(new_content, updated_content);
    }

    @Test
    public void test_copy_file_to_current_directory() {
        String current_dir_file = "test_copy.txt";
        String result = handler.handle_file_operations(null, "copy", current_dir_file);
        String dst_file = result.split("\\|")[1];
        assertEquals("true", handler.handle_file_operations(dst_file, "exists", null));
        handler.handle_file_operations(current_dir_file, "remove", null);
    }

    @Test
    public void test_copy_non_existent_file() {
        String non_existent_file = "non_existent.txt";
        assertEquals("false", handler.handle_file_operations(non_existent_file, "exists", null));
    }
}