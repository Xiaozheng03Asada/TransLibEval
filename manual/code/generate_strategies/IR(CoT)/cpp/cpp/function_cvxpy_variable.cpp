#include <string>
#include <Eigen/Dense>
#include <sstream>
#include <stdexcept>
class CVXPYVariableFunction {
public:
    std::string process_variables(const std::string& x_value = "", 
                                const std::string& y_value = "", 
                                const std::string& constraint = ""){
    
                                    try {
                                        
                                        if (x_value.empty() && y_value.empty() && constraint.empty()) {
                                            
                                            Eigen::MatrixXd x = Eigen::MatrixXd::Zero(1,1);
                                            Eigen::MatrixXd y = Eigen::MatrixXd::Zero(1,1);
                                            return "x, y";
                                        }
                                
                                        
                                        if (!x_value.empty() && !y_value.empty()) {
                                            try {
                                                double x = std::stod(x_value);
                                                double y = std::stod(y_value);
                                                
                                                
                                                if (x < 0 || y < 0) {
                                                    return "Error: Variable size cannot be negative";
                                                }
                                                
                                                
                                                std::ostringstream oss;
                                                oss << "x: " << x << ".0, y: " << y << ".0";
                                                return oss.str();
                                            }
                                            catch (const std::exception&) {
                                                return "Error: Invalid value for x or y";
                                            }
                                        }
                                
                                        
                                        if (!constraint.empty()) {
                                            return "Error: Invalid variable constraint";
                                        }
                                
                                        return "Error: Invalid input";
                                    }
                                    catch (const std::exception&) {
                                        return "Error: Unknown error occurred";
                                    }
                                }
};