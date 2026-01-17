#include <string>
#include <vector>
#include <stdexcept>
#include <nlohmann/json.hpp>

using json = nlohmann::json;

class ValidateTuple {
public:
    std::string validate(const std::string& input_str) {
        try {
            std::vector<std::string> parts;
            size_t prev = 0, pos;
            while ((pos = input_str.find(',', prev)) != std::string::npos) {
                parts.push_back(input_str.substr(prev, pos - prev));
                prev = pos + 1;
            }
            parts.push_back(input_str.substr(prev));

            if (parts.size() != 2) {
                return "Invalid input: Must be a pair of values";
            }

            // 创建JSON Schema来验证输入
            json jsonSchema = {
                {"type", "object"},
                {"properties", {
                    {"first", {{"type", "integer"}}},
                    {"second", {{"type", "string"}}}
                }},
                {"required", {"first", "second"}}
            };

            // 创建要验证的数据
            json jsonData;
            try {
                jsonData["first"] = std::stoi(parts[0]);
            } catch (const std::invalid_argument& e) {
                return "Invalid input: First value must be an integer";
            }
            jsonData["second"] = parts[1];

            // 使用Schema验证
            try {
                auto schema = nlohmann::json_schema::json_validator(jsonSchema);
                schema.validate(jsonData);
            } catch (const std::exception& e) {
                return "Invalid input: " + std::string(e.what());
            }

            // 如果验证通过，返回格式化的结果
            return "Valid tuple: (" + std::to_string(jsonData["first"].get<int>()) + ", " + jsonData["second"].get<std::string>() + ")";

        } catch (const std::exception& e) {
            return "Error: " + std::string(e.what());
        }
    }
};