#include <string>

namespace com {
namespace example {

class NumpyExample {
public:
    static std::string check_number(int x) {
        return x > 0 ? "positive" : (x < 0 ? "negative" : "zero");
    }
};

} // namespace example
} // namespace com