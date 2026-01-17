#include <functional>
#include <stdexcept>

class PartialFunctionExample {
public:
    int apply_partial_function(int base_value, int add_value) {
        class Solution {
        public:
            std::function<int(int, int)> addFunction = [](int base, int add) {
                return base + add;
            };

            int partialApply(int baseVal, int addVal) {
                return addFunction(baseVal, addVal);
            }
        };

        Solution solution;
        return solution.partialApply(base_value, add_value);
    }
};