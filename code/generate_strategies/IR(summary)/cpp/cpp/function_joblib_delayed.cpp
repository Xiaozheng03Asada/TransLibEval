#include <string>
#include <boost/any.hpp>
#include <omp.h>
class DelayedExample {
public:
    std::string apply_delayed_function(const boost::any& x, const boost::any& y){ try {
        int x_val = boost::any_cast<int>(x);
        int y_val = boost::any_cast<int>(y);

        int result;
        #pragma omp parallel
        {
            #pragma omp single
            {
                result = x_val + y_val;
            }
        }

        return std::to_string(result);
    }
    catch (const boost::bad_any_cast&) {
        throw std::invalid_argument("Inputs must be integers");
    }}
};