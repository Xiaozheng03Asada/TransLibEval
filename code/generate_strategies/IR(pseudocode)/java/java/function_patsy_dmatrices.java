package com.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;

public class PatsyProcessor {
    public String process_formula(String data, String formula) {
        
        try {
            Gson gson = new Gson();
            
            JsonObject jsonObj = JsonParser.parseString(data).getAsJsonObject();
            
            if (!jsonObj.has("y") || !jsonObj.get("y").isJsonArray()) {
                return "Error: invalid input";
            }
            JsonArray yArray = jsonObj.get("y").getAsJsonArray();
            int nRows = yArray.size();

            
            String[] parts = formula.split("~");
            if (parts.length != 2) {
                return "Error: invalid input";
            }
            String lhs = parts[0].trim();
            if (!lhs.equals("y")) {
                return "Error: invalid input";
            }
            String rhs = parts[1].trim();

            int xCols = 0;
            
            if (rhs.equals("x1 + x2")) {
                if (!jsonObj.has("x1") || !jsonObj.get("x1").isJsonArray() ||
                        !jsonObj.has("x2") || !jsonObj.get("x2").isJsonArray()) {
                    return "Error: invalid input";
                }
                
                xCols = 3;
            } else if (rhs.equals("x1 * x2")) {
                if (!jsonObj.has("x1") || !jsonObj.get("x1").isJsonArray() ||
                        !jsonObj.has("x2") || !jsonObj.get("x2").isJsonArray()) {
                    return "Error: invalid input";
                }
                
                xCols = 4;
            } else if (rhs.equals("x1 + I(x1 ** 2) + I(x1 ** 3)")) {
                if (!jsonObj.has("x1") || !jsonObj.get("x1").isJsonArray()) {
                    return "Error: invalid input";
                }
                
                xCols = 4;
            } else {
                
                return "Error: invalid input";
            }

            
            JsonObject result = new JsonObject();
            JsonArray yShape = new JsonArray();
            yShape.add(nRows);
            yShape.add(1);
            JsonArray xShape = new JsonArray();
            xShape.add(nRows);
            xShape.add(xCols);
            result.add("y_shape", yShape);
            result.add("X_shape", xShape);
            return gson.toJson(result);
        } catch (Exception e) {
            return "Error: invalid input";
        }
    }
}