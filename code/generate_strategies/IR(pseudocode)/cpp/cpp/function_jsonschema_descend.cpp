#include <string>
#include <map>
#include <nlohmann/json.hpp>

class JSONValidator {
public:
    std::string validate_json(const std::string& data_str, const std::string& schema_str) {
        try {
            
            nlohmann::json data = nlohmann::json::parse(data_str);
            nlohmann::json schema = nlohmann::json::parse(schema_str);

            
            if (!data.is_object()) {
                return "Validation failed";
            }

            
            for (auto& [key, value] : data.items()) {
                auto props = schema.value("properties", nlohmann::json::object());
                auto it = props.find(key);
                if (it != props.end()) {
                    auto& field_schema = *it;
                    
                    if (field_schema.contains("type")) {
                        std::string type = field_schema["type"];
                        
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