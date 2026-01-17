package com.example;

import java.util.*;
import org.apache.commons.lang3.builder.CompareToBuilder; 

public class NumberDictManager {

    
    public String manage_number_dict(String operations_str) {
        
        String[] operations = operations_str.split(";");
        Map<Integer, Integer> number_dict = new HashMap<>();

        
        Comparator<Map.Entry<Integer, Integer>> comparator = new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> entry1, Map.Entry<Integer, Integer> entry2) {
                return new CompareToBuilder().append(entry1.getKey(), entry2.getKey()).toComparison();
            }
        };

        
        for (String op : operations) {
            
            if (op.trim().isEmpty()) {
                continue;
            }
            String[] op_parts = op.split(",");
            switch (op_parts[0]) {
                case "add":
                    int keyAdd = Integer.parseInt(op_parts[1]);
                    int valueAdd = Integer.parseInt(op_parts[2]);
                    number_dict.put(keyAdd, valueAdd);
                    break;
                case "remove":
                    int keyRemove = Integer.parseInt(op_parts[1]);
                    
                    number_dict.remove(keyRemove);
                    break;
                case "get":
                    int keyGet = Integer.parseInt(op_parts[1]);
                    Integer value = number_dict.get(keyGet);
                    
                    return value == null ? "default" : String.valueOf(value);
                case "sort":
                    
                    List<Map.Entry<Integer, Integer>> sortedItems = new ArrayList<>(number_dict.entrySet());
                    Collections.sort(sortedItems, comparator);
                    StringBuilder sb = new StringBuilder();
                    
                    for (Map.Entry<Integer, Integer> entry : sortedItems) {
                        if (sb.length() != 0) {
                            sb.append(" ");
                        }
                        sb.append(entry.getKey()).append(":").append(entry.getValue());
                    }
                    return sb.toString();
                case "sum":
                    int sum = 0;
                    for (int v : number_dict.values()) {
                        sum += v;
                    }
                    return String.valueOf(sum);
                default:
                    
                    break;
            }
        }
        return "";
    }
}