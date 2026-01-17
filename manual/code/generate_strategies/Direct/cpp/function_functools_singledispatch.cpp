#include <string>
#include <boost/any.hpp>
#include <boost/algorithm/string.hpp>
#include <stdexcept>
class DataProcessor
{
public:
    boost::any test_singledispatch(const boost::any &data)
    {
        try
        {
            if (data.empty())
            {
                return std::string("NONE");
            }
            else if (data.type() == typeid(int))
            {
                return boost::any_cast<int>(data) * 2;
            }
            else if (data.type() == typeid(std::string))
            {
                std::string str = boost::any_cast<std::string>(data);
                boost::to_upper(str);
                return str;
            }
            else if (data.type() == typeid(double))
            {
                return boost::any_cast<double>(data) * 2;
            }
            else
            {
                throw std::runtime_error("不支持的数据类型");
            }
        }
        catch (const boost::bad_any_cast &)
        {
            throw std::runtime_error("类型转换错误");
        }
    }
};