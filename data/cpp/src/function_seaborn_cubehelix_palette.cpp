#include <string>
#include <vector>
#include <tuple>
#include <cmath>
#include <sstream>
#include <boost/format.hpp>
#include <boost/math/constants/constants.hpp>
#include <regex>
class SeabornCubehelixPalette {
public:
    std::string generate_cubehelix(int n_colors = 6, double start = 0.5, double rot = -1.5, double gamma = 1.0) {
        if (n_colors <= 0) {
            return "[]";
        }

        std::vector<std::tuple<double, double, double>> palette;
        const double pi = boost::math::constants::pi<double>();

        for (int i = 0; i < n_colors; ++i) {
            double fraction = static_cast<double>(i) / (n_colors - 1);
            double angle = 2.0 * pi * (start / 3.0 + rot * fraction);
            double amplitude = std::pow(fraction, gamma);

            // 计算RGB值
            double h = amplitude * (-0.14861 * std::cos(angle) + 1.78277 * std::sin(angle));
            double s = amplitude * (-0.29227 * std::cos(angle) - 0.90649 * std::sin(angle));
            double v = amplitude * (1.97294 * std::cos(angle));

            // 转换为RGB
            double r = h + 0.5;
            double g = s + 0.5;
            double b = v + 0.5;

            // 确保值在[0,1]范围内
            r = std::max(0.0, std::min(1.0, r));
            g = std::max(0.0, std::min(1.0, g));
            b = std::max(0.0, std::min(1.0, b));

            palette.emplace_back(r, g, b);
        }

        // 构造返回字符串
        std::stringstream ss;
        ss << "[";
        for (size_t i = 0; i < palette.size(); ++i) {
            auto [r, g, b] = palette[i];
            ss << "(" << r << ", " << g << ", " << b << ")";
            if (i < palette.size() - 1) {
                ss << ", ";
            }
        }
        ss << "]";

        return ss.str();
    }
};