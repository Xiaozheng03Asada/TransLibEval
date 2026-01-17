#include <vector>
#include <string>
#include <sstream>
#include <algorithm>

class ChunkManager {
public:
    static std::string chunk_data(const std::string& data_str, int chunk_size) {
        if (data_str.empty() || chunk_size <= 0) return "Error";

        try {
            std::vector<std::string> data;
            std::stringstream ss(data_str);
            std::string item;
            while (std::getline(ss, item, ',')) {
                data.push_back(item);
            }

            std::vector<std::vector<std::string>> chunks;
            for (size_t i = 0; i < data.size(); i += chunk_size) {
                auto last = std::min(data.size(), i + chunk_size);
                chunks.emplace_back(data.begin() + i, data.begin() + last);
            }

            std::stringstream result;
            for (size_t i = 0; i < chunks.size(); ++i) {
                if (i > 0) result << ";";
                for (size_t j = 0; j < chunks[i].size(); ++j) {
                    if (j > 0) result << ",";
                    result << chunks[i][j];
                }
            }
            return result.str();
        } catch (...) {
            return "Error";
        }
    }
};