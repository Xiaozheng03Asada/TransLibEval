package com.example;

import org.junit.jupiter.api.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class test_Whoosh_index_create_in {
    private String indexDir;
    private function_Whoosh_index_create_in indexManager;

    @BeforeEach
    public void setUp() {
        indexDir = "test_index";
        indexManager = new function_Whoosh_index_create_in();
    }

    @AfterEach
    public void tearDown() {
        deleteDirectory(new File(indexDir));
    }

    private void deleteDirectory(File directory) {
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

    @Test
    public void testCreateIndex() {
        String result = indexManager.add_and_search(indexDir, null, null);
        Assertions.assertEquals("No action performed", result);
        File dir = new File(indexDir);
        Assertions.assertTrue(dir.exists());
        Assertions.assertTrue(dir.isDirectory());
    }

    @Test
    public void testAddDocument() {
        String result = indexManager.add_and_search(indexDir, "This is a test document.", null);
        Assertions.assertEquals("Document added successfully", result);
        result = indexManager.add_and_search(indexDir, null, "test");
        Assertions.assertEquals("This is a test document.", result);
    }

    @Test
    public void testSearchDocument() {
        indexManager.add_and_search(indexDir, "This is another test document.", null);
        indexManager.add_and_search(indexDir, "This is a different document.", null);
        String result = indexManager.add_and_search(indexDir, null, "different");
        Assertions.assertEquals("This is a different document.", result);
    }

    @Test
    public void testMultipleDocuments() {
        indexManager.add_and_search(indexDir, "Document one", null);
        indexManager.add_and_search(indexDir, "Document two", null);
        indexManager.add_and_search(indexDir, "Document three", null);
        String result = indexManager.add_and_search(indexDir, null, "Document");
        Assertions.assertEquals("Document one Document two Document three", result);
    }

    @Test
    public void testSearchNonexistentKeyword() {
        indexManager.add_and_search(indexDir, "Some real document content", null);
        String result = indexManager.add_and_search(indexDir, null, "nonexistent keyword");
        Assertions.assertEquals("", result);
    }
}