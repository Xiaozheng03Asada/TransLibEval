
#include <string>
#include <nlohmann/json.hpp>
#include <regex>
#include <sstream>

class UserProfileHandler {
public:
    std::string create_user_profile(const std::string& username, const std::string& email) {
        nlohmann::json errors = nlohmann::json::array();
        bool has_errors = false;
    
        
        std::regex username_regex(R"(^[a-zA-Z0-9_]+$)");
        if (username.length() < 3) {
            has_errors = true;
            nlohmann::json error;
            error["loc"] = {"username"};
            error["msg"] = "ensure this value has at least 3 characters";
            error["type"] = "value_error.any_str.min_length";
            errors.push_back(error);
        }
        else if (username.length() > 20) {
            has_errors = true;
            nlohmann::json error;
            error["loc"] = {"username"};
            error["msg"] = "ensure this value has at most 20 characters";
            error["type"] = "value_error.any_str.max_length";
            errors.push_back(error);
        }
        else if (!std::regex_match(username, username_regex)) {
            has_errors = true;
            nlohmann::json error;
            error["loc"] = {"username"};
            error["msg"] = "string does not match regex '^[a-zA-Z0-9_]+$'";
            error["type"] = "value_error.str.regex";
            errors.push_back(error);
        }
    
        
        std::regex email_regex(R"([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,})");
        if (!std::regex_match(email, email_regex)) {
            has_errors = true;
            nlohmann::json error;
            error["loc"] = {"email"};
            error["msg"] = "value is not a valid email address";
            error["type"] = "value_error.email";
            errors.push_back(error);
        }
    
        if (has_errors) {
            return errors.dump();
        }
    
        std::ostringstream result;
        result << "username='" << username << "' email='" << email << "'";
        return result.str();
    }
};