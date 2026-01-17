package com.example;

import java.io.StringReader;
import java.io.Reader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class DataHeatmap {

    public String generate_heatmap(String data) {
        try {
            
            if (data == null) {
                return "An error occurred: Input data is None";
            }
            if (data.trim().isEmpty()) {
                return "No data in the file";
            }

            
            Reader reader = new StringReader(data);
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> csvData = csvReader.readAll();
            csvReader.close();

            
            if (csvData == null || csvData.size() <= 1) {
                return "No data in the file";
            }

            
            

            
            return "Heatmap generated successfully";
        } catch (CsvException e) {
            return "Invalid file format";
        } catch (IndexOutOfBoundsException e) {  
            return "Invalid file format";
        } catch (Exception ex) {
            return "An error occurred: " + ex.getMessage();
        }
    }
}