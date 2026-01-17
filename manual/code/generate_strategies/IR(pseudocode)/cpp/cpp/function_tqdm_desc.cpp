#include <string>
#include <stdexcept>
#include <indicators/progress_bar.hpp>
#include <algorithm>
#include <cctype>

class ProgressBar {
public:
    static std::string process_with_progress_bar(const std::string& data, const std::string& desc_text = "") {
        
        if (!desc_text.empty() && !std::all_of(desc_text.begin(), desc_text.end(), 
            [](unsigned char c){ return std::isprint(c); })) {
            throw std::invalid_argument("desc_text must be a valid string");
        }

        
        if (!data.empty() && !std::all_of(data.begin(), data.end(), 
            [](unsigned char c){ return std::isdigit(c); })) {
            throw std::invalid_argument("Input data must contain only digits");
        }

        std::string result;
        result.reserve(data.length() * 2);

        try {
            
            indicators::ProgressBar bar{
                indicators::option::BarWidth{50},
                indicators::option::Start{" ["},
                indicators::option::End{"]"},
                indicators::option::ForegroundColor{indicators::Color::white},
                indicators::option::ShowElapsedTime{true},
                indicators::option::ShowPercentage{true}
            };

            if (!desc_text.empty()) {
                bar.set_option(indicators::option::PrefixText{desc_text});
            }

            
            for (size_t i = 0; i < data.length(); ++i) {
                int value = (data[i] - '0') * 2;
                result += std::to_string(value);
                
                
                bar.set_progress(static_cast<float>(i + 1) / data.length() * 100);
            }

            return result;

        } catch (const std::exception& e) {
            throw;
        }
    }
};