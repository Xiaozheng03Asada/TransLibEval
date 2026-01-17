package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Arrays;

public class DataValidator {

    public String validate_data(String dataStr) {
        // 所有代码都在这个方法内部实现
        class DataValidator {
            private Map<String, String[]> validateData(Map<String, Object> data) {
                Map<String, String[]> errors = new LinkedHashMap<>();

                // 验证name字段
                Object nameValue = data.get("name");
                if (nameValue == null) {
                    errors.put("name", new String[]{"must be of string type"});
                } else if (!(nameValue instanceof String)) {
                    errors.put("name", new String[]{"must be of string type"});
                } else if (((String) nameValue).length() < 3) {
                    errors.put("name", new String[]{"min length is 3"});
                }

                // 验证age字段
                Object ageValue = data.get("age");
                if (ageValue == null) {
                    errors.put("age", new String[]{"must be of integer type"});
                } else {
                    int age;
                    if (ageValue instanceof Integer) {
                        age = (Integer) ageValue;
                    } else if (ageValue instanceof Number) {
                        age = ((Number) ageValue).intValue();
                    } else {
                        errors.put("age", new String[]{"must be of integer type"});
                        return errors;
                    }

                    if (age < 18) {
                        errors.put("age", new String[]{"min value is 18"});
                    } else if (age > 100) {
                        errors.put("age", new String[]{"max value is 100"});
                    }
                }

                return errors;
            }
        }

        try {
            // Schema 定义，使用LinkedHashMap保持顺序
            Map<String, Map<String, Object>> schema = new LinkedHashMap<>();
            Map<String, Object> nameRules = new LinkedHashMap<>();
            nameRules.put("type", "string");
            nameRules.put("minlength", 3);

            Map<String, Object> ageRules = new LinkedHashMap<>();
            ageRules.put("type", "integer");
            ageRules.put("min", 18);
            ageRules.put("max", 100);

            schema.put("name", nameRules);
            schema.put("age", ageRules);

            // 解析输入的JSON字符串
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = mapper.readValue(dataStr.replace("'", "\""), Map.class);

            // 创建验证器实例并验证数据
            DataValidator validator = new DataValidator();
            Map<String, String[]> errors = validator.validateData(data);

            if (!errors.isEmpty()) {
                // 手动构建错误消息字符串，确保数组格式正确
                StringBuilder errorMsg = new StringBuilder();
                errorMsg.append("Error: Invalid data - {");
                boolean first = true;
                for (Map.Entry<String, String[]> entry : errors.entrySet()) {
                    if (!first) {
                        errorMsg.append(", ");
                    }
                    errorMsg.append(entry.getKey())
                            .append("=[")
                            .append(String.join(", ", entry.getValue()))
                            .append("]");
                    first = false;
                }
                errorMsg.append("}");
                return errorMsg.toString();
            }

            return "Valid data: " + schema.toString();
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("Cannot deserialize value of type `java.lang.Integer` from String")) {
                return "Error: Invalid data - {age=[must be of integer type]}";
            }
            return "Error: Invalid data - {" + e.getMessage() + "}";
        }
    }
}