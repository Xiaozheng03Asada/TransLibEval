package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

public class test_Whoosh_index_writer {
    private String indexDir;
    private function_Whoosh_index_writer manager;

    @BeforeEach
    void setUp() throws IOException {
        indexDir = "test_index";
        manager = new function_Whoosh_index_writer();
        initializeIndex(indexDir);
    }

    @AfterEach
    void tearDown() throws IOException {
        Path indexPath = Paths.get(indexDir);
        if (Files.exists(indexPath)) {
            Files.walk(indexPath)
                    .sorted((a, b) -> -a.compareTo(b))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    private void initializeIndex(String indexDir) {
        manager.manage_and_search_documents(indexDir, "Document 1: Test text.", null);
    }

    @Test
    void testAddDocument() {
        String result = manager.manage_and_search_documents(indexDir, "Document 2: Another test text.", "Document");
        assertTrue(result.contains("Document 2: Another test text."));
    }

    @Test
    void testEmptyIndexSearch() {
        String result = manager.manage_and_search_documents(indexDir, null, "Nonexistent");
        assertEquals("No documents found", result);
    }

    @Test
    void testEmptyIndex() {
        String result = manager.manage_and_search_documents(indexDir, "Document 2: Another test text.", "Document");
        assertTrue(result.contains("Document 2: Another test text."));
    }

    @Test
    void testMultipleUpdatesOnSameDocument() {
        manager.manage_and_search_documents(indexDir, "Updated Document: Final version.", null);
        String result = manager.manage_and_search_documents(indexDir, null, "Updated");
        assertTrue(result.contains("Updated Document: Final version."));
    }

    @Test
    void testSearchSpecificDocument() {
        String result = manager.manage_and_search_documents(indexDir, null, "Test");
        assertTrue(result.contains("Document 1: Test text."));
    }
}