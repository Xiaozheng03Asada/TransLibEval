
#include <string>
#include <nlohmann/json.hpp>

using ordered_json = nlohmann::ordered_json;

class ProductSchema {
public:
    std::string deserialize_product_data(const std::string &data) {
        try {
            ordered_json parsed = ordered_json::parse(data);
            if (!parsed.contains("name") || !parsed.contains("price")) {
                return "Error: invalid input";
            }
            if (!parsed["name"].is_string() || !parsed["price"].is_number()) {
                return "Error: invalid input";
            }

            ordered_json result;
            result["name"] = parsed["name"];
            result["price"] = parsed["price"];
            if (parsed.contains("in_stock")) {
                if (!parsed["in_stock"].is_boolean()) {
                    return "Error: invalid input";
                }
                result["in_stock"] = parsed["in_stock"];
            } else {
                result["in_stock"] = true;
            }
            return result.dump();
        } catch (ordered_json::exception &) {
            return "Error: invalid input";
        }
    }
};

