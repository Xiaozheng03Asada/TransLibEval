#include <regex>
#include <string>
#include <date/date.h>
#include <chrono>
#include <sstream>
#include <iomanip>

class DateTimeModifier {
public:
    std::string get_current_datetime(const std::string& date_string = "") {
        if (date_string.empty()) {
            return "1900-01-01 00:00:00";  
        }
        
        try {
            std::istringstream in{date_string};
            date::sys_time<std::chrono::seconds> tp;
            in >> date::parse("%Y-%m-%d %H:%M:%S", tp);
            
            if (in.fail()) {
                throw std::invalid_argument("The date string format is incorrect.");
            }
    
            return date_string;
        }
        catch (const std::exception&) {
            throw std::invalid_argument("The date string format is incorrect.");
        }
    }
};