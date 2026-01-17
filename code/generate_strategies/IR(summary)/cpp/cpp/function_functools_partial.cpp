#include <boost/any.hpp>
#include <stdexcept>

class PartialFunctionExample {
public:
    int apply_partial_function(const boost::any& base_value, const boost::any& add_value) {
        try {
            int base = boost::any_cast<int>(base_value);
            int add = boost::any_cast<int>(add_value);
            auto add_func = [](int base, int add) { return base + add; };
            return add_func(base, add);
        }
        catch (const boost::bad_any_cast&) {
            throw std::invalid_argument("Input must be integers");
        }
    }
};