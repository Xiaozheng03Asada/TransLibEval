#include <string>
#include <boost/compute/detail/lru_cache.hpp>
#include <boost/algorithm/string.hpp>
#include <vector>
#include <unordered_map>
class LRUCacheManager
{
public:
    std::string manage_lru_cache(const std::string &cache_size, const std::string &operations)
    {
        if (operations.empty())
        {
            return "";
        }

        int size = std::stoi(cache_size);
        boost::compute::detail::lru_cache<std::string, std::string> cache(size);
        std::vector<std::string> results;

        std::vector<std::string> ops;
        boost::split(ops, operations, boost::is_any_of(";"));

        for (const auto &op : ops)
        {
            std::vector<std::string> parts;
            boost::split(parts, op, boost::is_any_of(","));

            if (parts[0] == "set")
            {
                cache.insert(parts[1], parts[2]);
            }
            else if (parts[0] == "get")
            {
                auto value = cache.get(parts[1]);
                results.push_back(value ? *value : "None");
            }
        }

        return boost::algorithm::join(results, ",");
    }
};