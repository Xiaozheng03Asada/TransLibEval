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
                                        // 处理未赋值变量的情况
                                        if (x_value.empty() && y_value.empty() && constraint.empty()) {
                                            // 使用Eigen创建变量
                                            Eigen::MatrixXd x = Eigen::MatrixXd::Zero(1,1);
                                            Eigen::MatrixXd y = Eigen::MatrixXd::Zero(1,1);
                                            return "x, y";
                                        }
                                
                                        // 处理x和y值的情况
                                        if (!x_value.empty() && !y_value.empty()) {
                                            try {
                                                double x = std::stod(x_value);
                                                double y = std::stod(y_value);
                                                
                                                // 检查负值
                                                if (x < 0 || y < 0) {
                                                    return "Error: Variable size cannot be negative";
                                                }
                                                
                                                // 格式化输出
                                                std::ostringstream oss;
                                                oss << "x: " << x << ".0, y: " << y << ".0";
                                                return oss.str();
                                            }
                                            catch (const std::exception&) {
                                                return "Error: Invalid value for x or y";
                                            }
                                        }
                                
                                        // 处理约束条件
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