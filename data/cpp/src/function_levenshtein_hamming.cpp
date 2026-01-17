#include <string>
#include <type_traits>
#include <boost/range/combine.hpp>
#include <boost/range/algorithm/count_if.hpp>
#include <boost/tuple/tuple.hpp>

class StringProcessor {
public:
    template<typename T1, typename T2>
    std::string calculate_hamming_distance(const T1& str1, const T2& str2) {
        if constexpr (!std::is_convertible_v<T1, std::string> ||
                      !std::is_convertible_v<T2, std::string>) {
            return "Error: Both inputs must be strings";
        } else {
            std::string s1(str1);
            std::string s2(str2);
            if (s1.size() != s2.size()) {
                return "Strings must be of the same length for Hamming distance.";
            }
           
            auto zipped = boost::combine(s1, s2);
            int distance = boost::range::count_if(zipped, [](const boost::tuple<char, char>& tup) {
                return boost::get<0>(tup) != boost::get<1>(tup);
            });
            return std::to_string(distance);
        }
    }
};