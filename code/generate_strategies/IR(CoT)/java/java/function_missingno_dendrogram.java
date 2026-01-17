package com.example;

import com.opencsv.CSVReader;
import java.io.StringReader;
import java.util.List;

public class MissingnoDendrogram {
    
    public String create_dendrogram(String data_str) {
        try {
            if (data_str == null || data_str.trim().isEmpty()) {
                throw new Exception("Empty input");
            }
            CSVReader reader = new CSVReader(new StringReader(data_str));
            List<String[]> rows = reader.readAll();
            
            if (rows.size() < 2) {
                throw new Exception("Not enough rows");
            }
            int headerLength = rows.get(0).length;
            
            if (headerLength < 1) {
                throw new Exception("Invalid header");
            }
            
            for (int i = 1; i < rows.size(); i++) {
                if (rows.get(i).length != headerLength) {
                    throw new Exception("Row length mismatch");
                }
            }
            
            return "Dendrogram created successfully";
        } catch (Exception e) {
            return "failed";
        }
    }
}