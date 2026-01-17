#include <string>
#include <vector>
#include <stdexcept>

class RepeatExample {
public:
    std::string repeat_element(const std::string& element, int count) {
        if (count < 0) {
            throw std::invalid_argument("Count cannot be negative");
        }
        if (count == 0) {
            return "";
        }
        std::vector<std::string> elements(count, element);
        std::string result;
        for (size_t i = 0; i < elements.size(); ++i) {
            result += elements[i];
            if (i != elements.size() - 1) {
                result += ", ";
            }
        }
        return result;
    }
};