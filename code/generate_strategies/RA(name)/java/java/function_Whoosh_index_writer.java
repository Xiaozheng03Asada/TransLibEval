package com.example;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WhooshIndexManager {
    public String manage_and_search_documents(String indexDir, String documentText, String searchQuery) {
        class WhooshIndexManager {
            private final StandardAnalyzer analyzer = new StandardAnalyzer();

            public String processDocuments(String indexDir, String documentText, String searchQuery) throws Exception {
                Path indexPath = Paths.get(indexDir);
                if (!Files.exists(indexPath)) {
                    Files.createDirectories(indexPath);
                }

                FSDirectory directory = FSDirectory.open(indexPath);
                IndexWriterConfig config = new IndexWriterConfig(analyzer);

                try (IndexWriter writer = new IndexWriter(directory, config)) {
                    if (documentText != null) {
                        Document doc = new Document();
                        doc.add(new TextField("content", documentText, Field.Store.YES));
                        writer.addDocument(doc);
                        writer.commit();
                    }
                }

                if (searchQuery != null) {
                    try (DirectoryReader reader = DirectoryReader.open(directory)) {
                        IndexSearcher searcher = new IndexSearcher(reader);
                        QueryParser parser = new QueryParser("content", analyzer);
                        Query query = parser.parse(searchQuery);
                        ScoreDoc[] hits = searcher.search(query, 10).scoreDocs;

                        List<String> results = new ArrayList<>();
                        for (ScoreDoc hit : hits) {
                            Document doc = searcher.doc(hit.doc);
                            results.add(doc.get("content"));
                        }

                        return results.isEmpty() ? "No documents found" : String.join(" ", results);
                    }
                }

                return "No documents found";
            }
        }

        try {
            return new WhooshIndexManager().processDocuments(indexDir, documentText, searchQuery);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process documents: " + e.getMessage());
        }
    }
}