
#include <string>
#include <plplot/plstream.h>
#include <sstream>
#include <vector>
#include <cmath>
class PieChartGenerator {
public:
    static std::string create_pie_chart(const std::string& labels, const std::string& sizes, const std::string& title = "Pie Chart"){ // 检查输入是否为空
        if (labels.empty() || sizes.empty()) {
            return "Error: labels and sizes cannot be empty.";
        }
        
        // 解析标签和大小
        std::vector<std::string> label_list;
        std::vector<double> size_list;
        
        std::stringstream label_ss(labels);
        std::string item;
        while (std::getline(label_ss, item, ',')) {
            label_list.push_back(item);
        }
        
        std::stringstream size_ss(sizes);
        while (std::getline(size_ss, item, ',')) {
            try {
                double val = std::stod(item);
                if (val < 0) {
                    return "Error: All sizes must be non-negative numbers.";
                }
                size_list.push_back(val);
            } catch (const std::exception&) {
                return "Error: All sizes must be non-negative numbers.";
            }
        }
        
        // 检查长度是否匹配
        if (label_list.size() != size_list.size()) {
            return "Error: labels and sizes must have the same length.";
        }
        
        try {
            // 创建PLplot对象
            plstream pls;
            
            // 设置输出文件格式为PNG
            pls.sdev("pngcairo");
            pls.sfnam("pie_chart.png");
            
            // 禁止PLplot提示符
            pls.setopt("quiet", "");
            pls.setopt("nobuffered", "");
            
            // 初始化图表
            pls.init();
            
            // 设置字体和颜色
            pls.font(2);
            pls.scol0(0, 255, 255, 255); // 白色背景
            
            // 设置图表区域
            const double x_min = -1.5;
            const double x_max = 1.5;
            const double y_min = -1.5;
            const double y_max = 1.5;
            
            pls.env(x_min, x_max, y_min, y_max, 1, -2); // 正方形，取消边框
            
            // 计算总和
            double total = 0.0;
            for (double size : size_list) {
                total += size;
            }
            
            // 绘制饼图
            double start_angle = 0.0;
            const double radius = 1.0;
            const int segments = 72;
            
            for (size_t i = 0; i < size_list.size(); i++) {
                double fraction = size_list[i] / total;
                double end_angle = start_angle + fraction * 2.0 * M_PI;
                
                // 定义颜色，循环使用一组预定义的颜色
                int color_idx = (i % 8) + 1;
                switch (color_idx) {
                    case 1: pls.scol0(color_idx, 31, 119, 180); break;  // 蓝色
                    case 2: pls.scol0(color_idx, 255, 127, 14); break;  // 橙色
                    case 3: pls.scol0(color_idx, 44, 160, 44); break;   // 绿色
                    case 4: pls.scol0(color_idx, 214, 39, 40); break;   // 红色
                    case 5: pls.scol0(color_idx, 148, 103, 189); break; // 紫色
                    case 6: pls.scol0(color_idx, 140, 86, 75); break;   // 棕色
                    case 7: pls.scol0(color_idx, 227, 119, 194); break; // 粉色
                    case 8: pls.scol0(color_idx, 127, 127, 127); break; // 灰色
                }
                
                // 绘制扇形
                std::vector<PLFLT> x(segments), y(segments);
                pls.col0(color_idx);
                
                for (int j = 0; j < segments; j++) {
                    double angle = start_angle + (j * (end_angle - start_angle)) / (segments - 1);
                    x[j] = radius * cos(angle);
                    y[j] = radius * sin(angle);
                }
                
                x.push_back(0);  // 中心点
                y.push_back(0);  // 中心点
                
                pls.fill(x.size(), x.data(), y.data());
                
                // 添加标签
                double label_angle = (start_angle + end_angle) / 2;
                double label_x = 0.6 * radius * cos(label_angle);
                double label_y = 0.6 * radius * sin(label_angle);
                
                // 添加百分比标签
                char percentage[20];
                snprintf(percentage, sizeof(percentage), "%s: %.1f%%", label_list[i].c_str(), fraction * 100);
                
                pls.col0(15);  // 黑色文字
                pls.ptex(label_x, label_y, 0.0, 0.0, 0.5, percentage);
                
                start_angle = end_angle;
            }
            
            // 添加标题
            pls.col0(15);  // 黑色文字
            pls.ptex(0.0, 1.3, 0.0, 0.0, 0.5, title.c_str());
            
            return "Pie chart saved as 'pie_chart.png'.";
        }
        catch (const std::exception&) {
            return "Error: Failed to create pie chart.";
        }
    }
};