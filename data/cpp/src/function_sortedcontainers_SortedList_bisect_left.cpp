#include <string>
#include <vector>
#include <sstream>
#include <algorithm>
#include <boost/container/flat_set.hpp>

class HanSeok {
public:
    int find_insert_position(int value = 0, const std::string& sorted_list = "") {
        std::string list_to_use = sorted_list.empty() ? "1,3,5,8" : sorted_list;
        
        
        std::vector<int> values;
        std::istringstream ss(list_to_use);
        std::string token;
        
        while (std::getline(ss, token, ',')) {
            try {
                values.push_back(std::stoi(token));
            }
            catch (...) {
            
            }
        }
        
    
        boost::container::flat_set<int> sorted_values(values.begin(), values.end());
        
       
        auto it = std::lower_bound(sorted_values.begin(), sorted_values.end(), value);
        
     
        return std::distance(sorted_values.begin(), it);
    }
};