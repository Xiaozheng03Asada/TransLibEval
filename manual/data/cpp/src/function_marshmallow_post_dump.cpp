#include <string>
#include <nlohmann/json.hpp>
using nlohmann::json;

class UserSchema {
public:
    std::string serialize_user_data(const std::string &user_data) {
        try {
            json parsed = json::parse(user_data);
            if (!parsed.contains("age") || !parsed.contains("score")) {
                return "Error: invalid input";
            }
            if (!parsed["age"].is_number_integer() || !parsed["score"].is_number()) {
                return "Error: invalid input";
            }
            json output;
            output["age"] = parsed["age"];
            output["score"] = parsed["score"];
            json result;
            result["result"] = output;
            return result.dump();
        } catch (json::exception &) {
            return "Error: invalid input";
        }
    }
};
