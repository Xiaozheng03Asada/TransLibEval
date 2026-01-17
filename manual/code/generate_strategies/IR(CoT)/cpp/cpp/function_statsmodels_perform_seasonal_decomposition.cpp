#include <string>
#include <sstream>
#include <vector>
#include <stdexcept>
#include <numeric>
#include <cmath>
#include <Eigen/Dense>
#include <type_traits>

class SeasonalDecomposition {
public:
    template<typename T>
    std::string perform_seasonal_decomposition(const T &data_input, const std::string &model = "additive", int period = 12) {
        if constexpr (!std::is_same_v<T, std::string>) {
            throw std::invalid_argument("Input data must be a string.");
        } else {
            if(model != "additive" && model != "multiplicative") {
                throw std::invalid_argument("Model must be 'additive' or 'multiplicative'.");
            }
            if(period <= 0) {
                throw std::invalid_argument("Period must be a positive integer.");
            }
            
            std::vector<double> data;
            std::stringstream ss(data_input);
            std::string token;
            while(std::getline(ss, token, ',')) {
                token.erase(0, token.find_first_not_of(" "));
                token.erase(token.find_last_not_of(" ") + 1);
                if(!token.empty()){
                    data.push_back(std::stod(token));
                }
            }
            if(data.empty()){
                throw std::invalid_argument("Data string is empty or invalid.");
            }
            
            Eigen::VectorXd data_array(data.size());
            for (size_t i = 0; i < data.size(); i++) {
                data_array(static_cast<int>(i)) = data[i];
            }
            
            double trend = data_array.mean();
            
            Eigen::VectorXd seasonal(data_array.size());
            for (int i = 0; i < data_array.size(); i++) {
                seasonal(i) = std::sin((2 * M_PI * i) / period);
            }
            
            Eigen::VectorXd residual = data_array.array() - trend - seasonal.array();
            
            std::ostringstream oss;
            oss << "Trend: " << trend << ", Seasonal: ";
            for (int i = 0; i < seasonal.size(); i++) {
                oss << seasonal(i);
                if(i < seasonal.size() - 1)
                    oss << ", ";
            }
            oss << ", Residual: ";
            for (int i = 0; i < residual.size(); i++) {
                oss << residual(i);
                if(i < residual.size() - 1)
                    oss << ", ";
            }
            oss << ", Observed: ";
            for (int i = 0; i < data_array.size(); i++) {
                oss << data_array(i);
                if(i < data_array.size() - 1)
                    oss << ", ";
            }
            return oss.str();
        }
    }
};