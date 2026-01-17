#include <gtest/gtest.h>
#include "../src/function_sklearn_SimpleImputer.cpp"

class TestSimpleImputerFunction : public ::testing::Test {
protected:
    SimpleImputerFunction imputer;
};

TEST_F(TestSimpleImputerFunction, TestOutputType) {
    std::string data = "1,2;None,3;4,None;5,6;None,8";
    std::string result = imputer.quick_sort_from_string(data);
    EXPECT_EQ(typeid(result).name(), typeid(std::string).name());
}

TEST_F(TestSimpleImputerFunction, TestDifferentMissingPattern) {
    std::string data = "1,2;None,3;4,None;5,6;None,8";
    std::string result = imputer.quick_sort_from_string(data);
    EXPECT_TRUE(result.find("1.0") != std::string::npos);
}

TEST_F(TestSimpleImputerFunction, TestEmptyData) {
    std::string data = "";
    std::string result = imputer.quick_sort_from_string(data);
    EXPECT_EQ(result, "");
}

TEST_F(TestSimpleImputerFunction, TestNoNanValues) {
    std::string data = "1,2;2,3;4,5;6,7;8,9";
    std::string result = imputer.quick_sort_from_string(data);
    EXPECT_TRUE(result.find("None") == std::string::npos);
}

TEST_F(TestSimpleImputerFunction, TestDifferentDimensionData) {
    std::string data = "1,2;None,4";
    std::string result = imputer.quick_sort_from_string(data);
    EXPECT_EQ(typeid(result).name(), typeid(std::string).name());
    EXPECT_TRUE(result.find("4.0") != std::string::npos);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}