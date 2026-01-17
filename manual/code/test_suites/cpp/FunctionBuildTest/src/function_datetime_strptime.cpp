#include <string>
#include <ctime>
#include <iomanip>
#include <sstream>

class DateParser {
public:
    std::string parse_date(const std::string& date_str, const std::string& format_str) {
        struct tm tm = {};
        std::istringstream ss(date_str);
        ss >> std::get_time(&tm, format_str.c_str());
        if (ss.fail()) {
            return "failed";
        }
        char buffer[20];
        strftime(buffer, sizeof(buffer), "%Y-%m-%d %H:%M:%S", &tm);
        return std::string(buffer);
    }
};