package com.example;

import org.apache.commons.lang3.function.TriFunction;

public class PartialFunctionExample {
    public int apply_partial_function(int base_value, int add_value) {
        
        class Solution {
            TriFunction<Integer, Integer, Integer, Integer> addFunction =
                    (stored, base, add) -> base + add;

            Integer partialApply(Integer baseVal, Integer addVal) {
                return addFunction.apply(null, baseVal, addVal);
            }
        }

        
        if (!(base_value instanceof Integer)) {
            throw new IllegalArgumentException("base_value must be an integer");
        }

        Solution solution = new Solution();
        return solution.partialApply(base_value, add_value);
    }
}