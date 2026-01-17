package com.example;

import com.google.gson.*;
import java.util.Locale;

public class ProductSchema {
    public String deserialize_product_data(String data) {
        try {
            // 配置Gson
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(data);

            if (!jsonElement.isJsonObject()) {
                return "Error: invalid input";
            }

            JsonObject jsonObject = jsonElement.getAsJsonObject();

            // 验证必需字段
            if (!jsonObject.has("name") || !jsonObject.has("price")) {
                return "Error: invalid input";
            }

            // 获取name
            JsonElement nameElement = jsonObject.get("name");
            if (!nameElement.isJsonPrimitive() || !nameElement.getAsJsonPrimitive().isString()) {
                return "Error: invalid input";
            }
            String name = nameElement.getAsString();

            // 获取price
            JsonElement priceElement = jsonObject.get("price");
            if (!priceElement.isJsonPrimitive() || !priceElement.getAsJsonPrimitive().isNumber()) {
                return "Error: invalid input";
            }
            double price = priceElement.getAsDouble();

            // 处理in_stock的默认值
            boolean inStock = true;
            if (jsonObject.has("in_stock")) {
                JsonElement inStockElement = jsonObject.get("in_stock");
                if (!inStockElement.isJsonPrimitive() || !inStockElement.getAsJsonPrimitive().isBoolean()) {
                    return "Error: invalid input";
                }
                inStock = inStockElement.getAsBoolean();
            }

            // 构建输出，使用String.format确保数字格式正确
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