#include <string>
#include <boost/any.hpp>
#include <boost/algorithm/string/join.hpp>
#include <vector>
#include <sstream>
class RepeatExample {
public:
std::string repeat_element(const boost::any& element, const boost::any& count){
        // 首先检查并转换count参数
        int repeat_count;
        try {
            if (count.type() == typeid(int)) {
                repeat_count = boost::any_cast<int>(count);
            } else {
                throw std::invalid_argument("Count must be an integer");
            }
        } catch (const boost::bad_any_cast&) {
            throw std::invalid_argument("Count must be an integer");
        }

        if (repeat_count < 0) {
            throw std::invalid_argument("Count must be non-negative");
        }
        
        if (repeat_count == 0) {
            return "";
        }

        std::string element_str;
        try {
            if (element.type() == typeid(int)) {
                element_str = std::to_string(boost::any_cast<int>(element));
            }
            else if (element.type() == typeid(std::string)) {
                element_str = boost::any_cast<std::string>(element);
            }
            else if (element.type() == typeid(const char*)) {
                element_str = boost::any_cast<const char*>(element);
            }
            else {
                throw std::invalid_argument("Unsupported element type");
            }
        }
        catch (const boost::bad_any_cast&) {
            throw std::invalid_argument("Invalid element type");
        }

        std::vector<std::string> repeated(repeat_count, element_str);
        return boost::algorithm::join(repeated, ", ");
    }
};