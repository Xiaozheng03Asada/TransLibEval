
#include <string>
#include <nlohmann/json.hpp>
#include <algorithm>
#include <cctype>
#include <string>
using ordered_json = nlohmann::ordered_json;

class ProductProcessor {
public:
    std::string process_product_data(const std::string &product_data) {
        try {
            ordered_json parsed = ordered_json::parse(product_data);
            if (!parsed.contains("name") || !parsed.contains("price")) {
                return "Error: invalid input";
            }
            if (!parsed["name"].is_string() || !parsed["price"].is_number()) {
                return "Error: invalid input";
            }
            std::string name = parsed["name"].get<std::string>();
            std::transform(name.begin(), name.end(), name.begin(), 
                           [](unsigned char c){ return std::toupper(c); });
            
            bool in_stock = true;
            if (parsed.contains("in_stock")) {
                if (!parsed["in_stock"].is_boolean()) {
                    return "Error: invalid input";
                }
                in_stock = parsed["in_stock"].get<bool>();
            }
            
            ordered_json output;
            output["name"] = name;
            output["price"] = parsed["price"];
            output["in_stock"] = in_stock;
            
            return output.dump();
        } catch (nlohmann::json::exception &) {
            return "Error: invalid input";
        }
    }
};
