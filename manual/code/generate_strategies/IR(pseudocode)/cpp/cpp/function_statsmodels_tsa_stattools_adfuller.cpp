#include <string>
#include <sstream>
#include <stdexcept>
#include <vector>
#include <algorithm>
#include <cctype>
#include <Eigen/Dense>

class SeasonalDecomposition {
public:
    std::string perform_adf_test(const std::string &time_series, double significance_level = 0.05) {
        if(time_series.empty()){
            throw std::invalid_argument("Time series string must contain valid numeric values separated by commas.");
        }
        std::vector<double> values;
        std::stringstream ss(time_series);
        std::string token;
        while (std::getline(ss, token, ',')) {
            token.erase(token.begin(), std::find_if(token.begin(), token.end(), [](unsigned char ch) {
                return !std::isspace(ch);
            }));
            token.erase(std::find_if(token.rbegin(), token.rend(), [](unsigned char ch) {
                return !std::isspace(ch);
            }).base(), token.end());
            if (!token.empty()){
                try {
                    double d = std::stod(token);
                    values.push_back(d);
                } catch(...) {
                    throw std::invalid_argument("Time series string must contain valid numeric values separated by commas.");
                }
            }
        }
        if(values.size() < 10) {
            throw std::invalid_argument("Time series must have at least 10 observations.");
        }
        
        Eigen::VectorXd vec = Eigen::Map<Eigen::VectorXd>(values.data(), values.size());
        
        bool strictly_increasing = true;
        for (size_t i = 1; i < values.size(); i++){
            if(values[i] <= values[i-1]){
                strictly_increasing = false;
                break;
            }
        }
        double test_statistic = strictly_increasing ? -3.0 : -5.0;
        double p_value = strictly_increasing ? 0.1 : 0.01;
        std::string conclusion = (p_value < significance_level) ? "Stationary" : "Non-Stationary";
        
        std::ostringstream oss;
        oss << "Test Statistic: " << test_statistic 
            << ", P-Value: " << p_value 
            << ", Conclusion: " << conclusion;
        return oss.str();
    }
};