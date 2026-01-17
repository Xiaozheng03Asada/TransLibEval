#include <string>
#include <stdexcept>
#include <ctime>
#include <iomanip>
#include <sstream>

class DateTimeModifier {
public:
    std::string get_modified_datetime(const std::string& original_datetime_str, int days, int hours, int weeks) {
        if (original_datetime_str.empty()) {
            throw std::invalid_argument("original_datetime must be a string");
        }

        struct tm tm = {};
        std::istringstream ss(original_datetime_str);
        ss >> std::get_time(&tm, "%Y-%m-%d %H:%M:%S");

        if (ss.fail()) {
            throw std::invalid_argument("original_datetime_str format is invalid");
        }

        time_t time = mktime(&tm);
        time += days * 86400 + hours * 3600 + weeks * 604800;

        tm = *localtime(&time);
        char buffer[20];
        strftime(buffer, sizeof(buffer), "%Y-%m-%d %H:%M:%S", &tm);

        return std::string(buffer);
    }
};