#include <string>
#include <vector>
#include <iterator>
#include <algorithm>

class CycleProcessor {
public:
    std::string test_cycle(const std::string& input_sequence, int num_elements) {
        if (input_sequence.empty()) {
            return "";
        }

        std::vector<char> charList(input_sequence.begin(), input_sequence.end());
        auto cycleIterator = charList.begin();

        std::string result;
        for (int i = 0; i < num_elements; ++i) {
            if (cycleIterator == charList.end()) {
                cycleIterator = charList.begin();
            }
            result += *cycleIterator++;
        }
        return result;
    }
};