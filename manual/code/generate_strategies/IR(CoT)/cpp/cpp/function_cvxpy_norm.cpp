#include <string>
#include <Eigen/Dense>
#include <regex>
#include <sstream>
#include <limits>
class CVXPYNormFunction {
public:
    std::string compute_norm(const std::string& vector, int p = 2){
        try {
            
            std::string cleaned = vector.substr(1, vector.length() - 2);
            std::vector<double> values;
            
            std::stringstream ss(cleaned);
            std::string item;
            
            while (std::getline(ss, item, ',')) {
                item.erase(std::remove_if(item.begin(), item.end(), ::isspace), item.end());
                values.push_back(std::stod(item));
            }
    
            if (values.empty()) {
                throw std::invalid_argument("Invalid input format.");
            }
    
            
            Eigen::Map<Eigen::VectorXd> eigen_vector(values.data(), values.size());
            double norm_value;
    
            if (p == 2) {
                norm_value = eigen_vector.norm();
            } else if (p == 1) {
                norm_value = eigen_vector.lpNorm<1>();
            } else if (p == std::numeric_limits<int>::max()) { 
                norm_value = eigen_vector.lpNorm<Eigen::Infinity>();
            } else {
                throw std::invalid_argument("Unsupported norm type");
            }
    
            
            std::ostringstream out;
            out.precision(std::numeric_limits<double>::digits10);
            out << norm_value;
            std::string result = out.str();
            
            
            if (result.find('.') == std::string::npos) {
                result += ".0";
            }
            
            return result;
        }
        catch (const std::exception&) {
            throw std::invalid_argument("Invalid input format.");
        }
    }
};