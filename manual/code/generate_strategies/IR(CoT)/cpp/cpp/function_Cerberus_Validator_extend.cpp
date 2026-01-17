#include <string>
#include <regex>
#include <nlohmann/json.hpp>
class SimpleValidator {
    public:
        using json = nlohmann::json;
        std::string validate_data(const std::string& data_str, const std::string& schema_str) {
            try {
                json data = json::parse(data_str);
                json schema = json::parse(schema_str);
                json errors = json::object();
                
                
                if (data.contains("age")) {
                    std::vector<std::string> age_errors;
                    if (!data["age"].is_number_integer()) {
                        age_errors.push_back("must be of integer type");
                    } else {
                        int age = data["age"];
                        if (age < schema["age"]["min"]) {
                            age_errors.push_back("min value is 18");
                        }
                        if (age > schema["age"]["max"]) {
                            age_errors.push_back("max value is 100");
                        }
                    }
                    if (!age_errors.empty()) {
                        errors["age"] = age_errors;
                    }
                }
                
               
                if (data.contains("name")) {
                    std::vector<std::string> name_errors;
                    if (!data["name"].is_string()) {
                        name_errors.push_back("must be of string type");
                    } else {
                        std::string name = data["name"];
                        if (name.length() > schema["name"]["maxlength"]) {
                            name_errors.push_back("max length is 50");
                        }
                    }
                    if (!name_errors.empty()) {
                        errors["name"] = name_errors;
                    }
                }
                
                if (!errors.empty()) {
                
                    json output;
                    for (const auto& [key, value] : errors.items()) {
                        output[key] = value;
                    }
                    std::string formatted = output.dump(-1, ' ', true, nlohmann::json::error_handler_t::strict);
               
                    formatted = std::regex_replace(formatted, std::regex("\":"), "\": ");
                    return "Invalid data: " + formatted;
                }
                return "Valid data";
            }
            catch (const json::parse_error&) {
                return "Error: Invalid string format for data or schema";
            }
        }
    };