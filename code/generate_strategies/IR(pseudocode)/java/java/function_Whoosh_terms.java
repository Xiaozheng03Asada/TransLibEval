package com.example;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class WhooshTerms {

    public String manage_index_and_list_terms(String indexDir, String fieldName, boolean addDocuments) {
        try {
            File indexDirectory = new File(indexDir);

            if (!indexDirectory.exists()) {
                if (addDocuments) {
                    indexDirectory.mkdir();
                } else {
                    throw new IllegalArgumentException("Index directory '" + indexDir + "' does not exist");
                }
            }

            Directory directory = FSDirectory.open(Paths.get(indexDir));
            IndexReader reader = null;
            try {
                reader = DirectoryReader.open(directory);
            } catch (IOException e) {
                
                reader = null;
            }

            if (reader == null) {
                if (!addDocuments) {
                    throw new IllegalArgumentException("No index found in directory '" + indexDir + "'");
                }

                StandardAnalyzer analyzer = new StandardAnalyzer();
                IndexWriterConfig config = new IndexWriterConfig(analyzer);
                IndexWriter iwriter = new IndexWriter(directory, config);

                if (addDocuments) {
                    Document doc1 = new Document();
                    doc1.add(new TextField("content", "Hello world! This is a test document.", Field.Store.YES));
                    iwriter.addDocument(doc1);

                    Document doc2 = new Document();
                    doc2.add(new TextField("content", "Another test document is here.", Field.Store.YES));
                    iwriter.addDocument(doc2);

                    Document doc3 = new Document();
                    doc3.add(new TextField("content", "Whoosh makes text search easy.", Field.Store.YES));
                    iwriter.addDocument(doc3);

                    iwriter.commit();
                }
                iwriter.close();
                reader = DirectoryReader.open(directory);

            }
            IndexSearcher searcher = new IndexSearcher(reader);

            List<String> termsList = new ArrayList<>();
            for (LeafReaderContext leafContext : reader.leaves()) {
                LeafReader leafReader = leafContext.reader();

                Terms terms = leafReader.terms(fieldName);
                if (terms != null) {
                    TermsEnum termsEnum = terms.iterator();
                    while (termsEnum.next() != null) {
                        termsList.add(termsEnum.term().utf8ToString());
                    }
                }
            }


            reader.close();
            directory.close();

            return String.join(", ", termsList);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}