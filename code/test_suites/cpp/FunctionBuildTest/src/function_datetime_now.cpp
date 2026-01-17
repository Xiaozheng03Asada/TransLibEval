#include <string>
#include <ctime>
#include <iomanip>
#include <sstream>

class DateTimeModifier {
public:
    std::string get_current_datetime(const std::string& date_string) {
        if (!date_string.empty()) {
            try {
                struct tm tm = {};
                std::istringstream ss(date_string);
                ss >> std::get_time(&tm, "%Y-%m-%d %H:%M:%S");
                if (ss.fail()) {
                    throw std::invalid_argument("The date string format is incorrect.");
                }
                char buffer[20];
                strftime(buffer, sizeof(buffer), "%Y-%m-%d %H:%M:%S", &tm);
                return std::string(buffer);
            } catch (const std::exception& e) {
                throw std::invalid_argument("The date string format is incorrect.");
            }
        } else {
            return "1900-01-01 00:00:00";
        }
    }
};