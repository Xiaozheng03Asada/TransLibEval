package com.example;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.List;

public class IterableProcessor {
    public String get_first_n_elements(String iterable, int n) {
        try {
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(iterable, JsonArray.class);
            if (n < 0) {
                return "Error: invalid input";
            }

            
            List<Object> list = new ArrayList<>();
            for (JsonElement element : jsonArray) {
                if (element.isJsonPrimitive()) {
                    if (element.getAsJsonPrimitive().isNumber()) {
                        
                        double num = element.getAsDouble();
                        if (num == Math.floor(num)) {
                            list.add(element.getAsInt());
                        } else {
                            list.add(num);
                        }
                    } else {
                        list.add(element.getAsString());
                    }
                }
            }

            List<?> result = list.size() > n ? list.subList(0, n) : list;
            return result.toString();
        } catch (Exception e) {
            return "Error: invalid input";
        }
    }
}