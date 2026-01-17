#include <iostream>
#include <map>
#include <vector>
#include <string>
#include <sstream>
#include <json/json.h>

class DataValidator {
public:
    std::string validate_data(const std::string& dataStr) {
        class InnerDataValidator {
        public:
            std::map<std::string, std::vector<std::string>> validateData(const std::map<std::string, Json::Value>& data) {
                std::map<std::string, std::vector<std::string>> errors;

                // 验证name字段
                if (!data.count("name") || !data.at("name").isString()) {
                    errors["name"].push_back("must be of string type");
                } else if (data.at("name").asString().length() < 3) {
                    errors["name"].push_back("min length is 3");
                }

                // 验证age字段
                if (!data.count("age") || !data.at("age").isNumeric()) {
                    errors["age"].push_back("must be of integer type");
                } else {
                    int age = data.at("age").asInt();
                    if (age < 18) {
                        errors["age"].push_back("min value is 18");
                    } else if (age > 100) {
                        errors["age"].push_back("max value is 100");
                    }
                }

                return errors;
            }
        };

        try {
            // Schema 定义，使用map保持顺序
            std::map<std::string, std::map<std::string, Json::Value>> schema;
            std::map<std::string, Json::Value> nameRules;
            nameRules["type"] = "string";
            nameRules["minlength"] = 3;

            std::map<std::string, Json::Value> ageRules;
            ageRules["type"] = "integer";
            ageRules["min"] = 18;
            ageRules["max"] = 100;

            schema["name"] = nameRules;
            schema["age"] = ageRules;

            // 解析输入的JSON字符串
            Json::Value data;
            Json::CharReaderBuilder reader;
            std::string errs;
            std::istringstream s(dataStr);
            if (!Json::parseFromStream(reader, s, &data, &errs)) {
                return "Error: Invalid JSON - " + errs;
            }

            // 创建验证器实例并验证数据
            InnerDataValidator validator;
            auto errors = validator.validateData(data);

            if (!errors.empty()) {
                // 手动构建错误消息字符串，确保数组格式正确
                std::ostringstream errorMsg;
                errorMsg << "Error: Invalid data - {";
                bool first = true;
                for (const auto& entry : errors) {
                    if (!first) {
                        errorMsg << ", ";
                    }
                    errorMsg << entry.first << "=[";
                    for (size_t i = 0; i < entry.second.size(); ++i) {
                        if (i != 0) {
                            errorMsg << ", ";
                        }
                        errorMsg << entry.second[i];
                    }
                    errorMsg << "]";
                    first = false;
                }
                errorMsg << "}";
                return errorMsg.str();
            }

            return "Valid data: " + schemaToString(schema);
        } catch (const std::exception& e) {
            return "Error: Invalid data - {" + std::string(e.what()) + "}";
        }
    }

private:
    std::string schemaToString(const std::map<std::string, std::map<std::string, Json::Value>>& schema) {
        std::ostringstream oss;
        oss << "{";
        bool first = true;
        for (const auto& entry : schema) {
            if (!first) {
                oss << ", ";
            }
            oss << entry.first << "={";
            bool firstRule = true;
            for (const auto& rule : entry.second) {
                if (!firstRule) {
                    oss << ", ";
                }
                oss << rule.first << "=" << rule.second.asString();
                firstRule = false;
            }
            oss << "}";
            first = false;
        }
        oss << "}";
        return oss.str();
    }
};