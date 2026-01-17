#include <gtest/gtest.h>
#include "../src/function_boltons_iterutils_flatten.cpp"

TEST(DataFlattenerTest, TestFlattenDataValidInput) {
    DataFlattener function;
    std::string data_str = "1,2,3;4,5,6;7,8,9";
    std::string result = function.flatten_data(data_str);
    std::string expected = "1,2,3,4,5,6,7,8,9";
    EXPECT_EQ(result, expected);
}

TEST(DataFlattenerTest, TestFlattenDataEmptyString) {
    DataFlattener function;
    std::string data_str = "";
    std::string result = function.flatten_data(data_str);
    std::string expected = "Error";
    EXPECT_EQ(result, expected);
}

TEST(DataFlattenerTest, TestFlattenDataInvalidInput) {
    DataFlattener function;
    int data = 12345; // 非字符串输入
    std::string result = function.flatten_data(data);
    std::string expected = "Error";
    EXPECT_EQ(result, expected);
}

TEST(DataFlattenerTest, TestFlattenDataSingleElement) {
    DataFlattener function;
    std::string data_str = "1";
    std::string result = function.flatten_data(data_str);
    std::string expected = "1";
    EXPECT_EQ(result, expected);
}

TEST(DataFlattenerTest, TestFlattenDataMixedStructure) {
    DataFlattener function;
    std::string data_str = "1,2,3;4,5;6,7,8";
    std::string result = function.flatten_data(data_str);
    std::string expected = "1,2,3,4,5,6,7,8";
    EXPECT_EQ(result, expected);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}