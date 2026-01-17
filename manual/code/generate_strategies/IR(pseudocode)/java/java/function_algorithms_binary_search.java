
package com.example;

import org.apache.commons.lang3.ArrayUtils;

public class BinarySearchFunction {
    public int binary_search_index(String sortedString, String target) {
        if (target == null || target.length() != 1) return -1;
        Character[] arr = sortedString.chars()
                .mapToObj(c -> (char)c)
                .toArray(Character[]::new);
        return ArrayUtils.indexOf(arr, target.charAt(0)); 
    }
}