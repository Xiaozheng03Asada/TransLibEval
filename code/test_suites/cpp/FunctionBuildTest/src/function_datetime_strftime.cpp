#include <string>
#include <iomanip>
#include <sstream>
#include <ctime>

class TimeFormatter {
public:
    std::string format_current_time(int year, int month, int day, int hour, int minute, int second) {
        struct tm tm = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        tm.tm_year = year - 1900;
        tm.tm_mon = month - 1;
        tm.tm_mday = day;
        tm.tm_hour = hour;
        tm.tm_min = minute;
        tm.tm_sec = second;
        mktime(&tm);

        char buffer[20];
        strftime(buffer, sizeof(buffer), "%Y-%m-%d %H:%M:%S", &tm);
        return std::string(buffer);
    }
};