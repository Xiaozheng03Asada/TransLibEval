package com.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;

public class PatsyProcessor {
    public String process_formula(String data, String formula) {
        // 所有功能均写在此唯一方法中
        try {
            Gson gson = new Gson();
            // 解析JSON字符串到JsonObject
            JsonObject jsonObj = JsonParser.parseString(data).getAsJsonObject();
            // 检查必须存在"y"列且必须为数组
            if (!jsonObj.has("y") || !jsonObj.get("y").isJsonArray()) {
                return "Error: invalid input";
            }
            JsonArray yArray = jsonObj.get("y").getAsJsonArray();
            int nRows = yArray.size();

            // 分割公式，左侧必须为"y"
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
            // 根据不同的公式模式进行判断并验证必需的列是否存在
            if (rhs.equals("x1 + x2")) {
                if (!jsonObj.has("x1") || !jsonObj.get("x1").isJsonArray() ||
                        !jsonObj.has("x2") || !jsonObj.get("x2").isJsonArray()) {
                    return "Error: invalid input";
                }
                // 截距项 + x1 + x2
                xCols = 3;
            } else if (rhs.equals("x1 * x2")) {
                if (!jsonObj.has("x1") || !jsonObj.get("x1").isJsonArray() ||
                        !jsonObj.has("x2") || !jsonObj.get("x2").isJsonArray()) {
                    return "Error: invalid input";
                }
                // 截距项 + x1 + x2 + 交互项
                xCols = 4;
            } else if (rhs.equals("x1 + I(x1 ** 2) + I(x1 ** 3)")) {
                if (!jsonObj.has("x1") || !jsonObj.get("x1").isJsonArray()) {
                    return "Error: invalid input";
                }
                // 截距项 + x1 + (x1)^2 + (x1)^3
                xCols = 4;
            } else {
                // 公式不匹配的情况返回错误
                return "Error: invalid input";
            }

            // 构造返回结果JSON
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