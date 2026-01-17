package com.example;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;

public class MissingnoBar {

    
    
    public String visualize_missing_data(String csv_data) {
        try {
            if (csv_data == null || csv_data.trim().isEmpty()) {
                throw new IllegalArgumentException("Empty CSV data");
            }
            
            String[] lines = csv_data.split("\\r?\\n");
            if (lines.length < 2) {
                throw new IllegalArgumentException("Empty CSV data");
            }
            String[] headerArray = lines[0].split(",", -1);
            int headerCount = headerArray.length;
            for (int i = 1; i < lines.length; i++) {
                
                if (lines[i].trim().isEmpty()) continue;
                String[] fields = lines[i].split(",", -1);
                if (fields.length != headerCount) {
                    throw new IllegalArgumentException("Invalid CSV format: row column count mismatch");
                }
            }

            
            StringReader reader = new StringReader(csv_data);
            CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            
            if (parser.getRecords().isEmpty()) {
                throw new IllegalArgumentException("Empty CSV data");
            }

            
            List<String> headers = new ArrayList<>(parser.getHeaderMap().keySet());
            Map<String, Integer> missingCounts = new LinkedHashMap<>();
            for (String header : headers) {
                missingCounts.put(header, 0);
            }
            for (CSVRecord record : parser.getRecords()) {
                for (String header : headers) {
                    String val = record.get(header);
                    if (val == null || val.trim().isEmpty()) {
                        missingCounts.put(header, missingCounts.get(header) + 1);
                    }
                }
            }

            
            CategoryChart chart = new CategoryChartBuilder()
                    .width(800)
                    .height(600)
                    .title("Missing Data Bar Chart")
                    .xAxisTitle("Columns")
                    .yAxisTitle("Missing Count")
                    .build();
            List<String> columnNames = new ArrayList<>(missingCounts.keySet());
            List<Integer> counts = new ArrayList<>(missingCounts.values());
            chart.addSeries("Missing Data", columnNames, counts);

            
            return "Missing data visualization generated.";
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid CSV format: " + e.getMessage());
        }
    }
}