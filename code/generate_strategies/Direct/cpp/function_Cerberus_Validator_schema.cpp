#include <string>
#include <algorithm>
#include <nlohmann/json.hpp>
#include <type_traits>

class DataValidator {
public:
    using json = nlohmann::json;
    
    
    template<typename T>
    std::string validate_data(const T& data_input) {
        if constexpr (std::is_same_v<T, std::string>) {
            // 将输入中的单引号替换为双引号，以适应 JSON 解析要求
            std::string fixed = data_input;
            std::replace(fixed.begin(), fixed.end(), '\'', '\"');
            
            json data;
            try {
                data = json::parse(fixed);
            }
            catch (const json::parse_error& e) {
                return "Error: Invalid data - {\"parse_error\": [\"" + std::string(e.what()) + "\"]}";
            }
            
            // 校验字段 name
            if (data.contains("name")) {
                if (!data["name"].is_string()) {
                    return "Error: Invalid data - {'name': ['must be of string type']}";
                } else {
                    std::string name = data["name"].get<std::string>();
                    if (name.length() < 3) {
                        return "Error: Invalid data - {'name': ['min length is 3']}";
                    }
                }
            }
            // 校验字段 age
            if (data.contains("age")) {
                if (!data["age"].is_number_integer()) {
                    return "Error: Invalid data - {'age': ['must be of integer type']}";
                } else {
                    int age = data["age"].get<int>();
                    if (age < 18) {
                        return "Error: Invalid data - {'age': ['min value is 18']}";
                    }
                    else if (age > 100) {
                        return "Error: Invalid data - {'age': ['max value is 100']}";
                    }
                }
            }
            
            return "Valid data: {'name': {'type': 'string', 'minlength': 3}, 'age': {'type': 'integer', 'min': 18, 'max': 100}}";
        } else {
            return "Error: Input must be a string";
        }
    }
};