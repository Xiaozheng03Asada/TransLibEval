package com.example;

import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OneHotEncode {

    public String onehot_encode(String transactions) {
        // Replace commas and split into individual items
        String[] items = transactions.replace(",", "").split(" ");

        // Remove duplicates and sort alphabetically
        Set<String> uniqueItems = new HashSet<>(Arrays.asList(items));
        uniqueItems.removeIf(String::isEmpty); // Remove empty strings

        String[] sortedItems = uniqueItems.toArray(new String[0]);
        Arrays.sort(sortedItems);

        // Join the sorted unique items with a comma and space
        return StringUtils.join(sortedItems, ", ");
    }
}