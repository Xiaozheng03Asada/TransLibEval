#include <vector>
#include <string>
#include <algorithm>
#include <boost/algorithm/string.hpp>
#include <boost/algorithm/string/join.hpp>

class ChunkManager {
public:
    std::string chunk_data(const std::string& data_str, int chunk_size) {
        if (chunk_size <= 0) {
            return "Error";
        }
        
       
        std::vector<std::string> tokens;
        boost::split(tokens, data_str, boost::is_any_of(","));
        
       
        std::vector<std::vector<std::string>> chunks;
        for (size_t i = 0; i < tokens.size(); i += chunk_size) {
            size_t end_idx = std::min(i + static_cast<size_t>(chunk_size), tokens.size());
            chunks.emplace_back(tokens.begin() + i, tokens.begin() + end_idx);
        }
        
       
        std::vector<std::string> chunk_strs;
        for (const auto& chunk : chunks) {
            chunk_strs.push_back(boost::algorithm::join(chunk, ","));
        }
        
      
        std::string result = boost::algorithm::join(chunk_strs, ";");
        return result;
    }
};