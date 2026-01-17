#include <string>
#include <set>
#include <sstream>
#include <vector>
#include <boost/algorithm/string.hpp>

class OneHotEncode {
public:
    std::string onehot_encode(const std::string& transaction) {
        if (transaction.empty()) {
            return "";
        }

        std::vector<std::string> items;
        boost::split(items, transaction, boost::is_any_of(" ,"), boost::token_compress_on);

        std::set<std::string> unique_items(items.begin(), items.end());
        unique_items.erase(""); 

        std::ostringstream result;
        for (auto it = unique_items.begin(); it != unique_items.end(); ++it) {
            if (it != unique_items.begin()) {
                result << ", ";
            }
            result << *it;
        }

        return result.str();
    }
};