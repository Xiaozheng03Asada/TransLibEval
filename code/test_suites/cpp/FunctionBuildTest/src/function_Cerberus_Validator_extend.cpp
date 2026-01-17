#include <iostream>
#include <string>
#include <map>
#include <vector>
#include <nlohmann/json.hpp>
#include <validator.hpp>

using json = nlohmann::json;

class SimpleValidator {
public:
    std::string validate_data(const std::string& data_str, const std::string& schema_str) {
        class DataClass {
        public:
            int age;
            std::string name;

            DataClass() : age(0), name("") {}

            void setAge(int age) { this->age = age; }
            void setName(const std::string& name) { this->name = name; }
        };

        try {
            json data = json::parse(data_str);

            DataClass dataClass;
            if (data.contains("age")) {
                if (data["age"].is_number()) {
                    dataClass.setAge(data["age"]);
                } else {
                    return "Invalid data: {\"age\":[\"must be of integer type\"]}";
                }
            }
            if (data.contains("name")) {
                if (data["name"].is_string()) {
                    dataClass.setName(data["name"]);
                } else {
                    return "Invalid data: {\"name\":[\"must be of string type\"]}";
                }
            }

            validator::Validator validator;
            validator.add_rule("age", validator::Min(18, "min value is 18"));
            validator.add_rule("age", validator::Max(100, "max value is 100"));
            validator.add_rule("name", validator::MaxLength(50, "max length is 50"));

            auto violations = validator.validate(dataClass);

            if (violations.empty()) {
                return "Valid data";
            } else {
                std::map<std::string, std::vector<std::string>> errors;
                for (const auto& violation : violations) {
                    errors[violation.first].push_back(violation.second);
                }

                std::string errorJson = "Invalid data: {";
                bool first = true;
                for (const auto& entry : errors) {
                    if (!first) {
                        errorJson += ",";
                    }
                    first = false;
                    errorJson += "\"" + entry.first + "\":[\"" + entry.second[0] + "\"]";
                }
                errorJson += "}";
                return errorJson;
            }
        } catch (const std::exception& e) {
            return "Error: Invalid string format for data or schema";
        }
    }
};