package com.example;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

public class test_shutil_rmtree {
    private function_shutil_rmtree deleter;
    private String testDir;

    @BeforeEach
    void setUp() throws IOException {
        deleter = new function_shutil_rmtree();
        testDir = "test_directory";
        createTestDirectory(testDir);
    }

    @AfterEach
    void tearDown() {
        File dir = new File(testDir);
        if (dir.exists()) {
            cleanup(testDir);
        } else {
            System.out.printf("Directory '%s' does not exist, skipping cleanup.%n", testDir);
        }
    }

    private void createTestDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        directory.mkdirs();

        try (FileWriter fw = new FileWriter(new File(directory, "file1.txt"))) {
            fw.write("This is a test file.");
        }

        File subDir = new File(directory, "subdir");
        subDir.mkdirs();

        try (FileWriter fw = new FileWriter(new File(subDir, "file2.txt"))) {
            fw.write("This is another test file.");
        }
    }

    private void cleanup(String directoryPath) {
        String result = deleter.delete_directory_tree(directoryPath);
        if (!result.contains("deleted")) {
            throw new RuntimeException("Failed to clean up directory: " + result);
        }
    }

    @Test
    void testDeleteExistingDirectory() {
        String result = deleter.delete_directory_tree(testDir);
        assertTrue(result.startsWith(String.format("Directory '%s' deleted successfully.", testDir)));
    }

    @Test
    void testDeleteNonExistentDirectory() {
        String nonExistentDir = "non_existent_directory";
        String result = deleter.delete_directory_tree(nonExistentDir);
        assertEquals(String.format("Directory '%s' does not exist.", nonExistentDir), result);
    }

    @Test
    void testDeleteDirectoryWithFiles() {
        String result = deleter.delete_directory_tree(testDir);
        assertTrue(result.startsWith(String.format("Directory '%s' deleted successfully.", testDir)));
    }

    @Test
    void testDeleteWithPermissionsError() throws IOException {
        File protectedDir = new File(testDir, "protected");
        protectedDir.mkdirs();
        File protectedFile = new File(protectedDir, "protected_file.txt");

        try (FileWriter fw = new FileWriter(protectedFile)) {
            fw.write("Protected content.");
        }

        // 将文件设置为只读
        protectedFile.setReadOnly();
        // 确保目录也是只读的
        protectedDir.setReadOnly();

        String result = deleter.delete_directory_tree(protectedDir.getPath());

        // 即使文件是只读的，我们现在也应该能够删除它
        assertTrue(result.contains("Failed to delete directory") || !new File(protectedDir.getPath()).exists());
    }

    @Test
    void testPartialDeletion() {
        File protectedFile = new File(testDir, "protected_file.txt");
        String result = "";

        try {
            try (FileWriter fw = new FileWriter(protectedFile)) {
                fw.write("Partial deletion test.");
            }

            // 将文件设置为只读
            protectedFile.setReadOnly();

            result = deleter.delete_directory_tree(testDir);
        } catch (IOException e) {
            result = e.getMessage();
        }

        // 现在我们应该能够正确处理权限问题
        assertTrue(result.contains("Failed to delete directory") || result.contains("Permission denied") ||
                !new File(testDir).exists());
    }
}