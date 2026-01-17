#pragma once

#include <string>
#include <vector>
#include <sstream>
#include <stdexcept>
#include <boost/algorithm/string.hpp>
#include <boost/format.hpp>

class SeabornKDEPlot {
public:
    std::string generate_kdeplot(const std::string& data, bool shade = true, const std::string& color = "blue") {
        if (data.empty()) {
            throw std::invalid_argument("Input data cannot be empty.");
        }

        std::vector<double> numeric_data;
        std::vector<std::string> values;
        boost::split(values, data, boost::is_any_of(","));

        for (const auto& val : values) {
            try {
                numeric_data.push_back(std::stod(val));
            } catch (const std::invalid_argument& e) {
                throw std::invalid_argument("Input data must be a string with numeric values separated by commas.");
            }
        }

        // 构造输出字符串
        boost::format fmt("{\'data\': \'%1%\', \'shade\': %2%, \'color\': \'%3%\'}");
        fmt % data % (shade ? "True" : "False") % color;

        return fmt.str();
    }
};