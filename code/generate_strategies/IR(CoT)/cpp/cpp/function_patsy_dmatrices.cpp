#include <Eigen/Dense>
#include <nlohmann/json.hpp>
#include <string>
#include <sstream>
#include <stdexcept>
#include <vector>
#include <algorithm>
#include <cmath>

using json = nlohmann::json;

class PatsyProcessor {
public:
    std::string process_formula(const std::string& data, const std::string& formula) {
        try {
            json df = json::parse(data);

            if (!df.contains("y") || !df["y"].is_array()) {
                return "Error: invalid input";
            }

            size_t n = df["y"].size();

            size_t pos = formula.find("~");
            if (pos == std::string::npos) {
                return "Error: invalid input";
            }
            std::string lhs = formula.substr(0, pos);
            std::string rhs = formula.substr(pos + 1);

            auto trim = [](std::string s) -> std::string {
                s.erase(s.begin(), std::find_if(s.begin(), s.end(), [](unsigned char ch) { return !std::isspace(ch); }));
                s.erase(std::find_if(s.rbegin(), s.rend(), [](unsigned char ch) { return !std::isspace(ch); }).base(), s.end());
                return s;
            };
            lhs = trim(lhs);
            rhs = trim(rhs);

            if (lhs != "y") {
                return "Error: invalid input";
            }

            Eigen::MatrixXd X;
            int X_cols = 0;

            if (rhs == "x1 + x2") {
                if (!df.contains("x1") || !df.contains("x2")) {
                    return "Error: invalid input";
                }
                X_cols = 3;
                X = Eigen::MatrixXd::Ones(n, X_cols); 
                for (size_t i = 0; i < n; ++i) {
                    X(i, 1) = df["x1"][i].get<double>();
                    X(i, 2) = df["x2"][i].get<double>();
                }
            } else if (rhs == "x1 * x2") {
                if (!df.contains("x1") || !df.contains("x2")) {
                    return "Error: invalid input";
                }
                X_cols = 4;
                X = Eigen::MatrixXd::Ones(n, X_cols);
                for (size_t i = 0; i < n; ++i) {
                    X(i, 1) = df["x1"][i].get<double>();
                    X(i, 2) = df["x2"][i].get<double>();
                    X(i, 3) = df["x1"][i].get<double>() * df["x2"][i].get<double>(); 
                }
            } else if (rhs == "x1 + I(x1 ** 2) + I(x1 ** 3)") {
                if (!df.contains("x1")) {
                    return "Error: invalid input";
                }
                X_cols = 4;
                X = Eigen::MatrixXd::Ones(n, X_cols);
                for (size_t i = 0; i < n; ++i) {
                    double x1_val = df["x1"][i].get<double>();
                    X(i, 1) = x1_val;
                    X(i, 2) = std::pow(x1_val, 2);
                    X(i, 3) = std::pow(x1_val, 3);
                }
            } else {
                return "Error: invalid input";
            }

            json result;
            result["y_shape"] = { static_cast<int>(n), 1 };
            result["X_shape"] = { static_cast<int>(n), X_cols };

            return result.dump();
        } catch (...) {
            return "Error: invalid input";
        }
    }
};