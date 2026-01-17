#include <cmath>
#include <string>
#include <vector>
#include <algorithm>

class StandardizeData {
public:
    std::string standardize(double valueA, double valueB) {
        if (std::isnan(valueA) || std::isnan(valueB)) {
            return "A: None, B: None";
        }

        // 计算均值和标准差
        double meanA = valueA;
        double stdA = 0.0;
        double meanB = valueB;
        double stdB = 0.0;

        // 标准化计算
        double standardizedA = (stdA != 0) ? (valueA - meanA) / stdA : 0;
        double standardizedB = (stdB != 0) ? (valueB - meanB) / stdB : 0;

        // 处理可能的NaN情况
        standardizedA = std::isnan(standardizedA) ? 0 : standardizedA;
        standardizedB = std::isnan(standardizedB) ? 0 : standardizedB;

        char buffer[100];
        snprintf(buffer, sizeof(buffer), "A: %f, B: %f", standardizedA, standardizedB);
        return std::string(buffer);
    }
};