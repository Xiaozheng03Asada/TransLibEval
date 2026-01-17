#include <algorithm>
#include <string>
#include <vector>
#include <iomanip>
#include <sstream>

class SortCalculator {
public:
    std::string sort(double value1, double value2, double value3) {
        // 检查输入值是否为默认值，如果是则使用默认值
        if (value1 == 0.0 || value2 == 0.0 || value3 == 0.0) {
            value1 = 10.0;
            value2 = 5.0;
            value3 = 15.0;
        }

        // 创建数组并排序
        std::vector<double> data = {value1, value2, value3};
        std::sort(data.begin(), data.end());

        // 格式化结果字符串
        std::ostringstream oss;
        oss << "Sorted Values: " << std::fixed << std::setprecision(1) << data[0] << ", " << data[1] << ", " << data[2];
        std::string result = oss.str();
        size_t pos = result.find(".0");
        while (pos != std::string::npos) {
            result.erase(pos, 2);
            pos = result.find(".0", pos);
        }
        return result;
    }
};