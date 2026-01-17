
#include <string>
#include <plplot/plstream.h>
#include <sstream>
#include <vector>
#include <algorithm>

class BarPlotGenerator {
public:
    static std::string create_bar_plot(const std::string& categories, const std::string& values){
        
        if (categories.empty() || values.empty()) {
            return "Error: Categories and values cannot be empty.";
        }
        
        
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
                
                size_t pos;
                double val = std::stod(item, &pos);
                value_list.push_back(val);
            } catch (const std::exception&) {
                
                value_list.push_back(0.0);
            }
        }
        
        
        if (category_list.size() != value_list.size()) {
            return "Error: Categories and values must have the same length.";
        }
        
        try {
            
            plstream pls;
            
            
            pls.sdev("pngcairo");
            pls.sfnam("bar_plot.png");
            
            
            pls.setopt("quiet", "");
            pls.setopt("nobuffered", "");
            
            
            pls.init();
            
            
            pls.font(2);
            pls.scol0(0, 255, 255, 255); 
            pls.scol0(1, 0, 0, 255);     
            pls.scol0(2, 135, 206, 235); 
            
            
            const double x_min = -1.0;
            const double x_max = category_list.size();
            const double y_min = 0.0;
            const double y_max = *std::max_element(value_list.begin(), value_list.end()) * 1.2;
            
            pls.env(x_min, x_max, y_min, y_max, 0, 0);
            
            
            pls.lab("Categories", "Values", "Bar Plot");
            
            
            std::vector<PLFLT> x(category_list.size());
            std::vector<PLFLT> y(value_list.size());
            
            for (size_t i = 0; i < category_list.size(); i++) {
                x[i] = i;
                y[i] = value_list[i];
                
                
                PLFLT x1[4] = {static_cast<PLFLT>(i-0.25), static_cast<PLFLT>(i+0.25), 
                               static_cast<PLFLT>(i+0.25), static_cast<PLFLT>(i-0.25)};
                PLFLT y1[4] = {0.0, 0.0, y[i], y[i]};
                
                pls.col0(2); 
                pls.fill(4, x1, y1);
                
                
                pls.col0(1);
                pls.ptex(i, -0.1 * y_max, 0.0, 0.0, 0.5, category_list[i].c_str());
            }
            
            return "Plot saved as 'bar_plot.png'.";
        }
        catch (const std::exception&) {
            return "Error: Failed to create plot.";
        }
    }
};