#include <string>
#include <map>
#include <nlohmann/json.hpp>

class JSONValidator {
public:
    std::string validate_json(const std::string& data_str, const std::string& schema_str) {
        try {
            // 解析 JSON 字符串
            nlohmann::json data = nlohmann::json::parse(data_str);
            nlohmann::json schema = nlohmann::json::parse(schema_str);

            // 检查数据是否为对象
            if (!data.is_object()) {
                return "Validation failed";
            }

            // 验证每个字段
            for (auto& [key, value] : data.items()) {
                auto props = schema.value("properties", nlohmann::json::object());
                auto it = props.find(key);
                if (it != props.end()) {
                    auto& field_schema = *it;
                    // 检查类型
                    if (field_schema.contains("type")) {
                        std::string type = field_schema["type"];
                        // 内联类型验证逻辑
                        bool type_valid = false;
                        if (type == "string") type_valid = value.is_string();
                        else if (type == "integer") type_valid = value.is_number_integer();
                        else if (type == "boolean") type_valid = value.is_boolean();
                        else if (type == "object") type_valid = value.is_object();
                        else if (type == "array") type_valid = value.is_array();
                        else if (type == "number") type_valid = value.is_number();
                        
                        if (!type_valid) {
                            return "Validation failed";
                        }
                    }
                    // 递归验证嵌套对象
                    if (value.is_object()) {
                        std::string nested_result = validate_json(value.dump(), field_schema.dump());
                        if (nested_result != "JSON is valid") {
                            return "Validation failed";
                        }
                    }
                } else if (!props.contains(key)) {
                    return "Validation failed";
                }
            }

            // 验证必需字段
            if (schema.contains("required")) {
                for (const auto& req : schema["required"]) {
                    if (!data.contains(req)) {
                        return "Validation failed";
                    }
                }
            }

            return "JSON is valid";
        }
        catch (const std::exception&) {
            return "Validation failed";
        }
    }
};