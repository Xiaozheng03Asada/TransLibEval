#include <string>
#include <boost/any.hpp>
#include <boost/algorithm/string.hpp>
#include <algorithm>
#include <map>
#include <vector>
class PermutationsProcessor
{
public:
    std::string get_permutations(const boost::any &data)
    {
        try
        {
            std::string input_str;
            if (data.type() == typeid(std::string))
            {
                input_str = boost::any_cast<std::string>(data);
            }
            else if (data.type() == typeid(const char *))
            {
                input_str = boost::any_cast<const char *>(data);
            }
            else
            {
                throw std::invalid_argument("Input data must be a string.");
            }

            if (input_str.empty())
            {
                throw std::invalid_argument("Input string cannot be empty");
            }

            std::vector<std::string> perms;
            std::string current = input_str;

            // 生成所有排列（包括重复项），使用lambda递归
            std::function<void(std::string &, size_t)> generate;
            generate = [&perms, &generate](std::string &str, size_t left)
            {
                if (left == str.length())
                {
                    perms.push_back(str);
                    return;
                }
                for (size_t i = left; i < str.length(); i++)
                {
                    std::swap(str[left], str[i]);
                    generate(str, left + 1);
                    std::swap(str[left], str[i]); // 回溯
                }
            };
            generate(current, 0);

            // 将排列按字典序排序以匹配预期顺序
            std::sort(perms.begin(), perms.end());

            // 统计各排列出现的次数
            std::map<std::string, int> freq;
            for (const auto &perm : perms)
            {
                freq[perm]++;
            }

            // 构建结果字符串
            std::string perms_str = boost::algorithm::join(perms, ", ");
            std::string freq_str;
            bool first = true;
            for (const auto &pair : freq)
            {
                if (!first)
                {
                    freq_str += ", ";
                }
                freq_str += pair.first + ": " + std::to_string(pair.second);
                first = false;
            }

            return perms_str + "\n" + freq_str;
        }
        catch (const boost::bad_any_cast &)
        {
            throw std::invalid_argument("Input data must be a string.");
        }
    }
};