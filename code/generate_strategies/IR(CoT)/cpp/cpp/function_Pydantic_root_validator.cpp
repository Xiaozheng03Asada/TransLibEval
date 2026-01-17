#include <string>
#include <nlohmann/json.hpp>
#include <sstream>
class Order {
public:
    std::string check_discount_for_large_order(int quantity, float price, float discount = 0.0) {
        
        nlohmann::json errors = nlohmann::json::array();
        bool has_errors = false;
    
        
        
        if (quantity > 10 && discount == 0) {
            has_errors = true;
            nlohmann::json error;
            error["loc"] = {"__root__"};
            error["msg"] = "Discount must be greater than 0 for orders with quantity greater than 10";
            error["type"] = "value_error";
            errors.push_back(error);
        }
    
        
        if (discount > 0 && price <= 0) {
            has_errors = true;
            nlohmann::json error;
            error["loc"] = {"__root__"};
            error["msg"] = "Price must be positive when discount is applied";
            error["type"] = "value_error";
            errors.push_back(error);
        }
    
        
        if (has_errors) {
            return errors.dump();
        }
    
        
        std::ostringstream result;
        result << "quantity=" << quantity << " price=" << price << " discount=" << discount;
        return result.str();
    }
};