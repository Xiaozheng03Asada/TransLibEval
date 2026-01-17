#include <string>
#include <Eigen/Dense>
#include <regex>
#include <sstream>
#include <limits>
class CVXPYMaxFunction {
public:
    std::string compute_max_value(const std::string& vector){
        try {
          
            std::string cleaned = vector.substr(1, vector.length() - 2);
            std::vector<double> values;
            
            std::stringstream ss(cleaned);
            std::string item;
            
            while (std::getline(ss, item, ',')) {
                
                item.erase(std::remove_if(item.begin(), item.end(), ::isspace), item.end());
                
                
                if (item == "nan") {
                    throw std::invalid_argument("Input contains NaN values");
                }
                
                values.push_back(std::stod(item));
            }
    
            if (values.empty()) {
                throw std::invalid_argument("Invalid input format.");
            }
    
           
            Eigen::Map<Eigen::VectorXd> eigen_vector(values.data(), values.size());
            double max_value = eigen_vector.maxCoeff();
    
            
            std::ostringstream out;
            out.precision(std::numeric_limits<double>::digits10);
            out << max_value;
            std::string result = out.str();
            
         
            if (result.find('.') == std::string::npos) {
                result += ".0";
            }
            
            return result;
        }
        catch (const std::exception& e) {
            throw std::invalid_argument("Invalid input format.");
        }
    }
};

