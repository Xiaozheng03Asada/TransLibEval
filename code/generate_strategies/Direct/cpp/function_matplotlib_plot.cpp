#include <string>
#include <plplot/plstream.h>
#include <sstream>
#include <vector>
#include <algorithm>
#include <stdexcept>
class BarPlotGenerator {
public:
    static std::string create_bar_plot(const std::string& categories, const std::string& values) {
        // 检查输入是否为空
        if (categories.empty() || values.empty()) {
            return "Error: Categories and values cannot be empty.";
        }
        
        // 解析categories和values
        std::vector<std::string> category_list;
        std::vector<double> value_list;
        
        std::stringstream cat_ss(categories);
        std::string item;
        while (std::getline(cat_ss, item, ',')) {
            category_list.push_back(item);
        }
        
        std::stringstream val_ss(values);
        while (std::getline(val_ss, item, ',')) {
            try {
                // 检查是否包含引号 - 如果包含则视为错误
                if (item.find('\'') != std::string::npos || 
                    item.find('\"') != std::string::npos) {
                    return "Error: All values must be numbers.";
                }
                
                // 去除空白字符
                item.erase(0, item.find_first_not_of(" \t\r\n"));
                item.erase(item.find_last_not_of(" \t\r\n") + 1);
                
                // 尝试转换为数值
                value_list.push_back(std::stod(item));
            } catch (const std::exception&) {
                return "Error: All values must be numbers.";
            }
        }
        
        // 检查长度是否匹配
        if (category_list.size() != value_list.size()) {
            return "Error: Categories and values must have the same length.";
        }
        
        try {
            // 创建PLplot对象
            plstream pls;
            
            // 设置输出文件格式为PNG
            pls.sdev("pngcairo");
            pls.sfnam("bar_plot.png");
            
            // 禁止PLplot提示符
            pls.setopt("quiet", "");
            pls.setopt("nobuffered", "");
            
            // 初始化图表
            pls.init();
            
            // 设置字体和颜色
            pls.font(2);
            pls.scol0(0, 255, 255, 255); // 白色背景
            pls.scol0(1, 0, 0, 0);       // 黑色文字
            pls.scol0(2, 135, 206, 235); // 天蓝色柱状图 (skyblue)
            
            // 设置图表区域
            const double x_min = -1.0;
            const double x_max = category_list.size();
            const double y_min = 0.0;
            
            // 找出最大值增加20%的边距
            double y_max = *std::max_element(value_list.begin(), value_list.end()) * 1.2;
            if (y_max == 0) y_max = 1.0; // 避免全部为0的情况
            
            pls.env(x_min, x_max, y_min, y_max, 0, 0);
            
            // 设置标签和标题
            pls.lab("Categories", "Values", "Bar Plot");
            
            // 绘制柱状图
            for (size_t i = 0; i < category_list.size(); i++) {
                // 绘制每一个柱子
                PLFLT x[4] = {static_cast<PLFLT>(i-0.25), static_cast<PLFLT>(i+0.25), 
                              static_cast<PLFLT>(i+0.25), static_cast<PLFLT>(i-0.25)};
                PLFLT y[4] = {0.0, 0.0, static_cast<PLFLT>(value_list[i]), static_cast<PLFLT>(value_list[i])};
                
                pls.col0(2); // 设置柱状图颜色为天蓝色
                pls.fill(4, x, y);
                
                // 添加类别标签
                pls.col0(1);
                pls.ptex(i, -0.1 * y_max, 0.0, 0.0, 0.5, category_list[i].c_str());
            }
            
            return "Plot saved as 'bar_plot.png'.";
        }
        catch (const std::exception&) {
            return "Error: Failed to create plot.";
        }
    }};