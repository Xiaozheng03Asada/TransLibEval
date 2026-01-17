#include <string>
#include <sstream>
#include <boost/container/flat_map.hpp>

class SortedDictHandler {
public:
    std::string modify_sorted_dict(int index) {
      
        boost::container::flat_map<int, std::string> sorted_dict;
        sorted_dict.emplace(3, "three");
        sorted_dict.emplace(1, "one");
        sorted_dict.emplace(5, "five");

        try {
          
            if (index < 0) {
               
                index = sorted_dict.size() + index;
            }
            
            if (index < 0 || index >= static_cast<int>(sorted_dict.size())) {
                return "error: Invalid index";
            }

       
            auto it = sorted_dict.begin();
            std::advance(it, index);

      
            auto key = it->first;
            auto value = it->second;
            std::pair<int, std::string> removed_item = std::make_pair(key, value);

        
            sorted_dict.erase(it);

      
            std::ostringstream result;
            result << "{";
            bool first = true;
            for (const auto& pair : sorted_dict) {
                if (!first) {
                    result << ", ";
                }
                result << pair.first << ": '" << pair.second << "'";
                first = false;
            }
            result << "} (" << removed_item.first << ", '" << removed_item.second << "')";

            return result.str();
        }
        catch (...) {
            return "error: Invalid index";
        }
    }
};