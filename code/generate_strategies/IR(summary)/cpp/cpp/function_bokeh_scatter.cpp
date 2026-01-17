
#include <string>
#include <plplot/plstream.h>
#include <vector>
#include <regex>
#include <sstream>
#include <algorithm>
class Plotter {
public:
    std::string create_scatter_plot(const std::string& x_values,
                                  const std::string& y_values,
                                  const std::string& title = "Scatter Plot"){
                                    try {
                                        std::regex pattern(R"(\[(.*)\])");
                                        std::smatch match;
                                        
                                        std::vector<PLFLT> x_list;
                                        std::vector<PLFLT> y_list;
                                        
                                        
                                        if (std::regex_match(x_values, match, pattern)) {
                                            std::stringstream ss(match[1]);
                                            std::string item;
                                            while (std::getline(ss, item, ',')) {
                                                item = std::regex_replace(item, std::regex("\""), "");
                                                item = std::regex_replace(item, std::regex("^\\s+|\\s+$"), "");
                                                if (!item.empty()) {
                                                    x_list.push_back(std::stod(item));
                                                }
                                            }
                                        }
                                        
                                        
                                        if (std::regex_match(y_values, match, pattern)) {
                                            std::stringstream ss(match[1]);
                                            std::string item;
                                            while (std::getline(ss, item, ',')) {
                                                item = std::regex_replace(item, std::regex("^\\s+|\\s+$"), "");
                                                if (!item.empty()) {
                                                    y_list.push_back(std::stod(item));
                                                }
                                            }
                                        }
                                        
                                        if (x_list.empty() || y_list.empty()) {
                                            return "Error: Invalid input format.";
                                        }
                                        
                                        if (x_list.size() != y_list.size()) {
                                            return "Error: x_values and y_values must have the same length.";
                                        }
                                
                                        
                                        plstream pls;
                                        pls.sdev("pngcairo");
                                        pls.sfnam("scatter_plot.png");
                                        pls.setopt("destroy", "0");
                                        pls.init();
                                        
                                        
                                        PLFLT xmin = *std::min_element(x_list.begin(), x_list.end());
                                        PLFLT xmax = *std::max_element(x_list.begin(), x_list.end());
                                        PLFLT ymin = *std::min_element(y_list.begin(), y_list.end());
                                        PLFLT ymax = *std::max_element(y_list.begin(), y_list.end());
                                        
                                        pls.env(xmin-1, xmax+1, ymin-1, ymax+1, 0, 0);
                                        pls.lab("X Axis", "Y Axis", title.c_str());
                                        
                                        
                                        pls.col0(4);  
                                        pls.schr(0.0, 0.8);  
                                        for (size_t i = 0; i < x_list.size(); ++i) {
                                            pls.poin(1, &x_list[i], &y_list[i], 4);  
                                        }
                                
                                        return "Plot displayed.";
                                    }
                                    catch (const std::exception&) {
                                        return "Error: Invalid input format.";
                                    }
                                }
};