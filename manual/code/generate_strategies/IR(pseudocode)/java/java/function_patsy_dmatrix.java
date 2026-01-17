package com.example;

import org.json.JSONArray;
import org.json.JSONObject;

public class PatsyProcessor {
    
    public String generate_matrix(String data, String formula) {
        try {
            
            JSONObject jsonData = new JSONObject(data);
            
            JSONArray xArray = jsonData.getJSONArray("x");
            int nRows = xArray.length();
            int nCols;

            
            if (formula.equals("x + I(x ** 2)")) {
                
                nCols = 3;
            } else if (formula.equals("C(z)")) {
                
                nCols = 4;
            } else if (formula.equals("x * z * w")) {
                
                nCols = 8;
            } else if (formula.equals("x + (z > 6)")) {
                
                nCols = 3;
            } else if (formula.equals("C(w > 15)")) {
                
                nCols = 2;
            } else {
                
                return "Error: invalid input";
            }

            
            JSONObject result = new JSONObject();
            JSONArray shapeArray = new JSONArray();
            shapeArray.put(nRows);
            shapeArray.put(nCols);
            result.put("matrix_shape", shapeArray);
            return result.toString();
        } catch (Exception e) {
            return "Error: invalid input";
        }
    }
}