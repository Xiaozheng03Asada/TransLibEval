#include <string>
#include <nlohmann/json.hpp>

using json = nlohmann::json;

class DataNormalizer {
public:
    std::string normalize_data(const std::string& dataStr) {
        try {
            json data = json::parse(dataStr);

            // 验证和设置name字段
            if (!data.contains("name") || data["name"].is_null()) {
                data["name"] = "Unknown";
            } else if (!data["name"].is_string()) {
                return "Error: Name must be a string";
            }

            // 验证和设置age字段
            if (!data.contains("age") || data["age"].is_null()) {
                data["age"] = 18;
            } else if (!data["age"].is_number()) {
                return "Error: Age must be an integer";
            }

            return data.dump();
        } catch (const json::parse_error& e) {
            return "Error: Invalid string format for data";
        }
    }
};