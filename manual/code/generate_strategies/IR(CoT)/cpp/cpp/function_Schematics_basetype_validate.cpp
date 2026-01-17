#include <string>
#include <stdexcept>
#include <regex>
#include <nlohmann/json.hpp>  

using json = nlohmann::json;

class PositiveIntegerType {
public:

    std::string validate(const std::string& value) {
        try {
   
            json j = json::parse("[" + value + "]");
    
            if (!j[0].is_number_integer()) {
                return "Value must be an integer.";
            }
            
     
            int int_value = j[0].get<int>();
            
       
            if (int_value <= 0) {
                return "Value must be a positive integer.";
            }
            
            return "Validation successful.";
        }
        catch (const json::parse_error&) {
     
            return "Value must be an integer.";
        }
        catch (const json::type_error&) {
         
            return "Value must be an integer.";
        }
        catch (const std::exception&) {
    
            return "Value must be an integer.";
        }
    }
};