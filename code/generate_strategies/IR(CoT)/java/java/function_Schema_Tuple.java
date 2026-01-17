package com.example;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;

public class ValidateTuple {
    public String validate(String input_str) {
        try {
            String[] parts = input_str.split(",");
            if (parts.length != 2) {
                return "Invalid input: Must be a pair of values";
            }

            
            JSONObject jsonSchema = new JSONObject()
                    .put("type", "object")
                    .put("properties", new JSONObject()
                            .put("first", new JSONObject()
                                    .put("type", "integer"))
                            .put("second", new JSONObject()
                                    .put("type", "string")))
                    .put("required", new JSONArray()
                            .put("first")
                            .put("second"));

            
            JSONObject jsonData = new JSONObject();
            try {
                jsonData.put("first", Integer.parseInt(parts[0].trim()));
            } catch (NumberFormatException e) {
                return "Invalid input: First value must be an integer";
            }
            jsonData.put("second", parts[1].trim());

            
            Schema schema = SchemaLoader.load(jsonSchema);
            schema.validate(jsonData);

            
            return String.format("Valid tuple: (%s, %s)",
                    jsonData.get("first"),
                    jsonData.get("second"));

        } catch (ValidationException e) {
            return "Invalid input: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}