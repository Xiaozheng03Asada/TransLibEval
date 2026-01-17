package com.example;

import com.google.gson.*;
import java.util.Locale;

public class ProductSchema {
    public String deserialize_product_data(String data) {
        try {
            
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(data);

            if (!jsonElement.isJsonObject()) {
                return "Error: invalid input";
            }

            JsonObject jsonObject = jsonElement.getAsJsonObject();

            
            if (!jsonObject.has("name") || !jsonObject.has("price")) {
                return "Error: invalid input";
            }

            
            JsonElement nameElement = jsonObject.get("name");
            if (!nameElement.isJsonPrimitive() || !nameElement.getAsJsonPrimitive().isString()) {
                return "Error: invalid input";
            }
            String name = nameElement.getAsString();

            
            JsonElement priceElement = jsonObject.get("price");
            if (!priceElement.isJsonPrimitive() || !priceElement.getAsJsonPrimitive().isNumber()) {
                return "Error: invalid input";
            }
            double price = priceElement.getAsDouble();

            
            boolean inStock = true;
            if (jsonObject.has("in_stock")) {
                JsonElement inStockElement = jsonObject.get("in_stock");
                if (!inStockElement.isJsonPrimitive() || !inStockElement.getAsJsonPrimitive().isBoolean()) {
                    return "Error: invalid input";
                }
                inStock = inStockElement.getAsBoolean();
            }

            
            return String.format(Locale.US,
                    "{\"name\":\"%s\",\"price\":%.2f,\"in_stock\":%s}",
                    name,
                    price,
                    inStock);
        } catch (Exception e) {
            return "Error: invalid input";
        }
    }
}