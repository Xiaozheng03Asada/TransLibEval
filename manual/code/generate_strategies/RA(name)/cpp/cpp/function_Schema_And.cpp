#include <string>
#include <type_traits>
#include <fmt/format.h>

class StringValidator {
public:
    template<typename T1, typename T2>
    std::string validate_user_input(const T1& username, const T2& password) {
        try {
            // 检查用户名类型和内容
            std::string user;
            if constexpr (std::is_convertible_v<T1, std::string>) {
                user = std::string(username);
                if (user.empty()) {
                    return "Validation failed";
                }
            } else {
                return "Validation failed";
            }

            // 检查密码类型和长度
            std::string pass;
            if constexpr (std::is_convertible_v<T2, std::string>) {
                pass = std::string(password);
                if (pass.length() < 8) {
                    return "Validation failed";
                }
            } else {
                return "Validation failed";
            }

            return fmt::format("Validation passed: username = {}, password = {}", user, pass);
        }
        catch (...) {
            return "Validation failed";
        }
    }
};