
#include <string>
#include <plplot/plstream.h>
#include <vector>
#include <regex>
#include <sstream>
#include <iostream>
class Plotter
{
public:
    std::string create_bar_chart(const std::string &x_values,
                                 const std::string &y_values,
                                 const std::string &title = "Bar Chart")
    {
        try
        {
            std::regex pattern(R"(\[(.*)\])");
            std::smatch match;

            std::vector<std::string> x_list;
            std::vector<PLFLT> y_list;

            
            if (std::regex_match(x_values, match, pattern))
            {
                std::stringstream ss(match[1]);
                std::string item;
                while (std::getline(ss, item, ','))
                {
                    item = std::regex_replace(item, std::regex("\""), "");
                    item = std::regex_replace(item, std::regex("^\\s+|\\s+$"), "");
                    if (!item.empty())
                    {
                        x_list.push_back(item);
                    }
                }
            }

            
            if (std::regex_match(y_values, match, pattern))
            {
                std::stringstream ss(match[1]);
                std::string item;
                while (std::getline(ss, item, ','))
                {
                    item = std::regex_replace(item, std::regex("^\\s+|\\s+$"), "");
                    if (!item.empty())
                    {
                        y_list.push_back(std::stod(item));
                    }
                }
            }

            if (x_list.empty() || y_list.empty())
            {
                return "Error: Invalid input format.";
            }

            if (x_list.size() != y_list.size())
            {
                return "Error: x_values and y_values must have the same length.";
            }

            
            plstream pls;
            pls.sdev("pngcairo"); 
            pls.sfnam("bar_chart.png");

            
            pls.setopt("destroy", "0");
            pls.init();

           
            pls.col0(1);
            pls.env(0., x_list.size(), 0., *std::max_element(y_list.begin(), y_list.end()), 0, 0);
            pls.lab("X", "Y", title.c_str());

            
            for (size_t i = 0; i < x_list.size(); ++i)
            {
                PLFLT x[] = {i + 0.25, i + 0.75, i + 0.75, i + 0.25};
                PLFLT y[] = {0, 0, y_list[i], y_list[i]};
                pls.fill(4, x, y);
                pls.ptex(i + 0.5, -0.5, 0.0, 0.0, 0.5, x_list[i].c_str());
            }

            return "Plot displayed.";
        }
        catch (const std::exception &)
        {
            return "Error: Invalid input format.";
        }
    }
};