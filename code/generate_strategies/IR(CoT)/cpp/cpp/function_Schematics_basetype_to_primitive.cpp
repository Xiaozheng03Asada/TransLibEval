#include <string>
#include <stdexcept>
#include <nlohmann/json.hpp>

using json = nlohmann::json;

class IntegerListType {
public:

    std::string to_primitive(const std::string& value) {
   
        if (value.empty() || value[0] != '[' || value[value.size() - 1] != ']') {
            throw std::invalid_argument("Value must be a string representing a list.");
        }
        
        try {
            
            json parsed_value = json::parse(value);
            
        
            if (!parsed_value.is_array()) {
                throw std::invalid_argument("Value must be a list.");
            }
            
         
            for (const auto& item : parsed_value) {
                if (!item.is_number_integer()) {
                    throw std::invalid_argument("All items in the list must be integers.");
                }
            }
            
      
            return parsed_value.dump();
        }
        catch (const json::parse_error&) {
            throw std::invalid_argument("Value must be a valid list of integers in string format.");
        }
    }
};