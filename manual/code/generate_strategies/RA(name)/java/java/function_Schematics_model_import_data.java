package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class Product {
    // 唯一方法：import_and_validate，将所有逻辑写在该方法中
    public String import_and_validate(String data) {
        try {
            // 使用 Jackson 将输入的 JSON 字符串解析为 Map
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> dataMap = mapper.readValue(data, new TypeReference<Map<String, Object>>() {});

            // 检查必填字段 name 是否存在且为 String 类型
            if (!dataMap.containsKey("name")) {
                throw new Exception("Missing required field: name");
            }
            if (!(dataMap.get("name") instanceof String)) {
                throw new Exception("Field 'name' must be a string");
            }

            // 检查必填字段 price 是否存在
            if (!dataMap.containsKey("price")) {
                throw new Exception("Missing required field: price");
            }
            // 获取 price 值并判断是否为数字（这里要求是整数类型）
            Object priceObj = dataMap.get("price");
            int price;
            if (priceObj instanceof Number) {
                price = ((Number) priceObj).intValue();
            } else {
                throw new Exception("Field 'price' must be an integer");
            }
            // 检查 price 数值是否>=0
            if (price < 0) {
                throw new Exception("Field 'price' must be >= 0");
            }

            // 如果 category 字段不存在，则设置默认值 "General"
            if (!dataMap.containsKey("category")) {
                dataMap.put("category", "General");
            } else {
                if (!(dataMap.get("category") instanceof String)) {
                    throw new Exception("Field 'category' must be a string");
                }
            }
            // 将数据转换为字符串形式返回，格式与 Python 的 repr 类似，使用单引号包围
            String result = "{'name': '" + dataMap.get("name") + "', 'price': " + price + ", 'category': '" + dataMap.get("category") + "'}";
            return result;
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }
}