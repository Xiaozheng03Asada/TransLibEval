
#include <string>
#include <nlohmann/json.hpp>

using ordered_json = nlohmann::ordered_json;

class UserValidator {
public:
    std::string validate_user_data(const std::string &user_data) {
        try {
            ordered_json parsed = ordered_json::parse(user_data);
            if (!parsed.is_object()) {
                return "Error: invalid input";
            }
            if (parsed.size() != 2 || !parsed.contains("age") || !parsed.contains("score")) {
                return "Error: invalid input";
            }
            if (!parsed["age"].is_number_integer()) {
                return "Error: invalid input";
            }
            int age = parsed["age"].get<int>();
            if (age < 0 || age > 120) {
                return "Error: invalid input";
            }
            if (!parsed["score"].is_number()) {
                return "Error: invalid input";
            }
            double score = parsed["score"].get<double>();
            if (score < 0.0 || score > 100.0) {
                return "Error: invalid input";
            }
            ordered_json result;
            result["age"] = age;
            result["score"] = score;
            return result.dump();
        } catch (nlohmann::json::exception &) {
            return "Error: invalid input";
        }
    }
};
