package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils; 

public class HanSeok {
    
    public int find_insert_position(int value, String sorted_list) {
        
        if (sorted_list == null) {
            sorted_list = "1,3,5,8";
        }
        
        String[] parts = sorted_list.split(",");
        
        List<Integer> list = new ArrayList<>();
        for (String part : parts) {
            
            list.add(NumberUtils.toInt(part.trim()));
        }
        
        Collections.sort(list);
        
        int index = Collections.binarySearch(list, value);
        if (index < 0) {
            
            index = -index - 1;
        } else {
            
            while (index > 0 && list.get(index - 1) == value) {
                index--;
            }
        }
        return index;
    }
}