#include <string>
#include <nlohmann/json.hpp>
#include <sstream>
class Order {
public:
    std::string check_discount_for_large_order(int quantity, float price, float discount = 0.0) {
        // 使用nlohmann/json模拟Pydantic的验证行为
        nlohmann::json errors = nlohmann::json::array();
        bool has_errors = false;
    
        // 模拟Root Validator，执行跨字段的验证逻辑
        // 检查大订单必须有折扣的规则
        if (quantity > 10 && discount == 0) {
            has_errors = true;
            nlohmann::json error;
            error["loc"] = {"__root__"};
            error["msg"] = "Discount must be greater than 0 for orders with quantity greater than 10";
            error["type"] = "value_error";
            errors.push_back(error);
        }
    
        // 检查有折扣时价格必须为正数的规则
        if (discount > 0 && price <= 0) {
            has_errors = true;
            nlohmann::json error;
            error["loc"] = {"__root__"};
            error["msg"] = "Price must be positive when discount is applied";
            error["type"] = "value_error";
            errors.push_back(error);
        }
    
        // 如果有错误，返回错误信息
        if (has_errors) {
            return errors.dump();
        }
    
        // 没有错误，返回格式化的订单信息
        std::ostringstream result;
        result << "quantity=" << quantity << " price=" << price << " discount=" << discount;
        return result.str();
    }
};