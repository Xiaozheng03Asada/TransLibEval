#include <nlohmann/json.hpp>
#include <set>
#include <string>
class JaccardExample {
public:
    float compute_jaccard_distance(const nlohmann::json& string1, const nlohmann::json& string2){
        try {
            if (!string1.is_string() || !string2.is_string()) {
                throw std::invalid_argument("Input must be string");
            }
    
            std::string str1 = string1.get<std::string>();
            std::string str2 = string2.get<std::string>();
    
            if (str1.empty() && str2.empty()) {
                return 0.0f;
            }
    

            std::set<std::string> set1, set2;
            if (str1.length() > 1) {
                for (size_t i = 0; i < str1.length() - 1; ++i) {
                    set1.insert(str1.substr(i, 2));
                }
            }
            if (str2.length() > 1) {
                for (size_t i = 0; i < str2.length() - 1; ++i) {
                    set2.insert(str2.substr(i, 2));
                }
            }
    

            if (set1.empty() && set2.empty()) {
                return 0.0f;
            }
    

            std::set<std::string> intersection;
            for (const auto& s : set1) {
                if (set2.find(s) != set2.end()) {
                    intersection.insert(s);
                }
            }
    

            std::set<std::string> union_set = set1;
            union_set.insert(set2.begin(), set2.end());
    
      
            return 1.0f - static_cast<float>(intersection.size()) / union_set.size();
        }
        catch (const nlohmann::json::exception&) {
            throw std::invalid_argument("Input must be string");
        }
    }
};