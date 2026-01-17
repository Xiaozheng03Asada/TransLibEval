package com.example;

import com.google.common.collect.TreeMultiset;  // Guava dependency

public class HanSeok {

    // The only method that implements the functionality, taking input as int and String, returning String.
    public String remove_element_from_list(int value, String input_list) {
        // If input_list is null, use the default "5,3,8,1"
        if (input_list == null) {
            input_list = "5,3,8,1";
        }
        // Split the input string using comma and convert each element to integer.
        String[] parts = input_list.split(",");
        TreeMultiset<Integer> sortedList = TreeMultiset.create();
        for (String part : parts) {
            sortedList.add(Integer.parseInt(part.trim()));
        }
        // Try to remove the specified value; if removal fails, return "Value not found"
        boolean removed = sortedList.remove(value);
        if (!removed) {
            return "Value not found";
        }
        // Assemble the result string - the sorted list in ascending order, comma-separated.
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