#include <string>
#include <type_traits>
#include <fmt/format.h>

class PersonValidator {
public:
    template<typename T1, typename T2, typename T3>
    std::string validate(const T1& name, const T2& age, const T3& city) {
        try {
            // 验证姓名
            if constexpr (!std::is_convertible_v<T1, std::string>) {
                return "Invalid data: One or more fields are incorrect";
            }
            std::string name_str = std::string(name);
            if (name_str.empty()) {
                return "Invalid data: One or more fields are incorrect";
            }

            // 验证年龄
            int age_value;
            try {
                if constexpr (std::is_arithmetic_v<T2>) {
                    age_value = static_cast<int>(age);
                } else {
                    age_value = std::stoi(std::string(age));
                }
                if (age_value < 18 || age_value > 100) {
                    return "Invalid data: One or more fields are incorrect";
                }
            } catch (...) {
                return "Invalid data: One or more fields are incorrect";
            }

            // 验证城市
            std::string city_str;
            if constexpr (std::is_convertible_v<T3, std::string>) {
                city_str = std::string(city);
            } else if constexpr (std::is_arithmetic_v<T3>) {
                city_str = std::to_string(city);
            } else {
                return "Invalid data: One or more fields are incorrect";
            }
            if (city_str.empty()) {
                return "Invalid data: One or more fields are incorrect";
            }

            bool valid_city = false;
            bool has_space = false;
            for (char c : city_str) {
                if (std::isalpha(c)) {
                    valid_city = true;
                }
                if (c == ' ') {
                    has_space = true;
                }
            }
            if (!valid_city && !has_space) {
                return "Invalid data: One or more fields are incorrect";
            }

            // 全部验证通过，返回格式化结果
            return fmt::format("Valid data: name = {}, age = {}, city = {}", 
                             name_str, age_value, city_str);
        }
        catch (...) {
            return "Invalid data: One or more fields are incorrect";
        }
    }
};