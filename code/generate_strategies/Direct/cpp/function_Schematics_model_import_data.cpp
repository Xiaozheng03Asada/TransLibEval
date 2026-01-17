#include <string>
#include <stdexcept>
#include <nlohmann/json.hpp>

using json = nlohmann::json;

class Product {
public:
    std::string import_and_validate(const std::string& data) {
        try {

            name = "";
            price = 0;
            category = "General";
            
  
            json data_dict = json::parse(data);
            
 
            if (data_dict.contains("name")) {
                name = data_dict["name"].get<std::string>();
            }
            
            if (data_dict.contains("price")) {
                price = data_dict["price"].get<int>();
            }
            
            if (data_dict.contains("category")) {
                category = data_dict["category"].get<std::string>();
            }
            

            if (name.empty()) {
                throw std::runtime_error("name: This field is required.");
            }
            if (price < 0) {
                throw std::runtime_error("price: Value must be greater than or equal to 0.");
            }
            
  
            std::string result = "{'name':'" + name + "','price':" + std::to_string(price) + 
                                ",'category':'" + category + "'}";
            
            return result;
        }
        catch (const json::parse_error& e) {
            return "error: Invalid JSON format";
        }
        catch (const std::exception& e) {
            return std::string("error: ") + e.what();
        }
    }

private:
    std::string name;
    int price;
    std::string category;
};