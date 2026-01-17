#include <string>
#include <boost/unordered_map.hpp>
#include <algorithm>

class StringProcessor {
public:
    double calculate_setratio(const std::string& str1, const std::string& str2) {
       
        boost::unordered_map<char, int> freq1, freq2;
        for (char c : str1)
            freq1[c]++;
        for (char c : str2)
            freq2[c]++;

        
        int intersection = 0;
        for (const auto& pair : freq1) {
            char c = pair.first;
            if (freq2.find(c) != freq2.end()) {
                intersection += std::min(pair.second, freq2[c]);
            }
        }

        
        int total = str1.length() + str2.length();

        
        if (total == 0)
            return 1.0;

        
        return (2.0 * intersection) / total;
    }};