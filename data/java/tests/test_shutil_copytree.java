package com.example;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class test_shutil_copytree {
    private function_shutil_copytree handler;
    private String srcDir;
    private String destDir;
    private File file1;
    private File file2;
    private File file3InSubdir;

    @BeforeEach
    void setUp() throws IOException {
        handler = new function_shutil_copytree();
        srcDir = "test_src_dir";
        destDir = "test_dest_dir";

        // Create source directory and files
        File srcDirFile = new File(srcDir);
        srcDirFile.mkdirs();

        file1 = new File(srcDir, "file1.txt");
        file2 = new File(srcDir, "file2.txt");
        File subdir = new File(srcDir, "subdir");
        subdir.mkdirs();
        file3InSubdir = new File(subdir, "file3.txt");

        Files.writeString(file1.toPath(), "This is file 1.");
        Files.writeString(file2.toPath(), "This is file 2.");
        Files.writeString(file3InSubdir.toPath(), "This is file 3 in subdir.");
    }

    @AfterEach
    void tearDown() {
        deleteDirectory(new File(srcDir));
        deleteDirectory(new File(destDir));
    }

    private void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }

    @Test
    void test_copy_directory() throws IOException {
        String result = handler.handle_directory_operations(srcDir, destDir, "copy");
        assertTrue(result.contains("Directory copied from " + srcDir + " to " + destDir));
    }

    @Test
    void test_copy_directory_exists_error() throws IOException {
        new File(destDir).mkdirs();
        assertThrows(IllegalArgumentException.class, () ->
                handler.handle_directory_operations(srcDir, destDir, "copy")
        );
    }

    @Test
    void test_copy_directory_source_not_found() {
        String nonExistingDir = "non_existing_dir";
        assertThrows(IOException.class, () ->
                handler.handle_directory_operations(nonExistingDir, destDir, "copy")
        );
    }

    @Test
    void test_copy_directory_with_permissions() throws IOException {
        String result = handler.handle_directory_operations(srcDir, destDir, "copy");
        assertTrue(result.contains("Directory copied from " + srcDir + " to " + destDir));
    }

    @Test
    void test_copy_directory_with_hidden_files() throws IOException {
        File hiddenFile = new File(srcDir, ".hidden_file.txt");
        Files.writeString(hiddenFile.toPath(), "This is a hidden file.");
        String result = handler.handle_directory_operations(srcDir, destDir, "copy");
        assertTrue(result.contains("Directory copied from " + srcDir + " to " + destDir));
    }
}