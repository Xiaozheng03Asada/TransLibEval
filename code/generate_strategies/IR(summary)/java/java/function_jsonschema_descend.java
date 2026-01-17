package com.example;

import com.google.gson.*;
import java.util.Map;

public class JSONValidator {
    
    public String validate_json(String data_str, String schema_str) {
        try {
            
            JsonParser parser = new JsonParser();
            JsonElement dataElem = parser.parse(data_str);
            JsonElement schemaElem = parser.parse(schema_str);

            
            if (!dataElem.isJsonObject()) {
                return "Validation failed";
            }
            JsonObject dataObj = dataElem.getAsJsonObject();
            JsonObject schemaObj = schemaElem.getAsJsonObject();

            
            JsonObject properties = new JsonObject();
            if (schemaObj.has("properties") && schemaObj.get("properties").isJsonObject()) {
                properties = schemaObj.get("properties").getAsJsonObject();
            }

            
            for (Map.Entry<String, JsonElement> entry : dataObj.entrySet()) {
                String key = entry.getKey();
                JsonElement value = entry.getValue();
                if (properties.has(key)) {
                    JsonObject fieldSchema = properties.get(key).getAsJsonObject();
                    
                    if (fieldSchema.has("type")) {
                        String expectedType = fieldSchema.get("type").getAsString();
                        boolean typeMatches = false;
                        if ("string".equals(expectedType)) {
                            typeMatches = value.isJsonPrimitive() && value.getAsJsonPrimitive().isString();
                        } else if ("integer".equals(expectedType)) {
                            if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
                                double d = value.getAsDouble();
                                typeMatches = (d == Math.floor(d));
                            }
                        } else if ("boolean".equals(expectedType)) {
                            typeMatches = value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean();
                        } else if ("object".equals(expectedType)) {
                            typeMatches = value.isJsonObject();
                        } else if ("array".equals(expectedType)) {
                            typeMatches = value.isJsonArray();
                        } else if ("number".equals(expectedType)) {
                            typeMatches = value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber();
                        }
                        if (!typeMatches) {
                            return "Validation failed";
                        }
                    }
                    
                    if (value.isJsonObject()) {
                        String nestedValidation = this.validate_json(value.toString(), fieldSchema.toString());
                        if (!"JSON is valid".equals(nestedValidation)) {
                            return "Validation failed";
                        }
                    }
                } else {
                    
                    return "Validation failed";
                }
            }

            
            if (schemaObj.has("required") && schemaObj.get("required").isJsonArray()) {
                for (JsonElement reqElem : schemaObj.get("required").getAsJsonArray()) {
                    String reqField = reqElem.getAsString();
                    if (!dataObj.has(reqField)) {
                        return "Validation failed";
                    }
                }
            }
            
            return "JSON is valid";
        } catch (Exception e) {
            return "Validation failed";
        }
    }
}