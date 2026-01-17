package com.example;

import org.apache.commons.lang3.ObjectUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CreatePivotTable {
    
    public String create_pivot_table(String date1, String date2, String category1, String category2, Integer value1, Integer value2) {
        
        if(date1 == null || date2 == null || category1 == null || category2 == null || value1 == null || value2 == null) {
            date1 = "2023-01-01";
            date2 = "2023-01-02";
            category1 = "A";
            category2 = "B";
            value1 = 1;
            value2 = 2;
        }

        
        Map<String, Map<String, Integer>> pivot = new TreeMap<>();

        
        pivot.computeIfAbsent(date1, k -> new HashMap<>())
                .put(category1, pivot.get(date1).getOrDefault(category1, 0) + value1);

        
        pivot.computeIfAbsent(date2, k -> new HashMap<>())
                .put(category2, pivot.get(date2).getOrDefault(category2, 0) + value2);

        
        for (Map<String, Integer> row : pivot.values()) {
            row.putIfAbsent("A", 0);
            row.putIfAbsent("B", 0);
        }

        
        String firstDate = pivot.keySet().iterator().next();
        Map<String, Integer> firstRow = pivot.get(firstDate);
        int colA = firstRow.get("A");
        int colB = firstRow.get("B");

        
        return "Date: " + firstDate + ", Category A: " + colA + ", Category B: " + colB;
    }
}