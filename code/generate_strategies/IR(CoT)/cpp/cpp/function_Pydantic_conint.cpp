
#include <string>
#include <nlohmann/json.hpp>
#include <sstream>
#include <vector>
#include <stdexcept>
class ProductValidator {
public:
    std::string create_product(int stock, float price) {
        
        nlohmann::json errors = nlohmann::json::array();
        bool has_errors = false;
    
        
        if (stock < 0 || stock > 1000) {
            has_errors = true;
            nlohmann::json error;
            error["loc"] = {"stock"};
            error["msg"] = "value is not within range [0, 1000]";
            error["type"] = "value_error.range";
            errors.push_back(error);
        }
    
        
        if (price <= 0) {
            has_errors = true;
            nlohmann::json error;
            error["loc"] = {"price"};
            error["msg"] = "ensure this value is greater than 0";
            error["type"] = "value_error.number.not_gt";
            errors.push_back(error);
        }
    
        if (has_errors) {
            return errors.dump();
        }
    
        
        std::ostringstream result;
        result << "stock=" << stock << " price=" << price;
        return result.str();
    }
};