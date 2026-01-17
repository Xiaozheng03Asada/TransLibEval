package com.example;

import com.google.common.collect.TreeMultiset;  

public class HanSeok {

    
    public String remove_element_from_list(int value, String input_list) {
        
        if (input_list == null) {
            input_list = "5,3,8,1";
        }
        
        String[] parts = input_list.split(",");
        TreeMultiset<Integer> sortedList = TreeMultiset.create();
        for (String part : parts) {
            sortedList.add(Integer.parseInt(part.trim()));
        }
        
        boolean removed = sortedList.remove(value);
        if (!removed) {
            return "Value not found";
        }
        
        StringBuilder sb = new StringBuilder();
        for (Integer num : sortedList) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(num);
        }
        return sb.toString();
    }
}