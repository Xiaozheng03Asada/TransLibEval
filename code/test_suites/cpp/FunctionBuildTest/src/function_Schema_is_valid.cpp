#include <string>
#include <algorithm>
#include <cctype>

class UserValidator {
public:
    std::string validate_user(const std::string& name, const std::string& age, const std::string& email) {
        struct UserData {
            std::string name;
            int age;
            std::string email;

            UserData(const std::string& name, const std::string& age, const std::string& email) {
                this->name = name;
                try {
                    this->age = std::stoi(age);
                } catch (const std::invalid_argument& e) {
                    this->age = -1;
                } catch (const std::out_of_range& e) {
                    this->age = -1;
                }
                this->email = email;
            }

            bool isValid() const {
                if (name.empty()) {
                    return false;
                }

                if (age <= 0) {
                    return false;
                }

                return email.empty() || email.find('@') != std::string::npos;
            }
        };

        UserData userData(name, age, email);
        return userData.isValid() ? "Valid data" : "Invalid data";
    }
};