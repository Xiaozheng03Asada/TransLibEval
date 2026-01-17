#include <string>
#include <regex>
#include <stdexcept>

class PersonValidator {
public:
    std::string validate(const std::string& name, const std::string& age, const std::string& city) {
        try {
            // 验证name
            if (name.empty() || name.find_first_not_of(' ') == std::string::npos) {
                return "Invalid data: One or more fields are incorrect";
            }

            // 验证age
            int ageValue;
            try {
                ageValue = std::stoi(age);
                if (ageValue < 18 || ageValue > 100) {
                    return "Invalid data: One or more fields are incorrect";
                }
            } catch (const std::invalid_argument& e) {
                return "Invalid data: One or more fields are incorrect";
            }

            // 验证city
            std::regex cityRegex("[a-zA-Z ]+");
            if (city.empty() || !std::regex_match(city, cityRegex)) {
                return "Invalid data: One or more fields are incorrect";
            }

            // 如果所有验证都通过，返回成功消息
            return "Valid data: name = " + name + ", age = " + std::to_string(ageValue) + ", city = " + city;

        } catch (const std::exception& e) {
            return "Invalid data: One or more fields are incorrect";
        }
    }
};