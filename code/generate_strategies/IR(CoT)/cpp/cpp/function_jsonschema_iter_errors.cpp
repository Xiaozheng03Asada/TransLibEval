#include <string>
#include <nlohmann/json.hpp>

class JSONValidator {
public:
    std::string validate_json(const std::string& data_str, const std::string& schema_str) {
        try {
            
            nlohmann::json data = nlohmann::json::parse(data_str);
            nlohmann::json schema = nlohmann::json::parse(schema_str);

            
            if (schema.contains("type") && schema["type"] == "object" && !data.is_object()) {
                return "Validation failed: Not an object";
            }

            
            std::vector<std::string> errors;

            
            if (schema.contains("required")) {
                for (const auto& required : schema["required"]) {
                    if (!data.contains(required)) {
                        errors.push_back("'" + required.get<std::string>() + "' is a required property");
                    }
                }
            }

            
            if (schema.contains("properties")) {
                for (const auto& [key, value] : data.items()) {
                    if (schema["properties"].contains(key)) {
                        auto& prop_schema = schema["properties"][key];
                        if (prop_schema.contains("type")) {
                            std::string type = prop_schema["type"];
                            
                            
                            bool type_valid = true;
                            if (type == "string" && !value.is_string()) type_valid = false;
                            else if (type == "integer" && !value.is_number_integer()) type_valid = false;
                            else if (type == "number" && !value.is_number()) type_valid = false;
                            else if (type == "boolean" && !value.is_boolean()) type_valid = false;
                            else if (type == "object" && !value.is_object()) type_valid = false;
                            else if (type == "array" && !value.is_array()) type_valid = false;

                            if (!type_valid) {
                                
                                std::string value_str;
                                if (value.is_string()) {
                                    value_str = value.get<std::string>();
                                } else {
                                    value_str = value.dump();
                                }
                                errors.push_back("'" + value_str + "' is not of type '" + type + "'");
                            }

                            
                            if (type == "object" && value.is_object()) {
                                std::string nested_result = validate_json(value.dump(), prop_schema.dump());
                                if (nested_result != "JSON is valid") {
                                    errors.push_back("In property '" + key + "': " + nested_result);
                                }
                            }
                        }
                    }
                }
            }

            
            if (!errors.empty()) {
                return "Validation failed: " + errors[0];
            }
            return "JSON is valid";

        } catch (const nlohmann::json::parse_error& e) {
            return "Validation failed: Invalid JSON format - " + std::string(e.what());
        } catch (const std::exception& e) {
            return std::string("Validation failed: ") + e.what();
        }
    }
};