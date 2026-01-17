#include <string>
#include <cmath>
#include <Eigen/Dense>
#include <sstream>
#include <iomanip>
class CVXPYSumOfSquaresFunction {
public:
    std::string solve_sum_of_squares(const std::string& vector_values_str) {
        try {
            std::stringstream ss(vector_values_str);
            std::string item;
            std::vector<double> values;
    
          
            while (std::getline(ss, item, ',')) {
                values.push_back(std::stod(item));
            }
    
           
            Eigen::Map<Eigen::VectorXd> vector(values.data(), values.size());
            double sum_squares = vector.array().square().sum();
    
         
            std::ostringstream out;
            out << std::scientific << std::setprecision(20);
            out << sum_squares;
            std::string result = out.str();
    
           
            double value = std::stod(result);
            if (std::abs(value) < 1e-10) {
               
                return "0.0";
            }
    
            
            out.str("");
            out.clear();
            out << std::fixed << std::setprecision(15);
            out << value;
            result = out.str();
    
          
            while (result.back() == '0' && result.find('.') != std::string::npos) {
                result.pop_back();
            }
            if (result.back() == '.') {
                result += "0";
            }
    
            return result;
        }
        catch (const std::exception&) {
            throw std::invalid_argument("Invalid input format");
        }
    }};