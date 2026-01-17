#include <indicators/progress_bar.hpp>
#include <chrono>
#include <thread>
#include <stdexcept>
#include <type_traits>

class ProgressBar {
public:
    template<typename T>
    static int might_fail_function(const T& mininterval_value) {
        // 类型检查
        if constexpr (!std::is_same_v<T, float>) {
            throw std::invalid_argument("mininterval must be a number");
        }

        std::vector<int> results;
        results.reserve(10);

        // 创建进度条
        indicators::ProgressBar bar{
            indicators::option::BarWidth{50},
            indicators::option::Start{"["},
            indicators::option::End{"]"},
            indicators::option::ShowPercentage{true},
            indicators::option::MinProgress{0},
            indicators::option::MaxProgress{100},
            indicators::option::ForegroundColor{indicators::Color::white}
        };

        if (mininterval_value > 0) {
            bar.set_option(indicators::option::PostfixText{"Interval: " + std::to_string(mininterval_value)});
        }

        auto last_update = std::chrono::steady_clock::now();
        
        for (int i = 0; i < 10; ++i) {
            // 缩短每次迭代的睡眠时间
            std::this_thread::sleep_for(std::chrono::milliseconds(50));
            results.push_back(i);
            
            auto now = std::chrono::steady_clock::now();
            auto duration = std::chrono::duration<float>(now - last_update).count();

            // 根据 mininterval_value 控制进度条更新频率
            if (mininterval_value <= 0 || duration >= mininterval_value) {
                bar.set_progress((i + 1) * 10);
                last_update = now;
            }
        }

        // 确保最后显示100%
        bar.set_progress(100);
        
        return results.size();
    }
};