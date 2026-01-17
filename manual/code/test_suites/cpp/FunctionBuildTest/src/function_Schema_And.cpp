#include <string>
#include <stdexcept>

class StringValidator {
public:
    std::string validate_user_input(const std::string& username, const std::string& password) {
        try {
            // 使用empty()进行验证
            if (username.empty()) {
                return "Validation failed";
            }

            // 验证密码长度
            if (password.empty() || password.length() < 8) {
                return "Validation failed";
            }

            return "Validation passed: username = " + username + ", password = " + password;
        } catch (const std::exception& e) {
            return "Validation failed";
        }
    }
};