#include <string>
#include <cmath>
#include <stdexcept>
#include <typeinfo>

class DataProcessor {
public:
    std::string test_singledispatch(const std::string& data) {
        return data;
    }

    int test_singledispatch(int data) {
        return data * 2;
    }

    double test_singledispatch(double data) {
        return round(data * 2 * 100) / 100;
    }

    std::string test_singledispatch(std::nullptr_t) {
        return "NONE";
    }

    template<typename T>
    std::string test_singledispatch(T data) {
        throw std::runtime_error("不支持的数据类型: " + std::string(typeid(data).name()));
    }
};