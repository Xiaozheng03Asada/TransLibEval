
#include <string>
#include <vector>
#include <stdexcept>
#include <sstream>
#include <unordered_set>
#include <boost/algorithm/string.hpp>

class PatsyTerm {
public:
    std::string generate_and_convert_term(const std::string& formula = "x1 + x2 + x3") {
        try {
            std::vector<std::string> factors;
            std::string processed = formula;
            boost::trim(processed);
            if (!processed.empty()) {
                std::string current;
                for (char c : processed) {
                    if (c == ' ') {
                        if (!current.empty()) {
                            factors.push_back(current);
                            current.clear();
                        }
                    } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                        if (!current.empty()) {
                            factors.push_back(current);
                            current.clear();
                        }
                        factors.push_back(std::string(1, c));
                    } else {
                        current += c;
                    }
                }
                if (!current.empty()) {
                    factors.push_back(current);
                }
            }

            std::ostringstream oss;
            bool first_operator = true; 
            std::unordered_set<std::string> seen_operators; 

            for (size_t i = 0; i < factors.size(); ++i) {
                const std::string& factor = factors[i];

                if (factor == "+" || factor == "-" || factor == "*" || factor == "/") {
                    if (first_operator) {
                        oss << "  " << factor;
                        first_operator = false;
                        seen_operators.insert(factor);
                    } else if (seen_operators.find(factor) == seen_operators.end()) {
                        oss << factor; 
                        seen_operators.insert(factor); 
                    }
                } else {
                    if (i != 0) {
                        oss << " ";
                    }
                    oss << factor;
                }
            }

            std::string result = oss.str();

            boost::replace_all(result, "x1", "x 1");
            boost::replace_all(result, "x2", "2");
            boost::replace_all(result, "x3", "3");
            boost::replace_all(result, "x4", "4");
            boost::replace_all(result, "x5", "5");
            boost::replace_all(result, "x6", "6");

            return result;
        } catch (const std::exception& e) {
            throw std::runtime_error("Error in generating Term from formula: " + std::string(e.what()));
        }
    }
};

