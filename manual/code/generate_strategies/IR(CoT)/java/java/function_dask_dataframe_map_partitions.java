package com.example;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class ComputePartitionMeansFunction {

    
    public String compute_partition_means(String data_str, String column) {
        try {
            
            StringReader sr = new StringReader(data_str);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(sr);
            Map<String, Integer> headerMap = ((org.apache.commons.csv.CSVParser) records).getHeaderMap();

            
            if (!headerMap.containsKey(column)) {
                return "Error";
            }

            
            List<Double> values = new ArrayList<>();
            for (CSVRecord record : records) {
                String field = record.get(column);
                try {
                    
                    double num = Double.parseDouble(field);
                    values.add(num);
                } catch (Exception e) {
                    return "Error";
                }
            }

            int n = values.size();
            if (n == 0) {
                return "Error";
            }

            
            
            int mid;
            if(n >= 2) {
                mid = n / 2;
            } else {
                mid = n;
            }

            List<Double> partitionMeans = new ArrayList<>();
            
            if (mid > 0) {
                double sum = 0;
                for (int i = 0; i < mid; i++) {
                    sum += values.get(i);
                }
                double mean = sum / mid;
                partitionMeans.add(mean);
            }

            
            if (mid < n) {
                double sum = 0;
                int count = 0;
                for (int i = mid; i < n; i++) {
                    sum += values.get(i);
                    count++;
                }
                if(count > 0){
                    double mean = sum / count;
                    partitionMeans.add(mean);
                }
            }

            
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < partitionMeans.size(); i++) {
                
                sb.append(String.format("%.1f", partitionMeans.get(i)));
                if (i != partitionMeans.size() - 1) {
                    sb.append(",");
                }
            }
            return sb.toString();

        } catch (Exception e) {
            return "Error";
        }
    }
}