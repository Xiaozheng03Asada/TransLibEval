#include <string>
#include <regex>
#include <nlohmann/json.hpp>
#include <stdexcept>

using json = nlohmann::json;

class PatsyParser {
public:
    std::string parse_formula(const std::string& formula) {
        try {
            if (formula.empty()) {
                return "Error: Formula must be a string.";
            }
            size_t tilde_pos = formula.find("~");
            if (tilde_pos == std::string::npos) {
                return "Error: Failed to parse formula.";
            }
            std::string lhs = formula.substr(0, tilde_pos);
            std::string rhs = formula.substr(tilde_pos + 1);
            auto trim = [](const std::string& s) {
                size_t start = s.find_first_not_of(" \t\n\r");
                size_t end = s.find_last_not_of(" \t\n\r");
                return (start == std::string::npos) ? std::string("") : s.substr(start, end - start + 1);
            };
            lhs = trim(lhs);
            rhs = trim(rhs);
            if (!rhs.empty() && rhs.back() == '+') {
                return "Error: Failed to parse formula.";
            }
            int lhs_terms = lhs.empty() ? 0 : 1;
            bool no_intercept = false;
            std::regex noInt_regex(R"(\-\s*1)");
            if (std::regex_search(rhs, noInt_regex)) {
                no_intercept = true;
                rhs = std::regex_replace(rhs, noInt_regex, "");
                rhs = trim(rhs);
            }
            std::regex term_regex(R"([\w]+)");
            auto terms_begin = std::sregex_iterator(rhs.begin(), rhs.end(), term_regex);
            auto terms_end = std::sregex_iterator();
            int count = std::distance(terms_begin, terms_end);
            int rhs_terms = no_intercept ? count : count + 1;
            json result;
            result["lhs_terms"] = lhs_terms;
            result["rhs_terms"] = rhs_terms;
            return result.dump();  
        } catch (...) {
            return "Error: Failed to parse formula.";
        }
    }
};