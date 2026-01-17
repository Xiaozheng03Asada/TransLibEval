package com.example;

import org.apache.commons.text.similarity.LevenshteinDistance;
import java.util.ArrayList;
import java.util.List;

public class StringProcessor {
    public String calculate_editops(String str1, String str2) {
        
        class EditOperation {
            String type;
            int sourcePos;
            int targetPos;

            EditOperation(String type, int sourcePos, int targetPos) {
                this.type = type;
                this.sourcePos = sourcePos;
                this.targetPos = targetPos;
            }

            @Override
            public String toString() {
                return String.format("('%s', %d, %d)", type, sourcePos, targetPos);
            }
        }

        
        if (str1 == null || str2 == null) {
            return "Error: Both inputs must be strings";
        }

        
        LevenshteinDistance levenshtein = LevenshteinDistance.getDefaultInstance();
        List<EditOperation> operations = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < str1.length() || j < str2.length()) {
            if (i < str1.length() && j < str2.length()) {
                if (str1.charAt(i) == str2.charAt(j)) {
                    i++;
                    j++;
                    continue;
                }
                if (i + 1 < str1.length() && str1.charAt(i + 1) == str2.charAt(j)) {
                    operations.add(new EditOperation("delete", i, j));
                    i++;
                    continue;
                }
                if (j + 1 < str2.length() && str1.charAt(i) == str2.charAt(j + 1)) {
                    operations.add(new EditOperation("insert", i, j));
                    j++;
                    continue;
                }
                operations.add(new EditOperation("replace", i, j));
                i++;
                j++;
            } else if (i < str1.length()) {
                operations.add(new EditOperation("delete", i, j));
                i++;
            } else {
                operations.add(new EditOperation("insert", i, j));
                j++;
            }
        }

        return operations.toString();
    }
}