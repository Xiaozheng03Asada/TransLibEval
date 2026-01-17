#include <string>
#include <boost/algorithm/string.hpp>
#include <vector>
#include <map>
class OrderedDictCalculator {
public:
    std::string process_key_value_pairs(const std::string& data){
        if (data.empty()) {
            return "failed";
        }
    
        std::vector<std::string> items;
        boost::split(items, data, boost::is_any_of(","));
        
        std::map<std::string, std::string> ordered_dict;
        std::vector<std::string> key_order;  
    
        for (const auto& item : items) {
            std::string trimmed_item = boost::trim_copy(item);
            if (!trimmed_item.empty()) {
                std::vector<std::string> key_value;
                boost::split(key_value, trimmed_item, boost::is_any_of(":"));
                
                if (key_value.size() == 2) {
                    std::string key = boost::trim_copy(key_value[0]);
                    std::string value = boost::trim_copy(key_value[1]);
                    
                    if (ordered_dict.find(key) == ordered_dict.end()) {
                        key_order.push_back(key);
                    }
                    ordered_dict[key] = value;
                }
            }
        }
    
        if (ordered_dict.empty()) {
            return "failed";
        }
    
        std::string result;
        for (const auto& key : key_order) {
            if (!result.empty()) {
                result += ", ";
            }
            result += key + ":" + ordered_dict[key];
        }
        
        return result;
    }
};