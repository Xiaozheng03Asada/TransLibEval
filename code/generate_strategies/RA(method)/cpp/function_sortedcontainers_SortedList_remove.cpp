#include <string>
#include <sstream>
#include <vector>
#include <algorithm>
#include <nlohmann/json.hpp>

using json = nlohmann::json;

class HanSeok {
public:
    std::string remove_element_from_list(int value = 0, const std::string& input_list = "") {
        try {
          
            std::string list_to_use = input_list.empty() ? "5,3,8,1" : input_list;
            
           
            std::vector<int> values;
            std::istringstream ss(list_to_use);
            std::string token;
            
            while (std::getline(ss, token, ',')) {
                if (!token.empty()) {
                    try {
                        values.push_back(std::stoi(token));
                    }
                    catch (...) {
                  
                    }
                }
            }
            
            json sorted_list = json::array();
            for (const auto& val : values) {
                sorted_list.push_back(val);
            }
            
          
            std::sort(sorted_list.begin(), sorted_list.end());
            
          
            auto it = std::find(sorted_list.begin(), sorted_list.end(), value);
            if (it != sorted_list.end()) {
                sorted_list.erase(it);  
            }
            else {
                return "Value not found";
            }
            
          
            std::ostringstream result;
            bool first = true;
            for (const auto& item : sorted_list) {
                if (!first) {
                    result << ",";
                }
                result << item.get<int>();
                first = false;
            }
            
            return result.str();
        }
        catch (const json::exception& e) {
            return std::string("JSON error: ") + e.what();
        }
        catch (const std::exception& e) {
            return std::string("Error: ") + e.what();
        }
    }
};