#include <string>
#include <vector>
#include <nlohmann/json.hpp>

class ListProcessor {
public:
    std::string process_list(const std::string& input) {
        try {
            auto parsed = nlohmann::json::parse(input);

            if (!parsed.is_array()) {
                return "Error: input is not iterable";
            }

            std::vector<nlohmann::json> flattened;

            std::function<void(const nlohmann::json&)> flatten = [&](const nlohmann::json& element) {
                if (element.is_array()) {
                    for (const auto& sub_element : element) {
                        flatten(sub_element);
                    }
                } else if (element.is_object()) {
                    for (const auto& [key, value] : element.items()) {
                        flatten(value);
                    }
                } else {
                    flattened.push_back(element);
                }
            };

            flatten(parsed);

            return nlohmann::json(flattened).dump();
        } catch (...) {
            return "Error: invalid input";
        }
    }
};