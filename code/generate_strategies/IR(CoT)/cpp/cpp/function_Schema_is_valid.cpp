
#include <string>
#include <fmt/format.h>
#include <type_traits>

class UserValidator {
public:
    template<typename T1, typename T2, typename T3 = std::nullptr_t>
    std::string validate_user(const T1& name, const T2& age, const T3& email = nullptr) {
        try {
            
            if constexpr (!std::is_convertible_v<T1, std::string>) {
                return "Invalid data";
            }

            
            int age_value;
            try {
                if constexpr (std::is_arithmetic_v<T2>) {
                    age_value = static_cast<int>(age);
                } else {
                    age_value = std::stoi(std::string(age));
                }
                if (age_value <= 0) {
                    return "Invalid data";
                }
            } catch (...) {
                return "Invalid data";
            }

            
            if constexpr (!std::is_same_v<T3, std::nullptr_t>) {
                if constexpr (!std::is_convertible_v<T3, std::string>) {
                    return "Invalid data";
                }
                if (email != nullptr) {
                    std::string email_str = std::string(email);
                    if (email_str.find('@') == std::string::npos) {
                        return "Invalid data";
                    }
                }
            }

            return "Valid data";
        } catch (...) {
            return "Invalid data";
        }
    }
};