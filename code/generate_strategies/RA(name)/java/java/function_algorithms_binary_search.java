// 文件路径：src/main/java/com/example/function_algorithms_binary_search.java
package com.example;

import org.apache.commons.lang3.ArrayUtils;

public class BinarySearchFunction {
    public int binary_search_index(String sortedString, String target) {
        if (target == null || target.length() != 1) return -1;
        Character[] arr = sortedString.chars()
                .mapToObj(c -> (char)c)
                .toArray(Character[]::new);
        return ArrayUtils.indexOf(arr, target.charAt(0)); // 使用第三方库的查找方法[10](@ref)
    }
}