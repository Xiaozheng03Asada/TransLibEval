#include <string>
#include <sstream>
#include <stdexcept>
#include <vector>
#include <nlohmann/json.hpp>
#include <type_traits>
#include <cctype>
#include <iterator>

using json = nlohmann::json;

class ExponentialSmoothingProcessor {
public:
    template<typename T>
    std::string perform_exponential_smoothing(const T &data, int forecast_steps = 0, const std::string &seasonal_type = "", int seasonal_period = 0) {
        if constexpr (!std::is_same_v<T, std::string>) {
            throw std::invalid_argument("Input data must be a string.");
        } else {
            if (forecast_steps < 0) {
                throw std::invalid_argument("Forecast steps must be non-negative.");
            }
            if (seasonal_type != "add" && seasonal_type != "mul") {
                throw std::invalid_argument("Invalid seasonal type.");
            }
            
            json jsonData;
            try {
                jsonData = json::parse(data);
            } catch (...) {
                throw std::invalid_argument("Invalid JSON format.");
            }
            if (!jsonData.is_array()) {
                throw std::invalid_argument("Input data must be a JSON array.");
            }
            
            std::vector<double> series;
            for (const auto &val : jsonData) {
                if (!val.is_number())
                    throw std::invalid_argument("Data contains non-numeric values.");
                series.push_back(val.get<double>());
            }
            if (series.empty()){
                throw std::invalid_argument("Input series is empty.");
            }
            
            double last_val = series.back();
            std::vector<double> forecast(forecast_steps, last_val);
            
            std::ostringstream oss;
            oss << "Forecast: ";
            for (size_t i = 0; i < forecast.size(); ++i) {
                if(i > 0)
                    oss << ", ";
                oss << forecast[i];
            }
            return oss.str();
        }
    }
};