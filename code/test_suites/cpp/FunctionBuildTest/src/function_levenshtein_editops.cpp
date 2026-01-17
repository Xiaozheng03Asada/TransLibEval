#include <string>
#include <vector>
#include <algorithm>
#include <stdexcept>

class StringProcessor {
public:
    std::string calculate_editops(const std::string& str1, const std::string& str2) {
        struct EditOperation {
            std::string type;
            int sourcePos;
            int targetPos;

            EditOperation(const std::string& type, int sourcePos, int targetPos)
                : type(type), sourcePos(sourcePos), targetPos(targetPos) {}

            std::string toString() const {
                return "('" + type + "', " + std::to_string(sourcePos) + ", " + std::to_string(targetPos) + ")";
            }
        };

        if (str1.empty() || str2.empty()) {
            return "Error: Both inputs must be strings";
        }

        std::vector<EditOperation> operations;
        int i = 0;
        int j = 0;

        while (i < str1.length() || j < str2.length()) {
            if (i < str1.length() && j < str2.length()) {
                if (str1[i] == str2[j]) {
                    i++;
                    j++;
                    continue;
                }
                if (i + 1 < str1.length() && str1[i + 1] == str2[j]) {
                    operations.emplace_back("delete", i, j);
                    i++;
                    continue;
                }
                if (j + 1 < str2.length() && str1[i] == str2[j + 1]) {
                    operations.emplace_back("insert", i, j);
                    j++;
                    continue;
                }
                operations.emplace_back("replace", i, j);
                i++;
                j++;
            } else if (i < str1.length()) {
                operations.emplace_back("delete", i, j);
                i++;
            } else {
                operations.emplace_back("insert", i, j);
                j++;
            }
        }

        std::string result = "[";
        for (size_t k = 0; k < operations.size(); ++k) {
            result += operations[k].toString();
            if (k < operations.size() - 1) {
                result += ", ";
            }
        }
        result += "]";
        return result;
    }
};