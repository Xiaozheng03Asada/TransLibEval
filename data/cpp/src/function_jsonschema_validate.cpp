#include <string>
#include <nlohmann/json.hpp>

class JSONValidator {
public:
    std::string validate_json(const std::string& data_str, const std::string& schema_str) {
        try {
            // 解析 JSON 字符串
            nlohmann::json data = nlohmann::json::parse(data_str);
            nlohmann::json schema = nlohmann::json::parse(schema_str);

            // 验证类型
            if (schema.contains("type") && schema["type"] == "object" && !data.is_object()) {
                return "False";
            }

            // 验证必需字段
            if (schema.contains("required")) {
                for (const auto& required : schema["required"]) {
                    if (!data.contains(required)) {
                        return "False";
                    }
                }
            }

            // 验证属性类型
            if (schema.contains("properties")) {
                for (const auto& [key, value] : data.items()) {
                    if (schema["properties"].contains(key)) {
                        auto& prop_schema = schema["properties"][key];
                        if (prop_schema.contains("type")) {
                            std::string type = prop_schema["type"];
                            
                            // 类型检查
                            bool type_valid = true;
                            if (type == "string" && !value.is_string()) type_valid = false;
                            else if (type == "integer" && !value.is_number_integer()) type_valid = false;
                            else if (type == "number" && !value.is_number()) type_valid = false;
                            else if (type == "boolean" && !value.is_boolean()) type_valid = false;
                            else if (type == "object" && !value.is_object()) type_valid = false;
                            else if (type == "array" && !value.is_array()) type_valid = false;

                            if (!type_valid) {
                                return "False";
                            }

                            // 递归验证嵌套对象
                            if (type == "object" && value.is_object()) {
                                std::string nested_result = validate_json(value.dump(), prop_schema.dump());
                                if (nested_result != "True") {
                                    return "False";
                                }
                            }
                        }
                    }
                }
            }

            return "True";
        }
        catch (const std::exception&) {
            return "Validation failed: Invalid JSON format";
        }
    }
};