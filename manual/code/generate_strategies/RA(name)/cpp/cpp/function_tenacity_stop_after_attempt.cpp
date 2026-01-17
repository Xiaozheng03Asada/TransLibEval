#include <boost/asio.hpp>
#include <boost/system/system_error.hpp>
#include <iostream>
#include <stdexcept>
#include <thread>
#include <functional>

class RetryFunction {
public:
    int might_fail_function(int value, float extra_sleep = 0, bool handle_retry_error = false) {
        int attempts = 0;
        const int max_attempts = 3;
        const int wait_time = 1; // seconds

        while (attempts < max_attempts) {
            try {
        
                std::this_thread::sleep_for(std::chrono::milliseconds(static_cast<int>(extra_sleep * 1000)));
                if (value < 5) {
                    throw std::runtime_error("Value is too small");
                }
                return value;
            } catch (const std::runtime_error& e) {
                attempts++;
                if (attempts >= max_attempts) {
                    if (handle_retry_error) {
                        throw std::runtime_error("Operation failed after retries: " + std::string(e.what()));
                    }
                    throw;
                }
                std::this_thread::sleep_for(std::chrono::seconds(wait_time));
            }
        }
        return value;
    }
};