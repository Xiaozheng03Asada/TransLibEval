#include <gtest/gtest.h>
#include "../src/function_dask_array_mean.cpp"

class TestCalculateBlockwiseMean : public ::testing::Test {
protected:
    CalculateMeanFunction mean_func;
};

TEST_F(TestCalculateBlockwiseMean, TestBasicArray) {
    std::string result = mean_func.calculate_mean("1,2,3,4", 2);
    EXPECT_EQ(result, "2.5");
}

TEST_F(TestCalculateBlockwiseMean, TestFloatingPointArray) {
    std::string result = mean_func.calculate_mean("1.5,2.5,3.5,4.5", 2);
    EXPECT_EQ(result, "3.0");
}

TEST_F(TestCalculateBlockwiseMean, TestHighDimensionalArray) {
    std::string result = mean_func.calculate_mean("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24", 4);
    EXPECT_EQ(result, "12.5");
}

TEST_F(TestCalculateBlockwiseMean, TestNonNumericData) {
    std::string result = mean_func.calculate_mean("a,b,c", 2);
    EXPECT_TRUE(result.find("Error:") != std::string::npos);
}

TEST_F(TestCalculateBlockwiseMean, TestEmptyString) {
    std::string result = mean_func.calculate_mean("", 2);
    EXPECT_TRUE(result.find("Error:") != std::string::npos);
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}