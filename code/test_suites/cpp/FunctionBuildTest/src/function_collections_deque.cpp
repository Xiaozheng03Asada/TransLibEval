#include <deque>
#include <vector>
#include <string>
#include <stdexcept>

class DequeOperations {
public:
    std::string perform_operation(const std::string& operation_type) {
        std::deque<int> d;

        if (operation_type == "append_and_appendleft") {
            d.push_back(1);
            d.push_front(0);
            return std::to_string(d[0]) + ", " + std::to_string(d[1]);
        } else if (operation_type == "pop_and_popleft") {
            d = {1, 2, 3};
            d.pop_front();
            d.pop_back();
            return std::to_string(d.size());
        } else if (operation_type == "remove") {
            d = {1, 2, 3};
            d.erase(std::remove(d.begin(), d.end(), 2), d.end());
            return std::to_string(d[0]) + ", " + std::to_string(d[1]);
        } else if (operation_type == "clear") {
            d = {1, 2, 3};
            d.clear();
            return std::to_string(d.size());
        } else {
            throw std::invalid_argument("Invalid operation type");
        }
    }
};