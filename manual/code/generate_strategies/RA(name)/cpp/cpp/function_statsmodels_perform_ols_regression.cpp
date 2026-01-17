#include <string>
#include <sstream>
#include <stdexcept>
#include <vector>
#include <algorithm>
#include <numeric>
#include <Eigen/Dense>
#include <nlohmann/json.hpp> 
#include <cctype>
#include <iterator>

using json = nlohmann::json;

class OLSRegression {
public:
    std::string perform_ols_regression(const std::string &data_input, const std::string &dependent_var = "", const std::string &independent_vars_str = "") {
        json data;
        try {
            data = json::parse(data_input);
        }
        catch (...) {
            throw std::invalid_argument("Invalid JSON format.");
        }
        if (!data.is_object()) {
            throw std::invalid_argument("Data must be a dictionary.");
        }
        if (data.find(dependent_var) == data.end()) {
            throw std::out_of_range("Dependent variable not found.");
        }
        std::vector<double> y_vec;
        try {
            for (const auto &val : data[dependent_var]) {
                if (!val.is_number()) {
                    throw std::invalid_argument("Dependent variable contains non-numeric data.");
                }
                y_vec.push_back(val.get<double>());
            }
        }
        catch (...) {
            throw std::invalid_argument("Dependent variable contains non-numeric data.");
        }
        if (y_vec.size() < 2) {
            throw std::invalid_argument("Insufficient data for regression.");
        }
        int n = static_cast<int>(y_vec.size());
    
        std::vector<std::string> indep_vars;
        {
            std::istringstream iss(independent_vars_str);
            std::string token;
            while (std::getline(iss, token, ',')) {
                token.erase(token.begin(), std::find_if(token.begin(), token.end(), [](unsigned char ch) {
                    return !std::isspace(ch);
                }));
                token.erase(std::find_if(token.rbegin(), token.rend(), [](unsigned char ch) {
                    return !std::isspace(ch);
                }).base(), token.end());
                if (!token.empty()) {
                    indep_vars.push_back(token);
                }
            }
        }
    
        std::vector<std::vector<double>> Xcols;
        for (const auto &var : indep_vars) {
            if (data.find(var) == data.end()) {
                throw std::out_of_range("Independent variable " + var + " not found.");
            }
            std::vector<double> col;
            try {
                for (const auto &val : data[var]) {
                    if (!val.is_number())
                        throw std::invalid_argument("Independent variable contains non-numeric data.");
                    col.push_back(val.get<double>());
                }
            }
            catch (...) {
                throw std::invalid_argument("Independent variable contains non-numeric data.");
            }
            if (col.size() != static_cast<size_t>(n)) {
                throw std::invalid_argument("Inconsistent data length among variables.");
            }
            Xcols.push_back(col);
        }
        int p = static_cast<int>(Xcols.size());
    
        Eigen::MatrixXd X(n, p + 1);
        X.col(0) = Eigen::VectorXd::Ones(n);
        for (int j = 0; j < p; j++) {
            for (int i = 0; i < n; i++) {
                X(i, j + 1) = Xcols[j][i];
            }
        }
        Eigen::VectorXd y(n);
        for (int i = 0; i < n; i++) {
            y(i) = y_vec[i];
        }
    
        Eigen::VectorXd beta = (X.transpose() * X).ldlt().solve(X.transpose() * y);
        Eigen::VectorXd y_hat = X * beta;
        double ss_res = (y - y_hat).squaredNorm();
        double y_mean = y.mean();
        double ss_tot = (y.array() - y_mean).square().sum();
        double r_squared = 1 - ss_res / ss_tot;
    
        std::ostringstream coeffStream;
        coeffStream << "{";
        coeffStream << "\"const\": " << beta(0);
        for (int j = 0; j < p; j++) {
            coeffStream << ", \"" << indep_vars[j] << "\": " << beta(j + 1);
        }
        coeffStream << "}";
    
        std::ostringstream oss;
        oss << "R-squared: " << r_squared << ", Coefficients: " << coeffStream.str();
        return oss.str();
    }
};