#include <string>
#include <indicators/progress_bar.hpp>
#include <thread>
#include <chrono>

class ProgressRange {
public:
    static std::string might_fail_function(int start, int stop, int step,
        const std::string& desc = "General trange loop", 
        bool ascii = true,
        int miniters = 0,
        float maxinterval = 0.0f,
        float mininterval = 0.0f) {
        
        std::string results;
        int total_iterations = (stop - start + step - 1) / step;
        results.reserve(total_iterations * 2); 

        indicators::ProgressBar bar{
            indicators::option::BarWidth{50},
            indicators::option::Start{ascii ? "[" : "├"},
            indicators::option::End{ascii ? "]" : "┤"},
            indicators::option::ShowPercentage{true},
            indicators::option::MaxProgress{total_iterations},
            indicators::option::PrefixText{desc}
        };

        int update_count = 0;
        auto last_update = std::chrono::steady_clock::now();

        for (int i = start; i < stop; i += step) {
            
            update_count++;
            bool should_update = miniters == 0 || update_count >= miniters;

            
            auto now = std::chrono::steady_clock::now();
            float elapsed = std::chrono::duration<float>(now - last_update).count();
            bool interval_passed = mininterval <= 0.0f || elapsed >= mininterval;

            
            if (should_update && interval_passed) {
                bar.set_progress((i - start + step) / step);
                last_update = now;
                update_count = 0;
            }

            
            results += std::to_string(i * 2);
        }

        return results;
    }
};