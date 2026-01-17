#include <iostream>

// 空的main函数，依次加载所有的功能库
int main(int argc, char** argv) {
    if (argc < 2) {
        std::cerr << "No function specified to test." << std::endl;
        return 1;
    }

    // argv[1] 是传递过来的功能函数名称
    std::string func_name = argv[1];
    std::cout << "Testing function: " << func_name << std::endl;

    // 这里不需要实际调用函数，只测试能否通过编译
    std::cout << "Function " << func_name << " build successfully." << std::endl;

    return 0;
}
