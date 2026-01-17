package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class test_Whoosh_terms {

    private String indexDir = "test_list_terms_index";
    private function_Whoosh_terms func;

    @BeforeEach
    public void setUp() {
        func = new function_Whoosh_terms();
        func.manage_index_and_list_terms(indexDir, "content", true);
    }

    @AfterEach
    public void tearDown() {
        File indexDirectory = new File(indexDir);
        if (indexDirectory.exists()) {
            try {
                deleteDirectory(indexDirectory.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Recursive function to delete a directory and its contents
    private void deleteDirectory(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            Files.list(path).forEach(p -> {
                try {
                    deleteDirectory(p);
                } catch (IOException e) {
                    // Handle exception appropriately
                    e.printStackTrace();
                }
            });
        }
        Files.delete(path);
    }

    @Test
    public void testListTerms() {
        String terms = func.manage_index_and_list_terms(indexDir, "content", true);
        assertNotNull(terms);
        assertTrue(terms.toLowerCase().contains("hello"));
        assertTrue(terms.toLowerCase().contains("test"));
        assertTrue(terms.length() > 0);
    }

    @Test
    public void testInvalidIndexDirectory() {
        String invalidIndexDir = "invalid_index";
        File invalidDir = new File(invalidIndexDir);
        if (invalidDir.exists()) {
            try {
                deleteDirectory(invalidDir.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        assertThrows(IllegalArgumentException.class, () -> {
            func.manage_index_and_list_terms(invalidIndexDir, "content", false);
        });
    }

    @Test
    public void testCaseInsensitivity() {
        String terms = func.manage_index_and_list_terms(indexDir, "content", true);
        assertNotNull(terms);
        assertTrue(terms.toLowerCase().contains("whoosh"));
    }

    @Test
    public void testWordFrequency() {
        String terms = func.manage_index_and_list_terms(indexDir, "content", true);
        assertNotNull(terms);
        assertNotEquals(0, terms.split(", ").length);
    }

    @Test
    public void testTermExistence() {
        String terms = func.manage_index_and_list_terms(indexDir, "content", true);
        assertNotNull(terms);
        assertTrue(terms.toLowerCase().contains("whoosh"));
        assertFalse(terms.toLowerCase().contains("nonexistentword"));
    }
}