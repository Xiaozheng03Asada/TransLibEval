#include <string>
#include <vector>
#include <sstream>
#include <stdexcept>
#include <nlohmann/json.hpp>

using json = nlohmann::json;

class Person {
public:
    std::string get_native_representation(const std::string& data) {
       
        std::vector<std::string> parts;
        std::stringstream ss(data);
        std::string item;
        
        while (std::getline(ss, item, ',')) {
            parts.push_back(item);
        }
        
  
        if (parts.size() != 2) {
            return "error: Invalid input format";
        }
        
    
        std::string name = parts[0];
        int age;
        
     
        try {
            age = std::stoi(parts[1]);
        }
        catch (const std::exception&) {
            return "error: Invalid age format";
        }
        
      
        std::string city = "Unknown";
        
  
        if (name.empty()) {
            return "error: {\"name\": [\"This field is required.\"]}";
        }
        
        if (age < 0) {
            return "error: {\"age\": [\"Value must be greater than or equal to 0.\"]}";
        }
        
      
        return name + " " + std::to_string(age) + " " + city;
    }
};