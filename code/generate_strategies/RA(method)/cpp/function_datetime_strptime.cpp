#include <string>
#include <date/date.h>
#include <iomanip>
#include <sstream>
class DateParser {
public:
    std::string parse_date(const std::string& date_str, const std::string& format_str) {
        if (date_str.empty()) {
            return "failed";
        }
    
        try {
            std::istringstream in{date_str};
            date::sys_time<std::chrono::seconds> tp;
    
            if (format_str == "%Y-%m-%d %H:%M:%S") {
                in >> date::parse("%Y-%m-%d %H:%M:%S", tp);
            } else if (format_str == "%d/%m/%Y") {
                in >> date::parse("%d/%m/%Y", tp);
                if (!in.fail()) {
                    auto days = date::floor<date::days>(tp);
                    tp = days;  
                }
            } else {
                return "failed";
            }
    
            if (in.fail()) {
                return "failed";
            }
    
            return date::format("%Y-%m-%d %H:%M:%S", tp);
        }
        catch (...) {
            return "failed";
        }
    }
};