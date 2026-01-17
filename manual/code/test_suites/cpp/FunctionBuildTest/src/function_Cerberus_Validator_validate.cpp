#include <string>
#include <stdexcept>

class NumberComparer {
public:
    std::string compare_numbers(const std::string& num1, const std::string& num2) {
        if (num1.empty() || num2.empty()) {
            std::string fieldName = num1.empty() ? "num1" : "num2";
            return "Error: Invalid input. {'" + fieldName + "': ['must be of number type']}";
        }

        try {
            double n1 = std::stod(num1);
            double n2 = std::stod(num2);

            if (n1 > n2) {
                return "Greater";
            } else if (n1 < n2) {
                return "Smaller";
            } else {
                return "Equal";
            }
        } catch (const std::invalid_argument& e) {
            return "Error: Invalid input. {'num1': ['must be of number type']}";
        }
    }
};