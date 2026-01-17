package com.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class WhooshSearcher {

    public String perform_search(String indexDir, String queryText) {
        {
            if (indexDir == null || indexDir.isEmpty()) {
                throw new IllegalArgumentException("Index directory cannot be null or empty.");
            }
            if (queryText == null) {
                queryText = ""; // or throw an exception, depending on desired behavior
            }

            try {
                File indexDirectory = new File(indexDir);
                if (!indexDirectory.exists() || !indexDirectory.isDirectory()) {
                    throw new IllegalArgumentException("Index directory does not exist: " + indexDir);
                }


                Directory directory = FSDirectory.open(Paths.get(indexDir));
                IndexReader reader = null;
                try {
                    reader = DirectoryReader.open(directory);
                } catch (IOException e) {
                    throw new IllegalArgumentException("Index is empty or invalid.");
                }
                IndexSearcher searcher = new IndexSearcher(reader);
                Analyzer analyzer = new StandardAnalyzer();
                QueryParser parser = new QueryParser("content", analyzer);
                Query query = parser.parse(queryText);

                ScoreDoc[] hits = searcher.search(query, 10).scoreDocs; // Limit to 10 hits

                List<String> resultsList = new ArrayList<>();
                for (ScoreDoc hit : hits) {
                    Document document = searcher.doc(hit.doc);
                    resultsList.add(document.get("content"));
                }
                reader.close();

                // Sort the results to ensure consistent order
                Collections.sort(resultsList);

                if (resultsList.isEmpty()) {
                    return "No documents found";
                } else {
                    return String.join(", ", resultsList);
                }
            } catch (Exception e) {
                if (e instanceof IllegalArgumentException) {
                    throw (IllegalArgumentException) e; // Re-throw IllegalArgumentException
                }
                return "No documents found";
            }
        }
    }
}