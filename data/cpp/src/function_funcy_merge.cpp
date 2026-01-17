#include <string>
#include <nlohmann/json.hpp>

class DictMerger {
public:
    std::string combine_dicts(const std::string& dict1, const std::string& dict2) {
        try {
           
            auto parsed_dict1 = nlohmann::json::parse(dict1);
            auto parsed_dict2 = nlohmann::json::parse(dict2);

            if (!parsed_dict1.is_object() || !parsed_dict2.is_object()) {
                return "Error: input is not a dictionary";
            }

            for (auto& [key, value] : parsed_dict2.items()) {
                parsed_dict1[key] = value;
            }

            return parsed_dict1.dump();
        } catch (...) {
            return "Error: invalid input";
        }
    }
};