#include <string>
#include <nlohmann/json.hpp>

class JSONValidator {
public:
    std::string validate_json(const std::string& data_str, const std::string& schema_str) {
        try {
            
            nlohmann::json data = nlohmann::json::parse(data_str);
            nlohmann::json schema = nlohmann::json::parse(schema_str);

            
            if (schema.contains("type") && schema["type"] == "object" && !data.is_object()) {
                return "JSON is invalid";
            }

            
            if (schema.contains("required")) {
                for (const auto& required : schema["required"]) {
                    if (!data.contains(required)) {
                        return "JSON is invalid";
                    }
                }
            }

            
            if (schema.contains("properties")) {
                for (const auto& [key, value] : data.items()) {
                    if (schema["properties"].contains(key)) {
                        auto& prop_schema = schema["properties"][key];
                        if (prop_schema.contains("type")) {
                            std::string type = prop_schema["type"];
                            if (type == "string" && !value.is_string()) {
                                return "JSON is invalid";
                            }
                            else if (type == "integer" && !value.is_number_integer()) {
                                return "JSON is invalid";
                            }
                            else if (type == "number" && !value.is_number()) {
                                return "JSON is invalid";
                            }
                            else if (type == "boolean" && !value.is_boolean()) {
                                return "JSON is invalid";
                            }
                            else if (type == "object" && !value.is_object()) {
                                return "JSON is invalid";
                            }
                            else if (type == "array" && !value.is_array()) {
                                return "JSON is invalid";
                            }
                        }
                    }
                }
            }

            return "JSON is valid";
        }
        catch (const std::exception&) {
            return "JSON is invalid";
        }
    }
};