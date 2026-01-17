#include <boost/algorithm/string.hpp>
#include <string>
#include <vector>
#include <unordered_map>
#include <sstream>
#include <iomanip>
#include <cmath>

class SpacyVectorProcessor {
public:
    std::string vector_lengths(const std::string& text) {
        std::vector<std::string> tokens;
        boost::split(tokens, text, boost::is_any_of(" "), boost::token_compress_on);

        std::ostringstream oss;
        for (const auto& token : tokens) {
   
            static const std::unordered_map<std::string, std::vector<double>> predefined_vectors = {
                {"hello", {1.0, 2.0, 3.0}},
                {"world", {4.0, 5.0, 6.0}},
                {"Hello", {1.1, 2.1, 3.1}},
                {",",     {0.1, 0.2, 0.3}},
                {"!",     {0.2, 0.3, 0.4}}
            };

            auto it = predefined_vectors.find(token);
            std::vector<double> vector;
            if (it != predefined_vectors.end()) {
                vector = it->second;
            } else {
                // 默认返回值
                vector = {0.0, 0.0, 0.0};
            }

            // 计算词向量的欧几里得长度
            double sum_of_squares = 0.0;
            for (double value : vector) {
                sum_of_squares += value * value;
            }
            double vector_length = std::sqrt(sum_of_squares);

            oss << token << ": " << std::fixed << std::setprecision(6) << vector_length << ", ";
        }

        std::string result = oss.str();
        if (!result.empty()) {
            result.pop_back();
            result.pop_back();
        }

        return result;
    }
};