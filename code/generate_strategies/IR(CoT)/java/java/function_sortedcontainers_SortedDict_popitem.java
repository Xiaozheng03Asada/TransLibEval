package com.example;

import com.google.common.collect.Maps; 
import java.util.Map;
import java.util.SortedMap;
import org.apache.commons.lang3.tuple.ImmutablePair; 
import org.apache.commons.lang3.tuple.Pair;

public class SortedDictHandler {
    public String modify_sorted_dict(int index) {
        
        SortedMap<Integer, String> sortedDict = Maps.newTreeMap();
        sortedDict.put(3, "three");  
        sortedDict.put(1, "one");    
        sortedDict.put(5, "five");   

        
        if (index < 0) {
            index = sortedDict.size() + index;
        }
        
        if (index < 0 || index >= sortedDict.size()) {
            return "error: Invalid index";
        }

        
        int cur = 0;
        Integer keyToRemove = null;
        String valueToRemove = null;
        for (Map.Entry<Integer, String> entry : sortedDict.entrySet()) {
            if (cur == index) {
                keyToRemove = entry.getKey();
                valueToRemove = entry.getValue();
                break;
            }
            cur++;
        }

        
        sortedDict.remove(keyToRemove);
        
        Pair<Integer, String> removedItem = new ImmutablePair<>(keyToRemove, valueToRemove);

        
        StringBuilder dictString = new StringBuilder();
        dictString.append("{");
        int count = 0;
        for (Map.Entry<Integer, String> entry : sortedDict.entrySet()) {
            if (count > 0) {
                dictString.append(", ");
            }
            dictString.append(entry.getKey()).append(": '").append(entry.getValue()).append("'");
            count++;
        }
        dictString.append("}");

        
        String removedItemString = "(" + removedItem.getLeft() + ", '" + removedItem.getRight() + "')";

        return dictString.toString() + " " + removedItemString;
    }
}