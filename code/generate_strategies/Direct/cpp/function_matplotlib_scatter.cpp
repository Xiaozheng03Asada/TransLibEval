
#include <string>
#include <plplot/plstream.h>
#include <sstream>
#include <vector>
#include <algorithm>

class ScatterPlotGenerator
{
public:
    static std::string create_scatter_plot(const std::string &x_values,
                                           const std::string &y_values,
                                           const std::string &title = "Scatter Plot")
    {
        // 检查输入是否为空
        if (x_values.empty() || y_values.empty())
        {
            return "Error: x_values and y_values cannot be empty.";
        }

        // 解析x_values和y_values
        std::vector<double> x_list;
        std::vector<double> y_list;

        std::stringstream x_ss(x_values);
        std::string item;

        try
        {
            while (std::getline(x_ss, item, ','))
            {
                // 检查是否包含引号，如果有则视为错误
                if (item.find('\'') != std::string::npos ||
                    item.find('\"') != std::string::npos)
                {
                    return "Error: All x and y values must be numbers.";
                }

                // 去除空白字符
                item.erase(0, item.find_first_not_of(" \t\r\n"));
                item.erase(item.find_last_not_of(" \t\r\n") + 1);

                x_list.push_back(std::stod(item));
            }

            std::stringstream y_ss(y_values);
            while (std::getline(y_ss, item, ','))
            {
                // 检查是否包含引号，如果有则视为错误
                if (item.find('\'') != std::string::npos ||
                    item.find('\"') != std::string::npos)
                {
                    return "Error: All x and y values must be numbers.";
                }

                // 去除空白字符
                item.erase(0, item.find_first_not_of(" \t\r\n"));
                item.erase(item.find_last_not_of(" \t\r\n") + 1);

                y_list.push_back(std::stod(item));
            }
        }
        catch (const std::exception &)
        {
            return "Error: All x and y values must be numbers.";
        }

        // 检查长度是否匹配
        if (x_list.size() != y_list.size())
        {
            return "Error: x_values and y_values must have the same length.";
        }

        // 其余代码保持不变...
        try
        {
            // 创建PLplot对象
            plstream pls;

            // 设置输出文件格式为PNG
            pls.sdev("pngcairo");
            pls.sfnam("scatter_plot.png");

            // 禁止PLplot提示符
            pls.setopt("quiet", "");
            pls.setopt("nobuffered", "");

            // 初始化图表
            pls.init();

            // 设置字体和颜色
            pls.font(2);
            pls.scol0(0, 255, 255, 255); // 白色背景
            pls.scol0(1, 0, 0, 0);       // 黑色文字
            pls.scol0(2, 0, 0, 255);     // 蓝色点
            pls.scol0(15, 0, 0, 0);      // 黑色

            // 找出数据范围
            double x_min = *std::min_element(x_list.begin(), x_list.end());
            double x_max = *std::max_element(x_list.begin(), x_list.end());
            double y_min = *std::min_element(y_list.begin(), y_list.end());
            double y_max = *std::max_element(y_list.begin(), y_list.end());

            // 为轴添加一些边距
            double x_padding = (x_max - x_min) * 0.1;
            double y_padding = (y_max - y_min) * 0.1;

            if (x_padding == 0)
                x_padding = 1.0; // 如果所有x值相同
            if (y_padding == 0)
                y_padding = 1.0; // 如果所有y值相同

            x_min -= x_padding;
            x_max += x_padding;
            y_min -= y_padding;
            y_max += y_padding;

            // 设置图表区域
            pls.env(x_min, x_max, y_min, y_max, 0, 0);

            // 设置标签和标题
            pls.lab("X Values", "Y Values", title.c_str());

            // 绘制散点图
            pls.col0(2);      // 蓝色点
            pls.schr(0, 0.8); // 设置点的大小

            // 转换为PLFLT数组
            std::vector<PLFLT> x_plflt(x_list.begin(), x_list.end());
            std::vector<PLFLT> y_plflt(y_list.begin(), y_list.end());

            // 使用符号17（圆形）
            pls.poin(x_list.size(), x_plflt.data(), y_plflt.data(), 17);

            return "Plot saved as 'scatter_plot.png'.";
        }
        catch (const std::exception &)
        {
            return "Error: Failed to create plot.";
        }
    }
};