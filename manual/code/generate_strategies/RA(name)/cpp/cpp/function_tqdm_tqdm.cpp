#include <string>
#include <indicators/progress_bar.hpp>

class ProgressBar {
public:
    static std::string might_fail_function(const std::string& data) {
        std::string result;
        result.reserve(data.length());

        indicators::ProgressBar bar{
            indicators::option::BarWidth{50},
            indicators::option::Start{"["},
            indicators::option::End{"]"},
            indicators::option::ShowPercentage{true},
            indicators::option::MaxProgress{data.length()}
        };

        try {
            for (size_t i = 0; i < data.length(); ++i) {
                result += data[i];
                bar.set_progress(i + 1);
            }
            return result;
        } catch (const std::exception& e) {
            throw;
        }
    }
};