package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;

public class ProductProcessor {

    // 该唯一的方法 process_product_data 对应 Python 中的同名函数
    public String process_product_data(String product_data) {
        // 使用 Jackson 实现 JSON 的解析与序列化
        try {
            ObjectMapper mapper = new ObjectMapper();
            // 解析 JSON 字符串为 Map
            Map<String, Object> data = mapper.readValue(product_data, Map.class);

            // 检查必须存在的字段 "name" 和 "price"
            if (!data.containsKey("name") || !data.containsKey("price")) {
                throw new Exception();
            }

            // 模拟 pre_load 钩子: 如果存在 "name"，且为字符串则转换为大写，否则报错
            Object nameObj = data.get("name");
            if (nameObj instanceof String) {
                String nameUpper = ((String) nameObj).toUpperCase();
                data.put("name", nameUpper);
            } else {
                throw new Exception();
            }

            // 检查 "price" 的数据类型, 要求为数值类型 (float 对应 Java 中的 double)
            Object priceObj = data.get("price");
            double price;
            if (priceObj instanceof Number) {
                price = ((Number) priceObj).doubleValue();
            } else {
                throw new Exception();
            }
            data.put("price", price);

            // "in_stock" 字段如果存在需要为布尔值，如果不存在，默认为 true
            if (data.containsKey("in_stock")) {
                Object inStockObj = data.get("in_stock");
                if (!(inStockObj instanceof Boolean)) {
                    throw new Exception();
                }
            } else {
                data.put("in_stock", true);
            }

            // 序列化 Map 成 JSON 字符串并返回
            return mapper.writeValueAsString(data);
        } catch (Exception e) {
            // 如果解析失败或验证出错，则返回错误信息
            return "Error: invalid input";
        }
    }
}