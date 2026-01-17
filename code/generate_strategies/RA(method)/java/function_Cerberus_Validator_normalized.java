package com.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DataNormalizer {
    public String normalize_data(String dataStr) {
        if (!(dataStr instanceof String)) {
            return "Error: Input must be a string";
        }

        try {
            Gson gson = new Gson();
            JsonObject data = gson.fromJson(dataStr, JsonObject.class);

            // 验证和设置name字段
            if (!data.has("name") || data.get("name").isJsonNull()) {
                data.addProperty("name", "Unknown");
            } else {
                JsonElement nameElement = data.get("name");
                if (!nameElement.isJsonPrimitive() || !nameElement.getAsJsonPrimitive().isString()) {
                    return "Error: Name must be a string";
                }
            }

            // 验证和设置age字段
            if (!data.has("age") || data.get("age").isJsonNull()) {
                data.addProperty("age", 18);
            } else {
                JsonElement ageElement = data.get("age");
                if (!ageElement.isJsonPrimitive() || !ageElement.getAsJsonPrimitive().isNumber()) {
                    return "Error: Age must be an integer";
                }
            }

            return data.toString();
        } catch (JsonParseException e) {
            return "Error: Invalid string format for data";
        }
    }
}