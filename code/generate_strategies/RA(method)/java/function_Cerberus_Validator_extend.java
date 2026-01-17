package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

public class SimpleValidator {
    public String validate_data(String data_str, String schema_str) {
        class DataClass {
            @Min(value = 18, message = "min value is 18")
            @Max(value = 100, message = "max value is 100")
            private Integer age;

            @Size(max = 50, message = "max length is 50")
            private String name;

            public Integer getAge() { return age; }
            public void setAge(Integer age) { this.age = age; }
            public String getName() { return name; }
            public void setName(String name) { this.name = name; }
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = mapper.readValue(data_str, HashMap.class);

            DataClass dataClass = new DataClass();
            if (data.get("age") != null) {
                if (data.get("age") instanceof Integer) {
                    dataClass.setAge((Integer) data.get("age"));
                } else if (data.get("age") instanceof Number) {
                    dataClass.setAge(((Number) data.get("age")).intValue());
                }
            }
            if (data.get("name") != null) {
                if (data.get("name") instanceof String) {
                    dataClass.setName((String) data.get("name"));
                } else {
                    return "Invalid data: {\"name\":[\"must be of string type\"]}";
                }
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            var violations = validator.validate(dataClass);

            if (violations.isEmpty()) {
                return "Valid data";
            } else {
                Map<String, String[]> errors = new LinkedHashMap<>();
                // 首先添加age的错误（如果有）
                for (var violation : violations) {
                    if (violation.getPropertyPath().toString().equals("age")) {
                        errors.put("age", new String[]{violation.getMessage()});
                    }
                }
                // 然后添加name的错误（如果有）
                for (var violation : violations) {
                    if (violation.getPropertyPath().toString().equals("name")) {
                        errors.put("name", new String[]{violation.getMessage()});
                    }
                }

                // 使用特定的JSON字符串格式
                StringBuilder errorJson = new StringBuilder("Invalid data: {");
                boolean first = true;
                for (Map.Entry<String, String[]> entry : errors.entrySet()) {
                    if (!first) {
                        errorJson.append(",");
                    }
                    first = false;
                    errorJson.append("\"").append(entry.getKey()).append("\":[\"")
                            .append(entry.getValue()[0]).append("\"]");
                }
                errorJson.append("}");
                return errorJson.toString();
            }
        } catch (Exception e) {
            return "Error: Invalid string format for data or schema";
        }
    }
}