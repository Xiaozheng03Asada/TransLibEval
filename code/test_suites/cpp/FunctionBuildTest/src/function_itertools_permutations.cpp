#include <iostream>
#include <vector>
#include <map>
#include <algorithm>
#include <sstream>
#include <stdexcept>
#include <set>

class PermutationsProcessor {
public:
    static std::string get_permutations(const std::string& str) {
        // 1. 检查数据类型是否为字符串，否则抛出异常
        if (str.empty()) {
            throw std::invalid_argument("Input data must be a string.");
        }

        // 2. 将字符串拆分为字符列表
        std::vector<char> charList(str.begin(), str.end());

        // 3. 生成所有排列
        std::set<std::string> permsSet;
        std::sort(charList.begin(), charList.end());
        do {
            std::string perm(charList.begin(), charList.end());
            permsSet.insert(perm);
        } while (std::next_permutation(charList.begin(), charList.end()));

        std::vector<std::string> perms(permsSet.begin(), permsSet.end());

        // 4. 对排列进行排序，确保顺序一致
        std::sort(perms.begin(), perms.end());

        // 5. 对排列进行统计计数，用 map 保证 key 顺序稳定
        std::map<std::string, int> permDict;
        for (const auto& perm : perms) {
            permDict[perm]++;
        }

        // 6. 拼接排列列表为以逗号分隔的字符串
        std::ostringstream permsStream;
        for (size_t i = 0; i < perms.size(); ++i) {
            permsStream << perms[i];
            if (i < perms.size() - 1) {
                permsStream << ", ";
            }
        }
        std::string permsStr = permsStream.str();

        // 7. 拼接计数字典为字符串，形式为 "key: value"
        std::ostringstream dictStream;
        size_t i = 0;
        for (const auto& entry : permDict) {
            dictStream << entry.first << ": " << entry.second;
            if (i < permDict.size() - 1) {
                dictStream << ", ";
            }
            i++;
        }
        std::string permDictStr = dictStream.str();

        // 8. 返回两个部分拼接的字符串，中间使用换行符隔开
        return permsStr + "\n" + permDictStr;
    }
};