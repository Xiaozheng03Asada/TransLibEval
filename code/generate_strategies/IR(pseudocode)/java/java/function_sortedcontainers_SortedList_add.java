package com.example;

import com.google.common.collect.TreeMultiset;
import java.util.List;
import java.util.ArrayList;

public class SortedListHandler {

    
    
    public String modify_sorted_list(int index) {
        
        TreeMultiset<Integer> sortedList = TreeMultiset.create();
        
        sortedList.add(5);
        sortedList.add(3);
        sortedList.add(8);
        sortedList.add(1);
        try {
            
            
            List<Integer> list = new ArrayList<>(sortedList);
            if(index < 0 || index >= list.size()){
                throw new IndexOutOfBoundsException();
            }
            
            int removedItem = list.get(index);
            sortedList.remove(removedItem);
            
            List<Integer> remaining = new ArrayList<>(sortedList);
            
            return "Removed item: " + removedItem + ", Remaining list: " + remaining.toString();
        } catch(IndexOutOfBoundsException e) {
            return "Index out of range";
        }
    }
}