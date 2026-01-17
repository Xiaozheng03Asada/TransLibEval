#include <string>
#include <date/date.h>
#include <chrono>
#include <sstream>
class DateTimeModifier
{
public:
    std::string get_modified_datetime(const std::string &original_datetime_str,
                                      int days = 0, int hours = 0, int weeks = 0)
    {
        try
        {
            std::istringstream in{original_datetime_str};
            date::sys_time<std::chrono::seconds> tp;
            in >> date::parse("%Y-%m-%d %H:%M:%S", tp);

            if (in.fail())
            {
                throw std::invalid_argument("original_datetime_str format is invalid");
            }

            auto total_days = days + weeks * 7;
            auto total_hours = std::chrono::hours(hours);
            auto total_days_duration = date::days(total_days);

            tp += total_days_duration;
            tp += total_hours;

            return date::format("%Y-%m-%d %H:%M:%S", tp);
        }
        catch (const std::invalid_argument &)
        {
            throw;
        }
        catch (...)
        {
            throw std::invalid_argument("original_datetime_str format is invalid");
        }
    }
};