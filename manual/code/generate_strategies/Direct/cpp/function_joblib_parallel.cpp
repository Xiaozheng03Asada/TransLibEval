
#include <random>
#include <string>
#include <boost/any.hpp>
#include <vector>
#include <omp.h>
#include <sstream>
#include <cmath>
class ParallelProcessor
{
public:
    std::string run_parallel(const std::vector<boost::any> &data_items, int n_jobs = 1)
    {
        if (data_items.empty())
        {
            return "";
        }

        std::vector<double> results;
        results.reserve(data_items.size());

#pragma omp parallel for num_threads(std::abs(n_jobs))
        for (size_t i = 0; i < data_items.size(); ++i)
        {
            try
            {
                double value;
                const boost::any &item = data_items[i];

                if (item.empty())
                    continue;

                if (item.type() == typeid(int))
                {
                    value = static_cast<double>(boost::any_cast<int>(item));
                }
                else if (item.type() == typeid(double))
                {
                    value = boost::any_cast<double>(item);
                }
                else
                {
                    continue;
                }

#pragma omp critical
                results.push_back(value * value);
            }
            catch (...)
            {
                continue;
            }
        }

        if (results.empty())
        {
            return "";
        }

        std::stringstream ss;
        for (size_t i = 0; i < results.size(); ++i)
        {
            if (i > 0)
                ss << ", ";
            ss << results[i];
        }
        return ss.str();
    }
};