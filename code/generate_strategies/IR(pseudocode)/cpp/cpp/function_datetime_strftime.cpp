#include <string>
#include <date/date.h>
#include <chrono>
#include <sstream>
#include <iomanip>
class TimeFormatter
{
public:
    std::string format_current_time(int year, int month, int day,
                                    int hour, int minute, int second)
    {
        std::ostringstream oss;
        oss << date::year(year) << '-'
            << std::setw(2) << std::setfill('0') << month << '-'
            << std::setw(2) << std::setfill('0') << day << ' '
            << std::setw(2) << std::setfill('0') << hour << ':'
            << std::setw(2) << std::setfill('0') << minute << ':'
            << std::setw(2) << std::setfill('0') << second;

        return oss.str();
    }
};
