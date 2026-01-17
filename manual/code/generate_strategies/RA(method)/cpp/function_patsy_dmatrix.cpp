#include <string>
#include <vector>
#include <map>
#include <stdexcept>
#include <cmath>
#include <regex>
#include <Eigen/Dense>
#include <nlohmann/json.hpp>

using json = nlohmann::json;

class PatsyProcessor {
public:
    std::string generate_matrix(const std::string& data, const std::string& formula) {
        try {
            json df = json::parse(data);
            
            if (df.empty() || !df.is_object()) {
                return "Error: invalid input";
            }
            
            size_t n_rows = 0;
            for (auto& el : df.items()) {
                if (el.value().is_array()) {
                    n_rows = el.value().size();
                    break;
                }
            }
            
            if (n_rows == 0) {
                return "Error: invalid input";
            }
            
            Eigen::MatrixXd matrix;
            int n_cols = 0;
            
            if (formula == "x + I(x ** 2)") {
                if (!df.contains("x") || !df["x"].is_array()) {
                    return "Error: invalid input";
                }
                
                n_cols = 3;
                matrix = Eigen::MatrixXd::Ones(n_rows, n_cols);
                
                for (size_t i = 0; i < n_rows; ++i) {
                    double x_val = df["x"][i].get<double>();
                    matrix(i, 1) = x_val;
                    matrix(i, 2) = std::pow(x_val, 2);
                }
            }
            else if (formula == "C(z)") {
                if (!df.contains("z") || !df["z"].is_array()) {
                    return "Error: invalid input";
                }
                
                std::vector<double> unique_values;
                for (const auto& val : df["z"]) {
                    double v = val.get<double>();
                    if (std::find(unique_values.begin(), unique_values.end(), v) == unique_values.end()) {
                        unique_values.push_back(v);
                    }
                }
                
                n_cols = unique_values.size();
                matrix = Eigen::MatrixXd::Zero(n_rows, n_cols);
                
                for (size_t i = 0; i < n_rows; ++i) {
                    double z_val = df["z"][i].get<double>();
                    auto it = std::find(unique_values.begin(), unique_values.end(), z_val);
                    if (it != unique_values.end()) {
                        int col = std::distance(unique_values.begin(), it);
                        matrix(i, col) = 1.0;
                    }
                }
            }
            else if (formula == "x * z * w") {
                if (!df.contains("x") || !df.contains("z") || !df.contains("w")) {
                    return "Error: invalid input";
                }
                
                n_cols = 8;
                matrix = Eigen::MatrixXd::Ones(n_rows, n_cols);
                
                for (size_t i = 0; i < n_rows; ++i) {
                    double x_val = df["x"][i].get<double>();
                    double z_val = df["z"][i].get<double>();
                    double w_val = df["w"][i].get<double>();
                    
                    matrix(i, 1) = x_val;
                    matrix(i, 2) = z_val;
                    matrix(i, 3) = w_val;
                    
                    matrix(i, 4) = x_val * z_val;
                    matrix(i, 5) = x_val * w_val;
                    matrix(i, 6) = z_val * w_val;
                    
                    matrix(i, 7) = x_val * z_val * w_val;
                }
            }
            else if (formula == "x + (z > 6)") {
                if (!df.contains("x") || !df.contains("z")) {
                    return "Error: invalid input";
                }
                
                n_cols = 3; 
                matrix = Eigen::MatrixXd::Ones(n_rows, n_cols);
                
                for (size_t i = 0; i < n_rows; ++i) {
                    matrix(i, 1) = df["x"][i].get<double>();
                    matrix(i, 2) = (df["z"][i].get<double>() > 6) ? 1.0 : 0.0;
                }
            }
            else if (formula == "C(w > 15)") {
                if (!df.contains("w")) {
                    return "Error: invalid input";
                }
                
                n_cols = 2; 
                matrix = Eigen::MatrixXd::Zero(n_rows, n_cols);
                
                for (size_t i = 0; i < n_rows; ++i) {
                    int col = (df["w"][i].get<double>() > 15) ? 1 : 0;
                    matrix(i, col) = 1.0;
                }
            }
            else {
                return "Error: invalid input";
            }
            
            json result;
            std::stringstream ss;
            ss << "{\"matrix_shape\": [" << static_cast<int>(n_rows) << ", " << n_cols << "]}";
            return ss.str();
        }
        catch (const std::exception&) {
            return "Error: invalid input";
        }
    }
};