#include <string>
#include <vector>
#include <unordered_set>
#include "absl/strings/str_split.h"
#include "absl/strings/str_join.h"

class ListProcessor {
public:
    std::string process_list(const std::string &input_str) {
        std::vector<std::string> elements = absl::StrSplit(input_str, ',');
        std::vector<std::string> unique_elements;
        std::unordered_set<std::string> seen;
        for (const auto &item : elements) {
            if (seen.find(item) == seen.end()) {
                unique_elements.push_back(item);
                seen.insert(item);
            }
        }
        return absl::StrJoin(unique_elements, ",");
    }
};

