#include <string>
#include <nlohmann/json.hpp>
#include <type_traits>

class DataNormalizer {
public:
    using json = nlohmann::json;
    
    template<typename T>
    std::string normalize_data(const T& data_input) {
        if constexpr (std::is_same_v<T, std::string>) {
            if (data_input.empty()) {
                return "{\"name\": \"Unknown\", \"age\": 18}";
            }
            try {
                json data = json::parse(data_input);
                std::string name = data.contains("name") ? data["name"].get<std::string>() : "Unknown";
                int age = data.contains("age") ? data["age"].get<int>() : 18;
                
                return "{\"name\": \"" + name + "\", \"age\": " + std::to_string(age) + "}";
            }
            catch (const json::parse_error&) {
                if (!data_input.empty()) {
                    return "Error: Invalid string format for data";
                }
                return "{\"name\": \"Unknown\", \"age\": 18}";
            }
        } else {
            return "Error: Input must be a string";
        }
    }
};