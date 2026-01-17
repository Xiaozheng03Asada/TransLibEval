package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class test_shutil_move {
    private function_shutil_move mover;
    private final String srcFile = "test_file.txt";
    private final String srcDir = "test_dir";
    private final String destDir = "dest_dir";

    @BeforeEach
    void setUp() throws IOException {
        mover = new function_shutil_move();
        mover.perform_task("create_file", srcFile + ",This is a test file.");
        mover.perform_task("create_directory", srcDir);
        mover.perform_task("create_file", srcDir + "/nested_file.txt,This is a file in a directory.");
        mover.perform_task("create_directory", destDir);
    }

    @AfterEach
    void tearDown() throws IOException {
        for (String path : new String[]{srcFile, srcDir, destDir}) {
            if ("True".equals(mover.perform_task("path_exists", path))) {
                mover.perform_task("remove_path", path);
            }
        }
    }

    @Test
    void test_move_file() throws IOException {
        String movedPath = mover.perform_task("move", srcFile + "," + destDir);
        assertEquals("True", mover.perform_task("path_exists", destDir + "/test_file.txt"));
        assertEquals("False", mover.perform_task("path_exists", srcFile));
    }

    @Test
    void test_move_nonexistent_file() {
        assertThrows(IOException.class, () ->
                mover.perform_task("move", "nonexistent.txt,dest_dir")
        );
    }

    @Test
    void test_move_file_to_same_location() throws IOException {
        String movedPath = mover.perform_task("move", srcFile + "," + srcFile);
        assertEquals("True", mover.perform_task("path_exists", srcFile));
        assertEquals(srcFile, movedPath);
    }

    @Test
    void test_move_to_nonexistent_directory() throws IOException {
        String nonExistingDest = "non_existing_dest_dir";
        if ("True".equals(mover.perform_task("path_exists", nonExistingDest))) {
            mover.perform_task("remove_path", nonExistingDest);
        }
        String movedPath = mover.perform_task("move", srcDir + "," + nonExistingDest);
        assertEquals("True", mover.perform_task("path_exists", nonExistingDest));
        assertEquals("False", mover.perform_task("path_exists", srcDir));

        mover.perform_task("remove_path", nonExistingDest);
    }

    @Test
    void test_move_large_file() throws IOException {
        String largeFilePath = srcDir + "/large_file.txt";
        StringBuilder largeContent = new StringBuilder();
        for (int i = 0; i < 1000000; i++) {
            largeContent.append('\0');
        }
        mover.perform_task("create_file", largeFilePath + "," + largeContent);
        mover.perform_task("move", largeFilePath + "," + destDir);
        assertEquals("True", mover.perform_task("path_exists", destDir + "/large_file.txt"));
        assertEquals("False", mover.perform_task("path_exists", largeFilePath));
    }
}