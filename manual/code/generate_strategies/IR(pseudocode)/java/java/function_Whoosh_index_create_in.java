package com.example;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IndexManager {
    public String add_and_search(String indexDir, String documentText, String queryText) {
        try {
            
            File dir = new File(indexDir);
            if (!dir.exists()) {
                dir.mkdir();
            }

            Path path = Paths.get(indexDir);
            Directory directory = FSDirectory.open(path);
            StandardAnalyzer analyzer = new StandardAnalyzer();

            
            if (documentText != null) {
                IndexWriterConfig config = new IndexWriterConfig(analyzer);
                IndexWriter writer = new IndexWriter(directory, config);

                Document doc = new Document();
                doc.add(new TextField("content", documentText, Field.Store.YES));
                writer.addDocument(doc);
                writer.close();
                return "Document added successfully";
            }

            
            if (queryText != null) {
                DirectoryReader reader = DirectoryReader.open(directory);
                IndexSearcher searcher = new IndexSearcher(reader);

                QueryParser parser = new QueryParser("content", analyzer);
                Query query = parser.parse(queryText);

                TopDocs results = searcher.search(query, 1000);
                List<String> hits = new ArrayList<>();

                for (ScoreDoc scoreDoc : results.scoreDocs) {
                    Document doc = searcher.doc(scoreDoc.doc);
                    hits.add(doc.get("content"));
                }

                reader.close();
                return String.join(" ", hits);
            }

            directory.close();
            return "No action performed";

        } catch (IOException | ParseException e) {
            return "Error: " + e.getMessage();
        }
    }
}