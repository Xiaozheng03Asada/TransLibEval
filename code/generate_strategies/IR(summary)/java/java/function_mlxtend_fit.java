package com.example;

import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OneHotEncode {

    public String onehot_encode(String transactions) {
        
        String[] items = transactions.replace(",", "").split(" ");

        
        Set<String> uniqueItems = new HashSet<>(Arrays.asList(items));
        uniqueItems.removeIf(String::isEmpty); 

        String[] sortedItems = uniqueItems.toArray(new String[0]);
        Arrays.sort(sortedItems);

        
        return StringUtils.join(sortedItems, ", ");
    }
}