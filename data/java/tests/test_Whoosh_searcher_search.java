package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class test_Whoosh_searcher_search {

    private String indexDir = "test_search_index";
    private function_Whoosh_searcher_search searcher = new function_Whoosh_searcher_search();

    @BeforeEach
    void setUp() throws IOException {
        createIndex(indexDir);
    }

    @AfterEach
    void tearDown() throws IOException {
        deleteDirectory(Paths.get(indexDir));
    }


    void createIndex(String indexDir) throws IOException {
        Path indexPath = Paths.get(indexDir);
        if (!Files.exists(indexPath)) {
            Files.createDirectories(indexPath);
        }

        org.apache.lucene.store.Directory directory = org.apache.lucene.store.FSDirectory.open(indexPath);
        org.apache.lucene.analysis.Analyzer analyzer = new org.apache.lucene.analysis.standard.StandardAnalyzer();
        org.apache.lucene.index.IndexWriterConfig config = new org.apache.lucene.index.IndexWriterConfig(analyzer);
        org.apache.lucene.index.IndexWriter writer = new org.apache.lucene.index.IndexWriter(directory, config);

        org.apache.lucene.document.Document doc1 = new org.apache.lucene.document.Document();
        doc1.add(new org.apache.lucene.document.TextField("content", "This is a test document.", org.apache.lucene.document.Field.Store.YES));
        writer.addDocument(doc1);

        org.apache.lucene.document.Document doc2 = new org.apache.lucene.document.Document();
        doc2.add(new org.apache.lucene.document.TextField("content", "Another test document.", org.apache.lucene.document.Field.Store.YES));
        writer.addDocument(doc2);

        org.apache.lucene.document.Document doc3 = new org.apache.lucene.document.Document();
        doc3.add(new org.apache.lucene.document.TextField("content", "Document with a different content.", org.apache.lucene.document.Field.Store.YES));
        writer.addDocument(doc3);

        writer.close();
        directory.close();
    }

    void deleteDirectory(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    @Test
    void testSearchDocument() {
        String results = searcher.perform_search(indexDir, "test");
        assertEquals("Another test document., This is a test document.", results);
    }

    @Test
    void testSearchNoResults() {
        String results = searcher.perform_search(indexDir, "nonexistent");
        assertEquals("No documents found", results);
    }

    @Test
    void testSearchMultipleMatches() {
        String results = searcher.perform_search(indexDir, "document");
        assertEquals("Another test document., Document with a different content., This is a test document.", results);
    }

    @Test
    void testEmptyQuery() {
        String results = searcher.perform_search(indexDir, "");
        assertEquals("No documents found", results);
    }

    @Test
    void testInvalidIndexDirectory() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            searcher.perform_search("invalid_dir", "test");
        });

        String expectedMessage = "Index directory does not exist: invalid_dir";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}