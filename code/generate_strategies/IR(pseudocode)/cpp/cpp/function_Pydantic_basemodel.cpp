#include <string>
#include <nlohmann/json.hpp>
#include <stdexcept>
#include <regex>

class UserValidator {
public:
    std::string create_user(const std::string& name, int age, const std::string& email) {
        nlohmann::json errors = nlohmann::json::array();

        
        if (age <= 0) {
            nlohmann::json error;
            error["loc"] = {"age"};
            error["msg"] = "value is not a valid integer";
            error["type"] = "type_error.integer";
            errors.push_back(error);
        }

        
        std::regex email_regex(R"((\w+)(\.{1}\w+)*@(\w+)(\.\w+)+)");
        if (!std::regex_match(email, email_regex)) {
            nlohmann::json error;
            error["loc"] = {"email"};
            error["msg"] = "value is not a valid email address";
            error["type"] = "type_error.email";
            errors.push_back(error);
        }

        
        if (name.empty()) {
            nlohmann::json error;
            error["loc"] = {"name"};
            error["msg"] = "value is not a valid string";
            error["type"] = "type_error.string";
            errors.push_back(error);
        }

        if (!errors.empty()) {
            return errors.dump();
        }

        std::ostringstream result;
        result << "name='" << name << "' age=" << age << " email='" << email << "'";
        return result.str();
    }
};