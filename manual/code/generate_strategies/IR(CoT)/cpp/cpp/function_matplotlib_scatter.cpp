
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
        
        if (x_values.empty() || y_values.empty())
        {
            return "Error: x_values and y_values cannot be empty.";
        }

        
        std::vector<double> x_list;
        std::vector<double> y_list;

        std::stringstream x_ss(x_values);
        std::string item;

        try
        {
            while (std::getline(x_ss, item, ','))
            {
                
                if (item.find('\'') != std::string::npos ||
                    item.find('\"') != std::string::npos)
                {
                    return "Error: All x and y values must be numbers.";
                }

                
                item.erase(0, item.find_first_not_of(" \t\r\n"));
                item.erase(item.find_last_not_of(" \t\r\n") + 1);

                x_list.push_back(std::stod(item));
            }

            std::stringstream y_ss(y_values);
            while (std::getline(y_ss, item, ','))
            {
                
                if (item.find('\'') != std::string::npos ||
                    item.find('\"') != std::string::npos)
                {
                    return "Error: All x and y values must be numbers.";
                }

                
                item.erase(0, item.find_first_not_of(" \t\r\n"));
                item.erase(item.find_last_not_of(" \t\r\n") + 1);

                y_list.push_back(std::stod(item));
            }
        }
        catch (const std::exception &)
        {
            return "Error: All x and y values must be numbers.";
        }

        
        if (x_list.size() != y_list.size())
        {
            return "Error: x_values and y_values must have the same length.";
        }

        
        try
        {
            
            plstream pls;

            
            pls.sdev("pngcairo");
            pls.sfnam("scatter_plot.png");

            
            pls.setopt("quiet", "");
            pls.setopt("nobuffered", "");

            
            pls.init();

            
            pls.font(2);
            pls.scol0(0, 255, 255, 255); 
            pls.scol0(1, 0, 0, 0);       
            pls.scol0(2, 0, 0, 255);     
            pls.scol0(15, 0, 0, 0);      

            
            double x_min = *std::min_element(x_list.begin(), x_list.end());
            double x_max = *std::max_element(x_list.begin(), x_list.end());
            double y_min = *std::min_element(y_list.begin(), y_list.end());
            double y_max = *std::max_element(y_list.begin(), y_list.end());

            
            double x_padding = (x_max - x_min) * 0.1;
            double y_padding = (y_max - y_min) * 0.1;

            if (x_padding == 0)
                x_padding = 1.0; 
            if (y_padding == 0)
                y_padding = 1.0; 

            x_min -= x_padding;
            x_max += x_padding;
            y_min -= y_padding;
            y_max += y_padding;

            
            pls.env(x_min, x_max, y_min, y_max, 0, 0);

            
            pls.lab("X Values", "Y Values", title.c_str());

            
            pls.col0(2);      
            pls.schr(0, 0.8); 

            
            std::vector<PLFLT> x_plflt(x_list.begin(), x_list.end());
            std::vector<PLFLT> y_plflt(y_list.begin(), y_list.end());

            
            pls.poin(x_list.size(), x_plflt.data(), y_plflt.data(), 17);

            return "Plot saved as 'scatter_plot.png'.";
        }
        catch (const std::exception &)
        {
            return "Error: Failed to create plot.";
        }
    }
};