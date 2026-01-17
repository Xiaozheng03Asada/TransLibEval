package com.example;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;

public class JSONValidator {

    
    public String validate_json(String data_str, String schema_str) {
        try {
            
            JSONObject data = new JSONObject(data_str);
            
            JSONObject schemaJson = new JSONObject(schema_str);
            
            Schema schema = SchemaLoader.load(schemaJson);
            
            schema.validate(data);
            
            return "JSON is valid";
        } catch (Exception e) {
            
            return "JSON is invalid";
        }
    }
}