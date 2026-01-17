#include <string>
#include <vector>
#include <nlohmann/json.hpp>

class IterableProcessor {
public:
    std::string get_first_n_elements(const std::string& iterable, int n) {
        try {
            auto parsed_iterable = nlohmann::json::parse(iterable);

            if (!parsed_iterable.is_array() || n < 0) {
                return "Error: invalid input";
            }

            nlohmann::json result = nlohmann::json::array();
            for (size_t i = 0; i < std::min(static_cast<size_t>(n), parsed_iterable.size()); ++i) {
                result.push_back(parsed_iterable[i]);
            }

            return result.dump();
        } catch (...) {
            return "Error: invalid input";
        }
    }
};