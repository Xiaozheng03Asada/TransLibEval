#include <string>
#include <boost/any.hpp>
#include <boost/algorithm/string.hpp>
#include <vector>
#include <sstream>
class CartesianProductProcessor {
public:
    std::string test_product(const boost::any& input_string){  try {
        if (input_string.empty()) {
            throw std::invalid_argument("Input cannot be None");
        }

        std::string input;
        if (input_string.type() == typeid(std::string)) {
            input = boost::any_cast<std::string>(input_string);
        }
        else if (input_string.type() == typeid(const char*)) {
            input = boost::any_cast<const char*>(input_string);
        }
        else {
            throw std::invalid_argument("Input must be a string");
        }

        std::vector<std::vector<std::string>> lists;
        std::vector<std::string> current_list;
        std::string current_element;

        for (char c : input) {
            if (c == ',') {
                if (!current_element.empty()) {
                    current_list.push_back(current_element);
                    current_element.clear();
                }
            }
            else if (c == ';') {
                if (!current_element.empty()) {
                    current_list.push_back(current_element);
                    current_element.clear();
                }
                if (current_list.empty()) {
                    throw std::invalid_argument("Empty list in input");
                }
                lists.push_back(current_list);
                current_list.clear();
            }
            else {
                current_element += c;
            }
        }

        if (!current_element.empty()) {
            current_list.push_back(current_element);
        }
        if (!current_list.empty()) {
            lists.push_back(current_list);
        }
        if (lists.empty()) {
            throw std::invalid_argument("No valid input lists");
        }

        std::vector<std::string> product_result;
        std::vector<size_t> indices(lists.size(), 0);
        bool done = false;

        while (!done) {
            std::string combination;
            for (size_t i = 0; i < lists.size(); ++i) {
                combination += lists[i][indices[i]];
            }
            product_result.push_back(combination);

            // 更新索引
            size_t i = lists.size() - 1;
            while (true) {
                ++indices[i];
                if (indices[i] < lists[i].size()) {
                    break;
                }
                indices[i] = 0;
                if (i == 0) {
                    done = true;
                    break;
                }
                --i;
            }
        }

        return boost::algorithm::join(product_result, ";");
    }
    catch (const boost::bad_any_cast&) {
        throw std::invalid_argument("Input must be a string");
    }
}
};