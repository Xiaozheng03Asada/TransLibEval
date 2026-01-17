#include <boost/algorithm/string.hpp>
#include <boost/regex.hpp>
#include <stdexcept>
#include <string>
#include <type_traits>

class TextSlugifier {
public:
    template<typename T>
    std::string create_slug(const T &text, const std::string &delim = "-") {
        if constexpr (std::is_same_v<T, std::string>) {
            if (text == "")
                return "";
           
            std::string lower = boost::algorithm::to_lower_copy(text);
            
            boost::regex nonword("[^a-z0-9\\s]+");
            std::string sanitized = boost::regex_replace(lower, nonword, " ");
        
            boost::algorithm::trim(sanitized);
         
            boost::regex spaces("\\s+");
            std::string slug = boost::regex_replace(sanitized, spaces, delim);
            return slug;
        } else {
            throw std::invalid_argument("Input text must be a string");
        }
    }
};