#include <string>
#include <vector>
#include <algorithm>
#include <sstream>
#include <nlohmann/json.hpp>

using json = nlohmann::json;

class SortedListHandler {
public:
    std::string modify_sorted_list(int index) {
 
        json sorted_list = json::array();
        sorted_list.push_back(5);
        sorted_list.push_back(3);
        sorted_list.push_back(8);
        sorted_list.push_back(1);
        
     
        std::sort(sorted_list.begin(), sorted_list.end());
        
        try {
       
            if (index < 0 || index >= static_cast<int>(sorted_list.size())) {
                return "Index out of range";
            }
            
        
            int removed_item = sorted_list[index].get<int>();
            
          
            sorted_list.erase(sorted_list.begin() + index);
            
         
            std::ostringstream result;
            result << "Removed item: " << removed_item << ", Remaining list: [";
            
            for (size_t i = 0; i < sorted_list.size(); ++i) {
                if (i > 0) {
                    result << ", ";
                }
                result << sorted_list[i].get<int>();
            }
            
            result << "]";
            
            return result.str();
        }
        catch (const std::exception&) {
            return "Index out of range";
        }
    }
};