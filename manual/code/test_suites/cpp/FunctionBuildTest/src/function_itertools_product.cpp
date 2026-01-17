#include <vector>
#include <string>
#include <sstream>
#include <stdexcept>
#include <algorithm>

class CartesianProductProcessor {
public:
    static std::string test_product(const std::string& input_string) {
        if (input_string.empty()) {
            throw std::invalid_argument("Invalid input");
        }

        std::vector<std::vector<std::string>> lists;
        std::vector<std::string> currentList;
        std::string currentElement;

        for (char ch : input_string) {
            if (ch == ',') {
                if (!currentElement.empty()) {
                    currentList.push_back(currentElement);
                    currentElement.clear();
                }
            } else if (ch == ';') {
                if (!currentElement.empty()) {
                    currentList.push_back(currentElement);
                    currentElement.clear();
                }
                if (!currentList.empty()) {
                    lists.push_back(currentList);
                    currentList.clear();
                } else {
                    throw std::invalid_argument("Invalid input");
                }
            } else {
                currentElement += ch;
            }
        }

        if (!currentElement.empty()) {
            currentList.push_back(currentElement);
        }
        if (!currentList.empty()) {
            lists.push_back(currentList);
        }
        if (lists.empty()) {
            throw std::invalid_argument("Invalid input");
        }

        std::vector<std::vector<std::string>> productResult = cartesianProduct(lists);
        std::vector<std::string> results;
        for (const auto& row : productResult) {
            std::stringstream ss;
            for (const auto& item : row) {
                ss << item;
            }
            results.push_back(ss.str());
        }

        std::stringstream result;
        for (size_t i = 0; i < results.size(); ++i) {
            result << results[i];
            if (i != results.size() - 1) {
                result << ";";
            }
        }

        return result.str();
    }

private:
    static std::vector<std::vector<std::string>> cartesianProduct(const std::vector<std::vector<std::string>>& lists) {
        std::vector<std::vector<std::string>> result;
        std::vector<std::string> current;
        generateCartesianProduct(lists, 0, current, result);
        return result;
    }

    static void generateCartesianProduct(const std::vector<std::vector<std::string>>& lists, size_t depth, std::vector<std::string>& current, std::vector<std::vector<std::string>>& result) {
        if (depth == lists.size()) {
            result.push_back(current);
            return;
        }

        for (const auto& item : lists[depth]) {
            current.push_back(item);
            generateCartesianProduct(lists, depth + 1, current, result);
            current.pop_back();
        }
    }
};