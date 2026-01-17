#include <Eigen/Dense>
#include <stdexcept>
#include <string>
#include <sstream>
#include <vector>
#include <cmath>

class MeanSquaredErrorCalculator {
public:
    std::string calculate(const std::string& X_str, const std::string& y_str) {
        std::vector<double> X;
        std::vector<double> y;

        // Parse X_str
        std::stringstream ss_X(X_str);
        std::string item;
        while (std::getline(ss_X, item, ',')) {
            X.push_back(std::stod(item));
        }

        // Parse y_str
        std::stringstream ss_y(y_str);
        while (std::getline(ss_y, item, ',')) {
            y.push_back(std::stod(item));
        }

        if (X.size() != y.size()) {
            throw std::invalid_argument("The number of samples in X and y must be the same.");
        }

        Eigen::VectorXd X_vector = Eigen::Map<Eigen::VectorXd>(X.data(), X.size());
        Eigen::VectorXd y_vector = Eigen::Map<Eigen::VectorXd>(y.data(), y.size());

        // Add a column of ones to X_vector for the intercept term
        Eigen::MatrixXd X_matrix(X.size(), 2);
        X_matrix.col(0) = Eigen::VectorXd::Ones(X.size());
        X_matrix.col(1) = X_vector;

        // Perform linear regression using normal equation
        Eigen::VectorXd weights = (X_matrix.transpose() * X_matrix).ldlt().solve(X_matrix.transpose() * y_vector);

        Eigen::VectorXd y_pred = X_matrix * weights;

        double mse = (y_vector - y_pred).squaredNorm() / y_vector.size();
        return std::to_string(mse);
    }
};