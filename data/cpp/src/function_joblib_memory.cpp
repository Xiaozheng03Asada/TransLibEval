#include <boost/any.hpp>
#include <boost/compute/detail/lru_cache.hpp>
#include <functional>
#include <map>

class MemoryExample {
public:
    int compute_square(const boost::any& x) {
        try {
            int value = boost::any_cast<int>(x);
            
            static std::map<int, int> cache;
            auto it = cache.find(value);
            if (it != cache.end()) {
                return it->second;
            }
            
            int result = value * value;
            cache[value] = result;
            return result;
        }
        catch (const boost::bad_any_cast&) {
            throw std::invalid_argument("Input must be an integer");
        }
    }
};