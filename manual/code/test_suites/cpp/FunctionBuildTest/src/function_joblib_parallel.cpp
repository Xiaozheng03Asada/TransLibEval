#include <vector>
#include <future>
#include <thread>
#include <cmath>
#include <algorithm>
#include <string>
#include <sstream>
#include <memory>

class ParallelProcessor {
public:
    std::string run_parallel(int data_1, float data_2, double data_3, int data_4, int data_5, int data_6, int data_7, int data_8, int data_9, int data_10, int n_jobs) {
        std::vector<std::shared_ptr<std::future<int>>> futures;
        std::vector<std::shared_ptr<std::thread>> threads;

        std::vector<std::variant<int, float, double>> dataItems = {data_1, data_2, data_3, data_4, data_5, data_6, data_7, data_8, data_9, data_10};
        std::vector<std::variant<int, float, double>> validDataItems;
        std::copy_if(dataItems.begin(), dataItems.end(), std::back_inserter(validDataItems), [](const auto& x) {
            return x.index() != 0 || std::get<int>(x) != 0;
        });

        for (const auto& x : validDataItems) {
            auto future = std::make_shared<std::future<int>>();
            auto thread = std::make_shared<std::thread>([x, future]() {
                if (x.index() == 0) {
                    int val = std::get<int>(x);
                    future->set_value(val * val);
                } else if (x.index() == 1) {
                    float val = std::get<float>(x);
                    future->set_value(static_cast<int>(std::pow(val, 2)));
                } else {
                    double val = std::get<double>(x);
                    future->set_value(static_cast<int>(std::pow(val, 2)));
                }
            });
            futures.push_back(future);
            threads.push_back(thread);
        }

        std::vector<int> results;
        for (auto& future : futures) {
            results.push_back(future->get());
        }

        for (auto& thread : threads) {
            thread->join();
        }

        std::ostringstream oss;
        for (size_t i = 0; i < results.size(); ++i) {
            oss << results[i];
            if (i != results.size() - 1) {
                oss << ", ";
            }
        }

        return oss.str();
    }
};