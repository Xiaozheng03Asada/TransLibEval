#include <string>
#include <vector>
#include <boost/tokenizer.hpp>
#include <stdexcept>
#include <algorithm>
#include <sstream>
#include <chrono>
#include <thread>

class SpacyTextProcessor {
public:
    static std::string spacy_text(const std::string& text) {
        auto start_time = std::chrono::steady_clock::now();

        try {
            // 空字符串检查
            if (text.empty()) {
                throw std::runtime_error("Empty input string");
            }

       
            if (text.size() > 1024 * 1024 * 1024) { 
                throw std::bad_alloc();
            }

            boost::char_separator<char> sep(" ", ".,!");
            boost::tokenizer<boost::char_separator<char>> tokens(text, sep);
            
            std::vector<std::string> result(tokens.begin(), tokens.end());

            // 将结果格式化为字符串
            std::ostringstream oss;
            for (const auto& word : result) {
                // 检查是否超时
                auto current_time = std::chrono::steady_clock::now();
                auto elapsed_time = std::chrono::duration_cast<std::chrono::seconds>(current_time - start_time).count();
                if (elapsed_time > 10) {
                    throw std::runtime_error("Processing time exceeded 10 seconds");
                }

                oss << word << " ";
            }
            std::string formatted_result = oss.str();
            if (!formatted_result.empty()) {
                formatted_result.pop_back(); 
            }

            return formatted_result;
        }
        catch (const std::bad_alloc&) {
            throw std::runtime_error("Insufficient memory error");
        }
        catch (const std::exception& e) {
            throw std::runtime_error("Other errors: " + std::string(e.what()));
        }
    }
};