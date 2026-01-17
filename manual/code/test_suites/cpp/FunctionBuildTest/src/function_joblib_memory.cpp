#include <unordered_map>

class MemoryExample {
public:
    static int compute_square(int x) {
        static std::unordered_map<int, int> cache;

        auto it = cache.find(x);
        if (it != cache.end()) {
            return it->second;
        }
        int result = x * x;
        cache[x] = result;
        return result;
    }
};