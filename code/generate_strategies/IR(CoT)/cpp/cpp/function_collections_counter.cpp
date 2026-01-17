#include <sstream>
#include <string>
#include <vector>
#include <boost/tokenizer.hpp>
#include <map>
class CounterCalculator
{
public:
    std::string count_elements(const std::string &data)
    {
        using namespace boost;
        using namespace std;

        tokenizer<> tok(data);
        vector<string> elements;
        std::map<std::string, int> counter;

        for (const auto &token : tok)
        {
            if (counter.find(token) == counter.end()) {
                elements.push_back(token);
            }
            counter[token]++;
        }

        if (counter.empty())
        {
            return "failed";
        }

        stringstream result;
        bool first = true;
        for (const auto &element : elements)
        {
            if (!first)
            {
                result << ", ";
            }
            result << element << ":" << counter[element];
            first = false;
        }

        return result.str();
    }
};