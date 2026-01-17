package com.example;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONException;

public class JSONValidator {
    public String validate_json(String data_str, String schema_str) {
        class JSONValidator {
            public String validateJson(String dataStr, String schemaStr) {
                try {
                    JSONObject dataJson = new JSONObject(dataStr);
                    JSONObject schemaJson = new JSONObject(schemaStr);
                    Schema schema = SchemaLoader.load(schemaJson);
                    schema.validate(dataJson);
                    return "True";
                } catch (ValidationException e) {
                    return "False";
                } catch (JSONException e) {
                    return "Validation failed: Invalid JSON format";
                }
            }
        }
        return new JSONValidator().validateJson(data_str, schema_str);
    }
}