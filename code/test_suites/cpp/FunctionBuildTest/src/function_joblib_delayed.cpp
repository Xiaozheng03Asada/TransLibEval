#include <future>
#include <stdexcept>
#include <typeinfo>
#include <thread>
#include <memory>

class DelayedExample {
public:
    template <typename T, typename U>
    std::string apply_delayed_function(T x, U y) {
        class TypeError : public std::runtime_error {
        public:
            TypeError(const std::string& message) : std::runtime_error(message) {}
        };

        if (typeid(x) != typeid(int) || typeid(y) != typeid(int)) {
            throw TypeError("Inputs must be Integers");
        }
        int a = static_cast<int>(x);
        int b = static_cast<int>(y);

        auto future = std::async(std::launch::async, [a, b]() {
            return a + b;
        });

        try {
            int result = future.get();
            return std::to_string(result);
        } catch (const std::exception& e) {
            throw std::runtime_error(e.what());
        }
    }
};