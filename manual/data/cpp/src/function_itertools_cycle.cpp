#include <string>
#include <boost/iterator/iterator_facade.hpp>

class CycleProcessor
{
public:
    std::string test_cycle(const std::string &input_sequence, int num_elements)
    {
        if (input_sequence.empty())
        {
            return "";
        }

        std::string result;
        result.reserve(num_elements);

        size_t input_length = input_sequence.length();
        for (int i = 0; i < num_elements; ++i)
        {
            result += input_sequence[i % input_length];
        }

        return result;
    }
};