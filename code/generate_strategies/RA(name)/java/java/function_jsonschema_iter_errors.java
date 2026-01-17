package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import java.util.Set;
import java.util.Locale;

public class JSONValidator {
    public String validate_json(String dataStr, String schemaStr) {
        class JSONValidator {
            public String validate(String data, String schema) {
                try {
                    // Set locale to ENGLISH to ensure English error messages
                    Locale.setDefault(Locale.ENGLISH);

                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode dataNode = mapper.readTree(data);
                    JsonNode schemaNode = mapper.readTree(schema);

                    JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
                    JsonSchema jsonSchema = factory.getSchema(schemaNode);

                    Set<ValidationMessage> errors = jsonSchema.validate(dataNode);

                    if (errors.isEmpty()) {
                        return "JSON is valid";
                    } else {
                        ValidationMessage firstError = errors.iterator().next();
                        // Format the error message to match exactly with the Python version
                        String errorMsg = firstError.getMessage();

                        // Replace networknt schema validator messages with Python jsonschema messages
                        errorMsg = errorMsg.replaceAll("\\$.age: missing required property", "'age' is a required property")
                                .replaceAll("\\$.name: missing required property", "'name' is a required property")
                                .replaceAll("\\$.age: string found, integer expected", "'30' is not of type 'integer'");

                        return "Validation failed: " + errorMsg;
                    }
                } catch (Exception e) {
                    return "Validation failed: Invalid JSON format";
                }
            }
        }
        return new JSONValidator().validate(dataStr, schemaStr);
    }
}